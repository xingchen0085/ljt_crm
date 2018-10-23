package com.douples.pay.permission.entity;

import com.douples.pay.common.entity.PermissionBaseEntity;

/**
 * 权限管理-角色
 * <p>Title: Role</p>  
 * <p>Description: 权限管理-角色</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class Role extends PermissionBaseEntity {

	private static final long serialVersionUID = -1850274607153125161L;
	private String roleCode; // 角色编码：例如：admin
	private String roleName; // 角色名称

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 角色名称
	 * 
	 * @return
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Role() {

	}
}
