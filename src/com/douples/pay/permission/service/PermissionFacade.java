package com.douples.pay.permission.service;

import java.util.List;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.entity.Permission;

/**
 * 权限接口
 * <p>Title: PermissionFacade</p>  
 * <p>Description: 权限接口</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface PermissionFacade {

	/**
	 * 创建pmsPermission
	 */
	void saveData(Permission pmsPermission);

	/**
	 * 修改pmsPermission
	 */
	void updateData(Permission pmsPermission);

	/**
	 * 根据id获取数据pmsPermission
	 * 
	 * @param id
	 * @return
	 */
	Permission getDataById(Long id);

	/**
	 * 分页查询pmsPermission
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsPermission
	 * @return
	 */
	PageBean listPage(PageParam pageParam, Permission pmsPermission);

	/**
	 * 检查权限名称是否已存在
	 * 
	 * @param permissionName
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
	 * 删除
	 * 
	 * @param permissionId
	 */
	void delete(Long permissionId);

	/**
	 * 根据角色查找角色对应的功能权限ID集
	 * 
	 * @param roleId
	 * @return
	 */
	String getPermissionIdsByRoleId(Long roleId);
	
	/**
	 * 查询所有的权限
	 */
	List<Permission> listAll();

}
