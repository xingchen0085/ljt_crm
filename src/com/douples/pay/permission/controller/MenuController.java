package com.douples.pay.permission.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douples.pay.common.controller.BaseController;
import com.douples.pay.common.dwz.DwzAjax;
import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.enums.PublicEnum;
import com.douples.pay.common.enums.PublicStatusEnum;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.permission.biz.MenuBiz;
import com.douples.pay.permission.entity.Menu;
import com.douples.pay.permission.service.MenuFacade;

/**
 * 权限-菜单控制器
 * <p>Title: MenuController</p>
 * <p>Description: </p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    private static final Log log = LogFactory.getLog(MenuController.class);

    @Autowired
    private MenuFacade menuFacade;
    @Autowired
    private MenuBiz menuBiz;

    /**
     * 列出要管理的菜单.
     *
     * @return PmsMenuList .
     */
    @RequiresPermissions("sys:menu:view")
    @RequestMapping("/list")
    public String listMenu(HttpServletRequest req, Model model) {
        String editMenuController = "sys/menu/editUI";
        String str = menuBiz.getTreeMenu(editMenuController);
        model.addAttribute("tree", str);
        return "sys/menuList";
    }

    /**
     * 进入新菜单添加页面.
     *
     * @return menuAdd .
     */
    @RequiresPermissions("sys:menu:add")
    @RequestMapping("/addUI")
    public String addMenuUI(HttpServletRequest req, Menu menu, Model model, Long pid) {
        if (null != pid) {
            Menu parentMenu = menuFacade.getById(pid);
            menu.setParent(parentMenu);
            model.addAttribute(menu);
        }
        return "sys/menuAdd";
    }

    /**
     * 保存新增菜单.
     *
     * @return operateSuccess or operateError .
     */
    @RequiresPermissions("sys:menu:add")
    @RequestMapping("/add")
    public String addMenu(HttpServletRequest req, Menu menu, Model model, DwzAjax dwz) {
        try {
            String name = menu.getName();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("isLeaf", "YES");
            map.put("name", name);
            List<Menu> list = menuFacade.getMenuByNameAndIsLeaf(map);
            if (list.size() > 0) {
                return operateError("同级菜单名称不能重复", model);
            }
            menu.setCreateUserName(getOperator().getLoginName());
            menu.setStatus(PublicStatusEnum.ACTIVE.name());
            if (null != menu.getParent().getId()) {
                menu.setIsLeaf(PublicEnum.YES.name());
                menu.setLevel(menu.getParent().getLevel() + 1);
            } else {
                menu.setIsLeaf(PublicEnum.NO.name());
                menu.setLevel(1L);
                Menu parent = new Menu();
                parent.setId(0l);
                menu.setParent(parent);
            }

            PropertyUtils.addDefaultProperty(menu, PropertyAddFlagEnum.INSERT,
                    getOperator().getId(), getOperator().getRealName());

            menuFacade.savaMenu(menu);
        } catch (Exception e) {
            // 记录系统操作日志
            log.error("== addPmsMenu exception:", e);
            return operateError("添加菜单出错", model);
        }
        return operateSuccess(model, dwz);
    }

    /**
     * 进入菜单修改页面.
     *
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping("/editUI")
    public String editMenuUI(HttpServletRequest req, Long id, Model model) {
        if (null != id) {
            Menu menu = menuFacade.getById(id);
            model.addAttribute(menu);
        }
        return "sys/menuEdit";
    }

    /**
     * 保存要修改的菜单.
     *
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping("/edit")
    public String editMenu(HttpServletRequest req, Menu menu, Model model, DwzAjax dwz) {
        try {
            Menu parentMenu = menu.getParent();
            if (null == parentMenu) {
                parentMenu = new Menu();
                parentMenu.setId(0L);
            }
            menu.setParent(parentMenu);
            PropertyUtils.addDefaultProperty(menu, PropertyAddFlagEnum.UPDATE,
                    getOperator().getId(), getOperator().getRealName());
            menuFacade.update(menu);
            // 记录系统操作日志
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            // 记录系统操作日志
            log.error("== editMenu exception:", e);
            return operateError("保存菜单出错", model);
        }

    }

    /**
     * 删除菜单.
     *
     * @return
     */
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/delete")
    public String delMenu(HttpServletRequest req, Long menuId, Model model, DwzAjax dwz) {
        try {
            if (menuId == null || menuId == 0) {
                return operateError("无法获取要删除的数据", model);
            }
            Menu menu = menuFacade.getById(menuId);
            if (menu == null) {
                return operateError("无法获取要删除的数据", model);
            }
            Long parentId = menu.getParent().getId(); // 获取父菜单ID

            // 先判断此菜单下是否有子菜单
            List<Menu> childMenuList = menuFacade.listByParentId(menuId);
            if (childMenuList != null && !childMenuList.isEmpty()) {
                return operateError("此菜单下关联有【" + childMenuList.size() + "】个子菜单，不能支接删除!", model);
            }

            // 删除掉菜单
            menuFacade.delete(menuId);

            // 删除菜单后，要判断其父菜单是否还有子菜单，如果没有子菜单了就要装其父菜单设为叶子节点
            List<Menu> childList = menuFacade.listByParentId(parentId);
            if (childList == null || childList.isEmpty()) {
                // 此时要将父菜单设为叶子
                Menu parent = menuFacade.getById(parentId);
                parent.setIsLeaf(PublicEnum.YES.name());
                menuFacade.update(parent);
            }
            // 记录系统操作日志
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            // 记录系统操作日志
            log.error("== delMenu exception:", e);
            return operateError("删除菜单出错", model);
        }
    }

}
