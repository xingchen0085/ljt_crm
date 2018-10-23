package com.douples.pay.permission.service;

import java.util.Set;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.entity.RolePermission;

/**
 * 角色权限接口
 * <p>Title: RolePermissionFacade</p>  
 * <p>Description: 角色权限接口</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface RolePermissionFacade {

	/**
	 * 根据操作员ID，获取所有的功能权限集
	 * 
	 * @param operatorId
	 */
	public Set<String> getPermissionsByOperatorId(Long operatorId);

	/**
	 * 创建pmsRolePermission
	 */
	void saveData(RolePermission pmsRolePermission);

	/**
	 * 修改pmsRolePermission
	 */
	void updateData(RolePermission pmsRolePermission);

	/**
	 * 根据id获取数据pmsRolePermission
	 * 
	 * @param id
	 * @return
	 */
	RolePermission getDataById(Long id);

	/**
	 * 分页查询pmsRolePermission
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsRolePermission
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RolePermission pmsRolePermission);
	
	/**
	 * 保存角色和权限之间的关联关系
	 */
	void saveRolePermission(Long roleId, String rolePermissionStr,Operator operator);

}
