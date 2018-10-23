package com.douples.pay.permission.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.douples.pay.common.controller.BaseController;
import com.douples.pay.common.dwz.DwzAjax;
import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.entity.Permission;
import com.douples.pay.permission.entity.Role;
import com.douples.pay.permission.enums.OperatorTypeEnum;
import com.douples.pay.permission.service.MenuFacade;
import com.douples.pay.permission.service.MenuRoleFacade;
import com.douples.pay.permission.service.OperatorRoleFacade;
import com.douples.pay.permission.service.PermissionFacade;
import com.douples.pay.permission.service.RoleFacade;
import com.douples.pay.permission.service.RolePermissionFacade;
import com.douples.pay.permission.utils.ValidateUtils;

/**
 * 权限管理模块角色管理
 * <p>Title: RoleController</p>
 * <p>Description: </p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleFacade roleFacade;
    @Autowired
    private MenuFacade menuFacade;
    @Autowired
    private MenuRoleFacade menuRoleFacade;
    @Autowired
    private PermissionFacade permissionFacade;
    @Autowired
    private RolePermissionFacade rolePermissionFacade;
    @Autowired
    private OperatorRoleFacade operatorRoleFacade;

    private static Log log = LogFactory.getLog(RoleController.class);

    /**
     * 获取角色列表
     *
     * @return listRole or operateError .
     */
    @RequiresPermissions("sys:role:view")
    @RequestMapping("/list")
    public String listPmsRole(HttpServletRequest req, PageParam pageParam, Role role, Model model) {
        try {
            PageBean pageBean = roleFacade.listPage(pageParam, role);
            model.addAttribute(pageBean);
            model.addAttribute("pageParam", pageParam);
            model.addAttribute("role", role);
            return "sys/roleList";
        } catch (Exception e) {
            log.error("== listRole exception:", e);
            return operateError("获取数据失败", model);
        }
    }

    /**
     * 转到添加角色页面 .
     *
     * @return addPmsRoleUI or operateError .
     */
    @RequiresPermissions("sys:role:add")
    @RequestMapping("/addUI")
    public String addPmsRoleUI(HttpServletRequest req, Model model) {
        try {
            return "sys/roleAdd";
        } catch (Exception e) {
            log.error("== addRoleUI get data exception:", e);
            return operateError("获取数据失败", model);
        }
    }

    /**
     * 保存新添加的一个角色 .
     *
     * @return operateSuccess or operateError .
     */
    @RequiresPermissions("sys:role:add")
    @RequestMapping("/add")
    public String addPmsRole(HttpServletRequest req, Model model, @RequestParam("roleCode") String roleCode, @RequestParam("roleName") String roleName, @RequestParam("remark") String remark, DwzAjax dwz) {
        try {
            Role roleNameCheck = roleFacade.getByRoleNameOrRoleCode(roleName, null);
            if (roleNameCheck != null) {
                return operateError("角色名【" + roleName + "】已存在", model);
            }

            Role roleCodeCheck = roleFacade.getByRoleNameOrRoleCode(null, roleCode);
            if (roleCodeCheck != null) {
                return operateError("角色编码【" + roleCode + "】已存在", model);
            }

            // 保存基本角色信息
            Role role = new Role();
            role.setRoleCode(roleCode);
            role.setRoleName(roleName);
            role.setRemark(remark);
            role.setCreateTime(new Date());
            role.setCreateUserName(getOperator().getLoginName());
            role.setStatus("ACTIVE");
            // 表单数据校验
            String validateMsg = validateRole(role);
            if (StringUtils.isNotBlank(validateMsg)) {
                return operateError(validateMsg, model); // 返回错误信息
            }
            PropertyUtils.addDefaultProperty(role, PropertyAddFlagEnum.INSERT,
                    getOperator().getId(), getOperator().getRealName());
            roleFacade.saveData(role);
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== addRole exception:", e);
            return operateError("保存数据失败", model);
        }
    }

    /**
     * 校验角色表单数据.
     *
     * @param role 角色信息.
     * @return msg .
     */
    private String validateRole(Role role) {
        String msg = ""; // 用于存放校验提示信息的变量
        String roleName = role.getRoleName(); // 角色名称
        String desc = role.getRemark(); // 描述
        // 角色名称 permissionName
        msg += ValidateUtils.lengthValidate("角色名称", roleName, true, 3, 90);
        // 描述 desc
        msg += ValidateUtils.lengthValidate("描述", desc, true, 3, 300);
        return msg;
    }

    /**
     * 转到角色修改页面 .
     *
     * @return editPmsRoleUI or operateError .
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/editUI")
    public String editPmsRoleUI(HttpServletRequest req, Model model, Long roleId) {
        try {
            Role role = roleFacade.getDataById(roleId);
            if (role == null) {
                return operateError("获取数据失败", model);
            }

            model.addAttribute(role);
            return "/sys/roleEdit";
        } catch (Exception e) {
            log.error("== editRoleUI exception:", e);
            return operateError("获取数据失败", model);
        }
    }

    /**
     * 保存修改后的角色信息 .
     *
     * @return operateSuccess or operateError .
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/edit")
    public String editPmsRole(HttpServletRequest req, Model model, Role role, DwzAjax dwz) {
        try {
            Long id = role.getId();

            Role currRole = roleFacade.getDataById(id);
            if (currRole == null) {
                return operateError("无法获取要修改的数据", model);
            }

//			Role roleNameCheck = roleFacade.getByRoleNameOrRoleCode(role.getRoleName(), null);
//			if (roleNameCheck != null) {
//				return operateError("角色名【" + role.getRoleName() + "】已存在", model);
//			}
//
//			Role roleCodeCheck = roleFacade.getByRoleNameOrRoleCode(null, role.getRoleCode());
//			if (roleCodeCheck != null) {
//				return operateError("角色编码【" + role.getRoleCode() + "】已存在", model);
//			}

            currRole.setRoleName(role.getRoleName());
            currRole.setRoleCode(role.getRoleCode());
            currRole.setRemark(role.getRemark());

            // 表单数据校验
            String validateMsg = validateRole(currRole);
            if (StringUtils.isNotBlank(validateMsg)) {
                return operateError(validateMsg, model); // 返回错误信息
            }

            PropertyUtils.addDefaultProperty(currRole, PropertyAddFlagEnum.UPDATE,
                    getOperator().getId(), getOperator().getRealName());
            roleFacade.updateData(currRole);
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== editRole exception:", e);
            return operateError("保存失败", model);
        }
    }

    /**
     * 删除一个角色
     *
     * @return operateSuccess or operateError .
     */
    @RequiresPermissions("sys:role:delete")
    @RequestMapping("/delete")
    public String deletePmsRole(HttpServletRequest req, Model model, Long roleId, DwzAjax dwz) {
        try {

            Role role = roleFacade.getDataById(roleId);
            if (role == null) {
                return operateError("无法获取要删除的角色", model);
            }
            String msg = "";
            // 判断是否有操作员关联到此角色
            int operatorCount = operatorRoleFacade.countOperatorByRoleId(roleId);
            if (operatorCount > 0) {
                msg += "有【" + operatorCount + "】个操作员关联到此角色，要先解除所有关联后才能删除!";
                return operateError(msg, model);
            }

            roleFacade.delete(roleId);
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== deletePmsRole exception:", e);
            return operateError("删除失败", model);
        }
    }

    /**
     * 分配权限UI
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequiresPermissions("sys:role:assignpermission")
    @RequestMapping("/assignPermissionUI")
    public String assignPermissionUI(HttpServletRequest req, Model model, Long roleId) {

        Role role = roleFacade.getDataById(roleId);
        if (role == null) {
            return operateError("无法获取角色信息", model);
        }
        // 普通操作员没有修改超级管理员角色的权限
        if (OperatorTypeEnum.USER.name().equals(this.getOperator().getType()) && "admin".equals(role.getRoleName())) {
            return operateError("权限不足", model);
        }

        String permissionIds = permissionFacade.getPermissionIdsByRoleId(roleId); // 根据角色查找角色对应的功能权限ID集
        List<Permission> permissionList = permissionFacade.listAll();
        List<Operator> operatorList = operatorRoleFacade.listOperatorByRoleId(roleId);

        model.addAttribute("permissionIds", permissionIds);
        model.addAttribute("permissionList", permissionList);
        model.addAttribute("operatorList", operatorList);
        model.addAttribute("role", role);
        return "/sys/assignPermissionUI";
    }

    /**
     * 分配角色权限
     */
    @RequiresPermissions("sys:role:assignpermission")
    @RequestMapping("/assignPermission")
    public String assignPermission(HttpServletRequest req, Model model, @RequestParam("roleId") Long roleId, DwzAjax dwz, @RequestParam("selectVal") String selectVal) {
        try {
            String rolePermissionStr = getRolePermissionStr(selectVal);
            rolePermissionFacade.saveRolePermission(roleId, rolePermissionStr, getOperator());
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== assignPermission exception:", e);
            return operateError("保存失败", model);
        }
    }

    /**
     * 分配菜单UI
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/assignMenuUI")
    public String assignMenuUI(HttpServletRequest req, Model model, Long roleId) {
        Role role = roleFacade.getDataById(roleId);
        if (role == null) {
            return operateError("无法获取角色信息", model);
        }
        // 普通操作员没有修改超级管理员角色的权限
        if (OperatorTypeEnum.USER.name().equals(this.getOperator().getType()) && "admin".equals(role.getRoleName())) {
            return operateError("权限不足", model);
        }

        String menuIds = menuFacade.getMenuIdsByRoleId(roleId); // 根据角色查找角色对应的菜单ID集
        List menuList = menuFacade.getListByParent(null);
        List<Operator> operatorList = operatorRoleFacade.listOperatorByRoleId(roleId);

        model.addAttribute("menuIds", menuIds);
        model.addAttribute("menuList", menuList);
        model.addAttribute("operatorList", operatorList);
        model.addAttribute("role", role);
        return "/sys/assignMenuUI";
    }

    /**
     * 分配角色菜单
     */
    @RequestMapping("/assignMenu")
    public String assignMenu(HttpServletRequest req, Model model, @RequestParam("roleId") Long roleId, DwzAjax dwz, @RequestParam("selectVal") String selectVal) {
        try {
            String roleMenuStr = getRolePermissionStr(selectVal);
            menuRoleFacade.saveRoleMenu(roleId, roleMenuStr, getOperator());
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== assignPermission exception:", e);
            return operateError("保存失败", model);
        }
    }

    /**
     * 得到角色和权限关联的ID字符串
     *
     * @return
     */
    private String getRolePermissionStr(String selectVal) throws Exception {
        String roleStr = selectVal;
        if (StringUtils.isNotBlank(roleStr) && roleStr.length() > 0) {
            roleStr = roleStr.substring(0, roleStr.length() - 1);
        }
        return roleStr;
    }
}
