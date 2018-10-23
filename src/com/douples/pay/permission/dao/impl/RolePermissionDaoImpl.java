package com.douples.pay.permission.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.douples.pay.permission.dao.RolePermissionDao;
import com.douples.pay.permission.entity.RolePermission;

/**
 * 权限-角色与权限点DAO实现
 * <p>Title: RolePermissionDaoImpl</p>  
 * <p>Description: 权限-角色与权限点DAO实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Repository
public class RolePermissionDaoImpl extends PermissionBaseDaoImpl<RolePermission> implements RolePermissionDao {

	/**
	 * 根据角色ID找到角色关联的权限点.
	 * 
	 * @param roleId
	 *            .
	 * @return rolePermissionList .
	 */
	public List<RolePermission> listByRoleId(final long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}

	/**
	 * 根据角色ID字符串获取相应角色-权限关联信息.
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<RolePermission> listByRoleIds(String roleIdsStr) {
		List<String> roldIds = Arrays.asList(roleIdsStr.split(","));
		return super.getSqlSession().selectList(getStatement("listByRoleIds"), roldIds);
	}
	
	public void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("permissionId", permissionId);
		super.getSqlSession().delete(getStatement("deleteByRoleIdAndPermissionId"), paramMap);
	}
	
	public void deleteByRoleId(Long roleId){
		super.getSqlSession().delete(getStatement("deleteByRoleId"), roleId);
	}
}
