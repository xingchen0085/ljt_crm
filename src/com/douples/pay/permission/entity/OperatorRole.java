package com.douples.pay.permission.entity;

import com.douples.pay.common.entity.PermissionBaseEntity;

/**
  * 权限管理-角色,操作员关联表
  * <p>Title: OperatorRole</p>  
  * <p>Description: 权限管理-角色,操作员关联表</p>  
  * @author hexuefeng  
  * @date 2017-12-1
  */
public class OperatorRole extends PermissionBaseEntity {

	private static final long serialVersionUID = 174356028197753020L;
	private Long roleId;// 角色ID
	private Long operatorId;// /操作员ID

	/**
	 * 角色ID
	 * 
	 * @return
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * 角色ID
	 * 
	 * @return
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 操作员ID
	 * 
	 * @return
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * 操作员ID
	 * 
	 * @return
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public OperatorRole() {

	}

}
