package com.douples.pay.permission.dao;

import java.util.List;

import com.douples.pay.permission.entity.Permission;

/**
 * 权限DAO
 * <p>Title: PermissionDao</p>  
 * <p>Description: 权限DAO</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface PermissionDao extends PermissionBaseDao<Permission> {
	/**
	 * 根据实体ID集字符串获取对象列表.
	 * 
	 * @param ids
	 * @return
	 */
	List<Permission> findByIds(String ids);

	/**
	 * 检查权限名称是否已存在
	 * 
	 * @param trim
	 * @return
	 */
	Permission getByPermissionName(String permissionName);

	/**
	 * 检查权限是否已存在
	 * 
	 * @param permission
	 * @return
	 */
	Permission getByPermission(String permission);

	/**
	 * 检查权限名称是否已存在(其他id)
	 * 
	 * @param permissionName
	 * @param id
	 * @return
	 */
	Permission getByPermissionNameNotEqId(String permissionName, Long id);

	/**
	 * 获取叶子菜单下所有的功能权限
	 * 
	 * @param valueOf
	 * @return
	 */
	List<Permission> listAllByMenuId(Long menuId);

}
