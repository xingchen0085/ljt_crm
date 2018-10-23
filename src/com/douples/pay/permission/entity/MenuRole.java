package com.douples.pay.permission.entity;

import com.douples.pay.common.entity.PermissionBaseEntity;

/**
 * 权限管理-角色权限关联表
 * <p>Title: MenuRole</p>  
 * <p>Description: 权限管理-角色权限关联表</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class MenuRole extends PermissionBaseEntity {

	private static final long serialVersionUID = -9012707031072904356L;

	/** 角色ID **/
	private Long roleId;

	/** 菜单ID **/
	private Long menuId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
}
