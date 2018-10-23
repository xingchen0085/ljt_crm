package com.douples.pay.permission.entity;

import com.douples.pay.common.entity.PermissionBaseEntity;

/**
 * 权限-权限表实体
 * <p>Title: Permission</p>  
 * <p>Description: 权限-权限表实体</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class Permission extends PermissionBaseEntity {

	private static final long serialVersionUID = -2175597348886393330L;
	private String permissionName; // 权限名称
	private String permission; // 权限标识

	/**
	 * 权限名称
	 * 
	 * @return
	 */
	public String getPermissionName() {
		return permissionName;
	}

	/**
	 * 权限名称
	 * 
	 * @return
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * 权限标识
	 * 
	 * @return
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * 权限标识
	 * 
	 * @return
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

}
