package com.douples.pay.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douples.pay.common.controller.BaseController;
import com.douples.pay.common.dwz.DWZ;
import com.douples.pay.common.dwz.DwzAjax;
import com.douples.pay.common.utils.StringUtil;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.exception.PermissionException;
import com.douples.pay.permission.service.MenuFacade;
import com.douples.pay.permission.service.OperatorRoleFacade;
import com.douples.pay.permission.utils.Constants;

/**
 * 登录控制器
 * <p>Title: LoginController</p>
 * <p>Description: 登录控制器</p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
@Controller
public class LoginController extends BaseController {

    /**
     * 日志
     */
    private static final Log LOG = LogFactory.getLog(LoginController.class);

    @Autowired
    private OperatorRoleFacade operatorRoleFacade;

    @Autowired
    private MenuFacade menuFacade;

    /**
     * 进入后台登陆页面
     * <p>Title: login</p>
     * <p>Description: </p>
     *
     * @param req   请求
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest req, Model model) {

        String exceptionClassName = (String) req.getAttribute(Constants.CAPTCHA_VALIDATE_MSG);
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (PermissionException.class.getName().equals(exceptionClassName)) {
            error = "网络异常,请联系管理员";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "错误次数太多，锁定10分钟";
        } else if (exceptionClassName != null) {
            error = "错误提示：" + exceptionClassName;
        }
        model.addAttribute("message", error);
        return "system/login";
    }

    /**
     * 登陆后台管理系统
     * <p>Title: index</p>
     * <p>Description: </p>
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest req, Model model) {
        Operator operator = (Operator) this.getSession().getAttribute(Constants.CURR_SESSION_OPERATOR);
        try {
            String tree = this.buildOperatorPermissionMenu(operator);

            model = buigDashBoardData(model);

            model.addAttribute("tree", tree);
            model.addAttribute("operator", operator);
        } catch (PermissionException e) {
            LOG.error("登录异常:" + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "system/login";
        }
        return "system/index";

    }

    //构建前端仪表盘数据 : 期间会用缓存，无需考虑效率问题
    private Model buigDashBoardData(Model model) {

        return model;
    }

    /**
     * 函数功能说明 ：进入退出系统确认页面. 修改者名字： 修改日期： 修改内容：
     *
     * @return String
     * @throws
     * @参数： @return
     */
    @RequestMapping(value = "/admin/confirm", method = RequestMethod.GET)
    public String confirm() {
        return "system/confirm";
    }

    /**
     * 函数功能说明 ： 退出系统. 修改者名字： 修改日期： 修改内容：
     *
     * @return String
     * @throws
     * @参数： @return
     */
    @RequestMapping(value = "/admin/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, Model model) {
        // 不是以form的形式提交的数据,要new一个DwzAjax对象
        DwzAjax dwz = new DwzAjax();
        try {
            HttpSession session = request.getSession();
            session.removeAttribute("employee");
            LOG.info("***clean session success!***");
        } catch (Exception e) {
            LOG.error(e);
            dwz.setStatusCode(DWZ.ERROR);
            dwz.setMessage("退出系统时系统出现异常，请通知系统管理员！");
            model.addAttribute("dwz", dwz);
            return "admin.common.ajaxDone";
        }
        return "admin.login";
    }

    /**
     * 获取用户的菜单权限
     * <p>Title: buildOperatorPermissionMenu</p>
     * <p>Description: </p>
     *
     * @param operator
     * @return
     * @throws PermissionException
     */
    private String buildOperatorPermissionMenu(Operator operator) throws PermissionException {
        // 根据用户ID得到该用户的所有角色拼成的字符串
        String roleIds = operatorRoleFacade.getRoleIdsByOperatorId(operator.getId());
        if (StringUtils.isBlank(roleIds)) {
            LOG.error("==>用户[" + operator.getLoginName() + "]没有配置对应的权限角色");
            throw new RuntimeException("该帐号已被取消所有系统权限");
        }
        // 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
        return this.buildPermissionTree(roleIds);
    }

    /**
     * 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
     * <p>Title: buildPermissionTree</p>
     * <p>Description: </p>
     *
     * @param operator 操作员
     * @return
     * @throws PermissionException
     */
    @SuppressWarnings("rawtypes")
    public String buildPermissionTree(Operator operator) throws PermissionException {
        List treeData = null;
        try {
            treeData = menuFacade.listMenuDataByOperatorId(operator.getId());
            if (StringUtil.isEmpty(treeData)) {
                LOG.error("用户没有分配菜单权限");
                throw new PermissionException(PermissionException.PERMISSION_USER_NOT_MENU, "该用户没有分配菜单权限"); // 该用户没有分配菜单权限
            }
        } catch (Exception e) {
            LOG.error("根据角色查询菜单出现错误", e);
            throw new PermissionException(PermissionException.PERMISSION_QUERY_MENU_BY_ROLE_ERROR, "根据角色查询菜单出现错误"); // 查询当前角色的
        }
        StringBuffer strJson = new StringBuffer();
        buildAdminPermissionTree("0", strJson, treeData);
        return strJson.toString();
    }

    /**
     * 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
     * <p>Title: buildPermissionTree</p>
     * <p>Description: </p>
     *
     * @param roleIds 权限菜单ID
     * @return
     * @throws PermissionException
     */
    @SuppressWarnings("rawtypes")
    public String buildPermissionTree(String roleIds) throws PermissionException {
        List treeData = null;
        try {
            treeData = menuFacade.listByRoleIds(roleIds);
            if (StringUtil.isEmpty(treeData)) {
                LOG.error("用户没有分配菜单权限");
                throw new PermissionException(PermissionException.PERMISSION_USER_NOT_MENU, "该用户没有分配菜单权限"); // 该用户没有分配菜单权限
            }
        } catch (Exception e) {
            LOG.error("根据角色查询菜单出现错误", e);
            throw new PermissionException(PermissionException.PERMISSION_QUERY_MENU_BY_ROLE_ERROR, "根据角色查询菜单出现错误"); // 查询当前角色的
        }
        StringBuffer strJson = new StringBuffer();
        buildAdminPermissionTree("0", strJson, treeData);
        return strJson.toString();
    }

    /**
     * 构建管理后台的树形权限功能菜单
     *
     * @param pId
     * @param treeBuf
     * @param menuList
     */
    @SuppressWarnings("rawtypes")
    private void buildAdminPermissionTree(String pId, StringBuffer treeBuf, List menuList) {

        List<Map> listMap = getSonMenuListByPid(pId.toString(), menuList);
        for (Map map : listMap) {
            String id = map.get("id").toString();// id
            String name = map.get("name").toString();// 名称
            String isLeaf = map.get("isLeaf").toString();// 是否叶子
            String level = map.get("level").toString();// 菜单层级（1、2、3、4）
            String url = map.get("url").toString(); // ACTION访问地址
            String navTabId = "";
            if (!StringUtil.isEmpty(map.get("targetName"))) {
                navTabId = map.get("targetName").toString(); // 用于刷新查询页面
            }

            if ("1".equals(level)) {
                treeBuf.append("<div class='accordionHeader'>");
                treeBuf.append("<h2> <span>Folder</span> " + name + "</h2>");
                treeBuf.append("</div>");
                treeBuf.append("<div class='accordionContent'>");
            }

            if ("YES".equals(isLeaf)) {
                treeBuf.append("<li><a href='" + url + "' target='navTab' rel='" + navTabId + "'>" + name + "</a></li>");
            } else {

                if ("1".equals(level)) {
                    treeBuf.append("<ul class='tree treeFolder'>");
                } else {
                    treeBuf.append("<li><a>" + name + "</a>");
                    treeBuf.append("<ul>");
                }

                buildAdminPermissionTree(id, treeBuf, menuList);

                if ("1".equals(level)) {
                    treeBuf.append("</ul>");
                } else {
                    treeBuf.append("</ul></li>");
                }

            }

            if ("1".equals(level)) {
                treeBuf.append("</div>");
            }
        }

    }

    /**
     * 根据(pId)获取(menuList)中的所有子菜单集合.
     *
     * @param pId      父菜单ID.
     * @param menuList 菜单集合.
     * @return sonMenuList.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<Map> getSonMenuListByPid(String pId, List menuList) {
        List sonMenuList = new ArrayList<Object>();
        for (Object menu : menuList) {
            Map map = (Map) menu;
            if (map != null) {
                String parentId = map.get("pId").toString();// 父id
                if (parentId.equals(pId)) {
                    sonMenuList.add(map);
                }
            }
        }
        return sonMenuList;
    }

}
