package com.douples.pay.permission.dao;

import java.util.List;

import com.douples.pay.permission.entity.RolePermission;

/**
 * 角色权限DAO
 * <p>Title: RolePermissionDao</p>  
 * <p>Description: 角色权限DAO</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface RolePermissionDao extends PermissionBaseDao<RolePermission> {

	/**
	 * 根据角色ID找到角色关联的权限点.
	 * 
	 * @param roleId
	 *            .
	 * @return rolePermissionList .
	 */
	public List<RolePermission> listByRoleId(final long roleId);

	/**
	 * 根据角色ID字符串获取相应角色-权限关联信息.
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<RolePermission> listByRoleIds(String roleIdsStr);

	public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);
	
	public void deleteByRoleId(Long roleId);
}
