package com.douples.pay.permission.controller;

import java.util.List;
import java.util.regex.Pattern;

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
import com.douples.pay.common.enums.PublicStatusEnum;
import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.entity.OperatorRole;
import com.douples.pay.permission.enums.OperatorTypeEnum;
import com.douples.pay.permission.service.OperatorFacade;
import com.douples.pay.permission.service.OperatorRoleFacade;
import com.douples.pay.permission.service.RoleFacade;
import com.douples.pay.permission.utils.PasswordHelper;
import com.douples.pay.permission.utils.ValidateUtils;

/**
 * 权限管理模块操作员管理
 * <p>Title: OperatorController</p>
 * <p>Description: </p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
@Controller
@RequestMapping("/sys/operator")
public class OperatorController extends BaseController {

    private static Log log = LogFactory.getLog(OperatorController.class);

    @Autowired
    private OperatorFacade operatorFacade;
    @Autowired
    private RoleFacade roleFacade;
    @Autowired
    private OperatorRoleFacade operatorRoleFacade;

    /**
     * 分页列出操作员信息，并可按登录名获姓名进行查询.
     *
     * @return listPmsOperator or operateError .
     */
    @RequiresPermissions("sys:operator:view")
    @RequestMapping("/list")
    public String listOperator(HttpServletRequest req, PageParam pageParam, Operator operator, Model model) {
        try {

            PageBean pageBean = operatorFacade.listPage(pageParam, operator);
            model.addAttribute(pageBean);
            model.addAttribute("OperatorStatusEnum", PublicStatusEnum.toMap());
            model.addAttribute("OperatorTypeEnum", OperatorTypeEnum.toMap());
            return "sys/operatorList";
        } catch (Exception e) {
            log.error("== listOperator exception:", e);
            return operateError("获取数据失败", model);
        }
    }

    /**
     * 查看操作员详情.
     *
     * @return .
     */
    @RequiresPermissions("sys:operator:view")
    @RequestMapping("/viewUI")
    public String viewOperatorUI(HttpServletRequest req, Long id, Model model) {
        try {
            Operator operator = operatorFacade.getDataById(id);
            if (operator == null) {
                return operateError("无法获取要查看的数据", model);
            }

            // 普通操作员没有查看超级管理员的权限
            if (OperatorTypeEnum.USER.name().equals(this.getOperator().getType()) && OperatorTypeEnum.ADMIN.name().equals(operator.getType())) {
                return operateError("权限不足", model);
            }

            // 准备角色列表
            model.addAttribute("rolesList", roleFacade.listAllRole());

            // 准备该用户拥有的角色ID字符串
            List<OperatorRole> lisOperatorRoles = operatorRoleFacade.listOperatorRoleByOperatorId(id);
            StringBuffer owenedRoleIdBuffer = new StringBuffer("");
            for (OperatorRole operatorRole : lisOperatorRoles) {
                owenedRoleIdBuffer.append(operatorRole.getRoleId());
                owenedRoleIdBuffer.append(",");
            }
            String owenedRoleIds = owenedRoleIdBuffer.toString();
            if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
                owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
            }
            model.addAttribute("operator", operator);
            model.addAttribute("owenedRoleIds", owenedRoleIds);
            return "sys/operatorView";
        } catch (Exception e) {
            log.error("== viewOperatorUI exception:", e);
            return operateError("获取数据失败", model);
        }
    }

    /**
     * 转到添加操作员页面 .
     *
     * @return addPmsOperatorUI or operateError .
     */
    @RequiresPermissions("sys:operator:add")
    @RequestMapping("/addUI")
    public String addOperatorUI(HttpServletRequest req, Model model) {
        try {
            model.addAttribute("rolesList", roleFacade.listAllRole());
            model.addAttribute("operatorStatusEnumList", PublicStatusEnum.toList());
            return "sys/operatorAdd";
        } catch (Exception e) {
            log.error("== addOperatorUI exception:", e);
            return operateError("获取角色列表数据失败", model);
        }
    }

    /**
     * 保存一个操作员
     */
    @RequiresPermissions("sys:operator:add")
    @RequestMapping("/add")
    public String addOperator(HttpServletRequest req, Operator operator, @RequestParam("selectVal") String selectVal, Model model, DwzAjax dwz) {
        try {
            operator.setType(OperatorTypeEnum.USER.name()); // 类型（
            // "0":'普通操作员',"1":'超级管理员'），只能添加普通操作员
            String roleOperatorStr = getRoleOperatorStr(selectVal);

            // 表单数据校验
            String validateMsg = validateOperator(operator, roleOperatorStr);


            if (StringUtils.isNotBlank(validateMsg)) {
                return operateError(validateMsg, model); // 返回错误信息
            }

            operator.setStatus(PublicStatusEnum.ACTIVE.name());

            // 校验操作员登录名是否已存在
            Operator loginNameCheck = operatorFacade.findOperatorByLoginName(operator.getLoginName());
            if (loginNameCheck != null) {
                return operateError("登录名【" + operator.getLoginName() + "】已存在", model);
            }

            PasswordHelper.encryptPassword(operator);
//            operator.setCreateUserName(getOperator().getLoginName());
//            operator.setCreateTime(new Date());
//            operator.setCreateDate(new Date());


            PropertyUtils.addDefaultProperty(operator, PropertyAddFlagEnum.INSERT,
                    getOperator().getId(), getOperator().getRealName());

            operatorFacade.saveOperator(operator, roleOperatorStr);

            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== addOperator exception:", e);
            return operateError("保存操作员信息失败", model);
        }
    }

    /**
     * 验证输入的邮箱格式是否符合
     *
     * @param email
     * @return 是否合法
     */
    public static boolean emailFormat(String email) {
        // boolean tag = true;
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        boolean result = Pattern.matches(check, email);
        return result;
    }

    /**
     * 验证输入的密码格式是否符合
     *
     * @param loginPwd
     * @return 是否合法
     */
    public static boolean loginPwdFormat(String loginPwd) {
        return loginPwd.matches(".*?[^a-zA-Z\\d]+.*?") && loginPwd.matches(".*?[a-zA-Z]+.*?") && loginPwd.matches(".*?[\\d]+.*?");
    }

    /**
     * 验证输入的操作员姓名格式是否符合
     *
     * @param loginPwd
     * @return 是否合法
     */
    public static boolean realNameFormat(String realName) {
        return realName.matches("[^\\x00-\\xff]+");
    }

    /**
     * 校验Pms操作员表单数据.
     *
     * @param Operator        操作员信息.
     * @param roleOperatorStr 关联的角色ID串.
     * @return
     */
    private String validateOperator(Operator operator, String roleOperatorStr) {
        String msg = ""; // 用于存放校验提示信息的变量
        msg += ValidateUtils.lengthValidate("真实姓名", operator.getRealName(), true, 2, 15);
        msg += ValidateUtils.lengthValidate("登录名", operator.getLoginName(), true, 3, 50);

        /*
         * String specialChar = "`!@#$%^&*()_+\\/"; if
         * (operator.getLoginName().contains(specialChar)) { msg +=
         * "登录名不能包含特殊字符，"; }
         */
//		if (!realNameFormat(operator.getRealName())) {
//			msg += "操作员姓名必须为中文！";
//		}

        // if (!emailFormat(operator.getLoginName())) {
        // msg += "账户名格式必须为邮箱地址！";
        // }

        // 登录密码
//		String loginPwd = operator.getLoginPwd();
//		String loginPwdMsg = ValidateUtils.lengthValidate("登录密码", loginPwd, true, 6, 50);
//		/*
//		 * if (StringUtils.isBlank(loginPwdMsg) &&
//		 * !ValidateUtils.isAlphanumeric(loginPwd)) { loginPwdMsg +=
//		 * "登录密码应为字母或数字组成，"; }
//		 */
//		msg += loginPwdMsg;

        // 手机号码
        String mobileNo = operator.getMobileNo();
        String mobileNoMsg = ValidateUtils.lengthValidate("手机号", mobileNo, true, 0, 12);
        if (StringUtils.isBlank(mobileNoMsg) && !ValidateUtils.isMobile(mobileNo)) {
            mobileNoMsg += "手机号格式不正确，";
        }
        msg += mobileNoMsg;

        // 状态
//		String status = operator.getStatus();
//		if (status == null) {
//			msg += "请选择状态，";
//			msg += "状态值不正确，";
//		} else if (!PublicStatusEnum.ACTIVE.name().equals(status) || PublicStatusEnum.UNACTIVE.name().equals(status)) {
//		}

        msg += ValidateUtils.lengthValidate("描述", operator.getRemark(), true, 3, 100);

        // 新增操作员的权限不能为空，为空没意义
        if (StringUtils.isBlank(roleOperatorStr) && operator.getId() == null) {
            msg += "操作员关联的角色不能为空";
        }
        return msg;
    }

    /**
     * 删除操作员
     *
     * @return
     */
    @RequiresPermissions("sys:operator:delete")
    @RequestMapping("/delete")
    public String deleteOperatorStatus(HttpServletRequest req, Long id, Model model, DwzAjax dwz) {
        operatorFacade.deleteOperatorById(id);
        return this.operateSuccess(model, dwz);
    }

    /**
     * 转到修改操作员界面
     *
     * @return PmsOperatorEdit or operateError .
     */
    @RequiresPermissions("sys:operator:edit")
    @RequestMapping("/editUI")
    public String editPmsOperatorUI(HttpServletRequest req, Long id, Model model) {
        try {
            Operator operator = operatorFacade.getDataById(id);
            if (operator == null) {
                return operateError("无法获取要修改的数据", model);
            }

            // 普通操作员没有修改超级管理员的权限
            if (OperatorTypeEnum.USER.name().equals(this.getOperator().getType()) && OperatorTypeEnum.ADMIN.name().equals(operator.getType())) {
                return operateError("权限不足", model);
            }
            // 准备角色列表
            model.addAttribute("rolesList", roleFacade.listAllRole());

            // 准备该用户拥有的角色ID字符串
            List<OperatorRole> lisOperatorRoles = operatorRoleFacade.listOperatorRoleByOperatorId(id);
            StringBuffer owenedRoleIdBuffer = new StringBuffer("");
            for (OperatorRole operatorRole : lisOperatorRoles) {
                owenedRoleIdBuffer.append(operatorRole.getRoleId());
                owenedRoleIdBuffer.append(",");
            }
            String owenedRoleIds = owenedRoleIdBuffer.toString();
            if (StringUtils.isNotBlank(owenedRoleIds) && owenedRoleIds.length() > 0) {
                owenedRoleIds = owenedRoleIds.substring(0, owenedRoleIds.length() - 1);
            }
            model.addAttribute("owenedRoleIds", owenedRoleIds);

            model.addAttribute("operatorStatusEnum", PublicStatusEnum.toMap());
            model.addAttribute("operatorTypeEnum", OperatorTypeEnum.toMap());
            model.addAttribute("operator", operator);
            return "sys/operatorEdit";
        } catch (Exception e) {
            log.error("== editOperatorUI exception:", e);
            return operateError("获取修改数据失败", model);
        }
    }

    /**
     * 保存修改后的操作员信息
     *
     * @return operateSuccess or operateError .
     */
    @RequiresPermissions("sys:operator:edit")
    @RequestMapping("/edit")
    public String editOperator(HttpServletRequest req, Operator operator, String selectVal, Model model, DwzAjax dwz) {
        try {
            Long id = operator.getId();

            Operator tOperator = operatorFacade.getDataById(id);
            if (tOperator == null) {
                return operateError("无法获取要修改的操作员信息", model);
            }

            // 普通操作员没有修改超级管理员的权限
            if ("USER".equals(this.getOperator().getType()) && "ADMIN".equals(tOperator.getType())) {
                return operateError("权限不足", model);
            }

            tOperator.setRemark(operator.getRemark());
            tOperator.setMobileNo(operator.getMobileNo());
            tOperator.setRealName(operator.getRealName());
            // 修改时不能修状态
            // pmsOperator.setStatus(getInteger("status"));

            String roleOperatorStr = getRoleOperatorStr(selectVal);

            // 表单数据校验
            String validateMsg = validateOperator(tOperator, roleOperatorStr);
            if (StringUtils.isNotBlank(validateMsg)) {
                return operateError(validateMsg, model); // 返回错误信息
            }

            PropertyUtils.addDefaultProperty(tOperator, PropertyAddFlagEnum.UPDATE,
                    getOperator().getId(), getOperator().getRealName());
            operatorFacade.updateOperator(tOperator, roleOperatorStr);
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== editOperator exception:", e);
            return operateError("更新操作员信息失败", model);
        }
    }

    /**
     * 根据ID冻结或激活操作员.
     *
     * @return operateSuccess or operateError .
     */
    @RequiresPermissions("sys:operator:changestatus")
    @RequestMapping("/changeStatus")
    public String changeOperatorStatus(HttpServletRequest req, Operator operator, Model model, DwzAjax dwz) {
        try {
            Long operatorId = operator.getId();
            Operator pmsOperator = operatorFacade.getDataById(operatorId);
            if (pmsOperator == null) {
                return operateError("无法获取要操作的数据", model);
            }

            if (this.getOperator().getId() == operatorId) {
                return operateError("不能修改自己账户的状态", model);
            }

            // 普通操作员没有修改超级管理员的权限
            if ("USER".equals(this.getOperator().getType()) && "ADMIN".equals(pmsOperator.getType())) {
                return operateError("权限不足", model);
            }

            // 2014-01-02,由删除改为修改状态
            // pmsPermissionBiz.deleteOperator(id);
            // 激活的变冻结，冻结的则变激活
            if (pmsOperator.getStatus().equals(PublicStatusEnum.ACTIVE.name())) {
                if ("ADMIN".equals(pmsOperator.getType())) {
                    return operateError("【" + pmsOperator.getLoginName() + "】为超级管理员，不能冻结", model);
                }
                pmsOperator.setStatus(PublicStatusEnum.UNACTIVE.name());
                PropertyUtils.addDefaultProperty(pmsOperator, PropertyAddFlagEnum.UPDATE,
                        getOperator().getId(), getOperator().getRealName());
                operatorFacade.updateData(pmsOperator);
            } else {
                pmsOperator.setStatus(PublicStatusEnum.ACTIVE.name());
                PropertyUtils.addDefaultProperty(pmsOperator, PropertyAddFlagEnum.UPDATE,
                        getOperator().getId(), getOperator().getRealName());
                operatorFacade.updateData(pmsOperator);
            }
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== changeOperatorStatus exception:", e);
            return operateError("删除操作员失败:" + e.getMessage(), model);
        }
    }

    /***
     * 重置操作员的密码（注意：不是修改当前登录操作员自己的密码） .
     *
     * @return
     */
    @RequiresPermissions("sys:operator:resetpwd")
    @RequestMapping("/resetPwdUI")
    public String resetOperatorPwdUI(HttpServletRequest req, Long id, Model model) {
        Operator operator = operatorFacade.getDataById(id);
        if (operator == null) {
            return operateError("无法获取要重置的信息", model);
        }

        // 普通操作员没有修改超级管理员的权限
        if ("USER".equals(this.getOperator().getType()) && "ADMIN".equals(operator.getType())) {
            return operateError("权限不足", model);
        }

        model.addAttribute("operator", operator);

        return "sys/operatorResetPwd";
    }

    /**
     * 重置操作员密码.
     *
     * @return
     */
    @RequiresPermissions("sys:operator:resetpwd")
    @RequestMapping("/resetPwd")
    public String resetOperatorPwd(HttpServletRequest req, Long id, String newPwd, String newPwd2, Model model, DwzAjax dwz) {
        try {
            Operator operator = operatorFacade.getDataById(id);
            if (operator == null) {
                return operateError("无法获取要重置密码的操作员信息", model);
            }

            // 普通操作员没有修改超级管理员的权限
            if ("USER".equals(this.getOperator().getType()) && "ADMIN".equals(operator.getType())) {
                return operateError("权限不足", model);
            }


            String validateMsg = validatePassword(newPwd, newPwd2);
            if (StringUtils.isNotBlank(validateMsg)) {
                return operateError(validateMsg, model); // 返回错误信息
            }
            operator.setLoginPwd(newPwd);
            PasswordHelper.encryptPassword(operator);
            PropertyUtils.addDefaultProperty(operator, PropertyAddFlagEnum.UPDATE,
                    getOperator().getId(), getOperator().getRealName());
            operatorFacade.updateData(operator);
            return operateSuccess(model, dwz);
        } catch (Exception e) {
            log.error("== resetOperatorPwd exception:", e);
            return operateError("密码重置出错:" + e.getMessage(), model);
        }
    }

    /**
     * 得到角色和操作员关联的ID字符串
     *
     * @return
     */
    private String getRoleOperatorStr(String selectVal) throws Exception {
        String roleStr = selectVal;
        if (StringUtils.isNotBlank(roleStr) && roleStr.length() > 0) {
            roleStr = roleStr.substring(0, roleStr.length() - 1);
        }
        return roleStr;
    }

    /***
     * 验证重置密码
     *
     * @param newPwd
     * @param newPwd2
     * @return
     */
    private String validatePassword(String newPwd, String newPwd2) {
        String msg = ""; // 用于存放校验提示信息的变量
        if (StringUtils.isBlank(newPwd)) {
            msg += "新密码不能为空，";
        } else if (newPwd.length() < 6) {
            msg += "新密码不能少于6位长度，";
        }

        if (!newPwd.equals(newPwd2)) {
            msg += "两次输入的密码不一致";
        }
        return msg;
    }
}
