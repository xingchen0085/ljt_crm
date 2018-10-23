package com.douples.pay.permission.controller;

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

import com.douples.pay.common.controller.BaseController;
import com.douples.pay.common.dwz.DwzAjax;
import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.enums.PublicStatusEnum;
import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.permission.entity.Permission;
import com.douples.pay.permission.entity.Role;
import com.douples.pay.permission.service.PermissionFacade;
import com.douples.pay.permission.service.RoleFacade;
import com.douples.pay.permission.utils.ValidateUtils;

/**
 * 权限管理模块的Permission类，包括权限点管理、角色管理、操作员管理
 * <p>Title: PermissionController</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Controller
@RequestMapping("/sys/permission")
public class PermissionController extends BaseController {

	@Autowired
	private PermissionFacade permissionFacade;
	@Autowired
	private RoleFacade roleFacade;

	private static Log log = LogFactory.getLog(PermissionController.class);

	/**
	 * 分页列出pms权限，也可根据权限获权限名称进行查询.
	 * 
	 * @return PmsPermissionList or operateError.
	 */
	@RequiresPermissions("sys:permission:view")
	@RequestMapping("/list")
	public String listPmsPermission(HttpServletRequest req, PageParam pageParam, Permission permission, Model model) {
		try {
			PageBean pageBean = permissionFacade.listPage(pageParam, permission);
			model.addAttribute(pageBean);
			model.addAttribute("pageParam", pageParam);
			return "sys/permissionList";
		} catch (Exception e) {
			log.error("== listPermission exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 进入添加Pms权限页面 .
	 * 
	 * @return addPmsPermissionUI .
	 */
	@RequiresPermissions("sys:permission:add")
	@RequestMapping("/addUI")
	public String addPmsPermissionUI() {
		return "sys/permissionAdd";
	}

	/**
	 * 将权限信息保存到数据库中
	 * 
	 * @return operateSuccess or operateError.
	 */
	@RequiresPermissions("sys:permission:add")
	@RequestMapping("/add")
	public String addPermission(HttpServletRequest req, Permission tPermission, Model model, DwzAjax dwz) {
		try {

			// 表单数据校验
			String validateMsg = validatePermission(tPermission);
			if (StringUtils.isNotBlank(validateMsg)) {
				return operateError(validateMsg, model); // 返回错误信息
			}

			String permissionName = tPermission.getPermissionName().trim();
			String permission = tPermission.getPermission();
			// 检查权限名称是否已存在
			Permission checkName = permissionFacade.getByPermissionName(permissionName);
			if (checkName != null) {
				return operateError("权限名称【" + permissionName + "】已存在", model);
			}
			// 检查权限是否已存在
			Permission checkPermission = permissionFacade.getByPermission(permission);
			if (checkPermission != null) {
				return operateError("权限【" + permission + "】已存在", model);
			}
			tPermission.setStatus(PublicStatusEnum.ACTIVE.name());
//			tPermission.setCreateUserName(getOperator().getLoginName());
//			tPermission.setCreateTime(new Date());
//			tPermission.setCreateDate(new Date());
            PropertyUtils.addDefaultProperty(tPermission, PropertyAddFlagEnum.INSERT,
                    getOperator().getId(), getOperator().getRealName());
			permissionFacade.saveData(tPermission);

			return operateSuccess(model, dwz); // 返回operateSuccess视图,并提示“操作成功”
		} catch (Exception e) {
			log.error("== addPermission exception:", e);
			return operateError("保存失败", model);
		}
	}

	/**
	 * 校验权限信息.
	 * 
	 * @param Permission
	 *            .
	 * @return msg .
	 */
	private String validatePermission(Permission pmsPermission) {
		String msg = ""; // 用于存放校验提示信息的变量
		String permissionName = pmsPermission.getPermissionName(); // 权限名称
		String permission = pmsPermission.getPermission(); // 权限标识
		String desc = pmsPermission.getRemark(); // 权限描述
		// 权限名称 permissionName
		msg += ValidateUtils.lengthValidate("权限名称", permissionName, true, 3, 90);
		// 权限标识 permission
		msg += ValidateUtils.lengthValidate("权限标识", permission, true, 3, 100);
		// 描述 desc
		msg += ValidateUtils.lengthValidate("描述", desc, true, 3, 60);
		return msg;
	}

	/**
	 * 转到权限修改页面 .
	 * 
	 * @return editPmsPermissionUI or operateError .
	 */
	@RequiresPermissions("sys:permission:edit")
	@RequestMapping("/editUI")
	public String editPermissionUI(HttpServletRequest req, Long id, Model model) {
		try {
			Permission permission = permissionFacade.getDataById(id);
			model.addAttribute("permission", permission);
			return "sys/permissionEdit";
		} catch (Exception e) {
			log.error("== editPermissionUI exception:", e);
			return operateError("获取数据失败", model);
		}
	}

	/**
	 * 保存修改后的权限信息
	 * 
	 * @return operateSuccess or operateError .
	 */
	@RequiresPermissions("sys:permission:edit")
	@RequestMapping("/edit")
	public String editPermission(HttpServletRequest req, Permission permission, Model model, DwzAjax dwz) {
		try {
			Long id = permission.getId();
			Permission tPermission = permissionFacade.getDataById(id);
			if (tPermission == null) {
				return operateError("无法获取要修改的数据", model);
			} else {

				String permissionName = permission.getPermissionName();
				String remark = permission.getRemark();

				tPermission.setPermissionName(permissionName);
				tPermission.setRemark(remark);

				// 表单数据校验
				String validateMsg = validatePermission(tPermission);
				if (StringUtils.isNotBlank(validateMsg)) {
					return operateError(validateMsg, model); // 返回错误信息
				}

				// 检查权限名称是否已存在
				Permission checkName = permissionFacade.getByPermissionNameNotEqId(permissionName, id);
				if (checkName != null) {
					return operateError("权限名称【" + permissionName + "】已存在", model);
				}

                PropertyUtils.addDefaultProperty(tPermission, PropertyAddFlagEnum.UPDATE,
                        getOperator().getId(), getOperator().getRealName());
				permissionFacade.updateData(tPermission);

				return operateSuccess(model, dwz);
			}
		} catch (Exception e) {
			log.error("== editPermission exception:", e);
			return operateError("修改失败", model);
		}
	}

	/**
	 * 删除一条权限记录
	 * 
	 * @return operateSuccess or operateError .
	 */
	@RequiresPermissions("sys:permission:delete")
	@RequestMapping("/delete")
	public String deletePermission(HttpServletRequest req, Long permissionId, Model model, DwzAjax dwz) {
		try {
			Permission permission = permissionFacade.getDataById(permissionId);
			if (permission == null) {
				return operateError("无法获取要删除的数据", model);
			}
			// 判断此权限是否关联有角色，要先解除与角色的关联后才能删除该权限
			List<Role> roleList = roleFacade.listByPermissionId(permissionId);
			if (roleList != null && !roleList.isEmpty()) {
				return operateError("权限【" + permission.getPermission() + "】关联了【" + roleList.size() + "】个角色，要解除所有关联后才能删除。其中一个角色名为:" + roleList.get(0).getRoleName(), model);
			}
			permissionFacade.delete(permissionId);
			return operateSuccess(model, dwz); // 返回operateSuccess视图,并提示“操作成功”
		} catch (Exception e) {
			log.error("== deletePermission exception:", e);
			return operateError("删除限权异常", model);
		}
	}
}
