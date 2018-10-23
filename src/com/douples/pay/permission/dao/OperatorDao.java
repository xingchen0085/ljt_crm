package com.douples.pay.permission.dao;

import java.util.List;

import com.douples.pay.permission.entity.Operator;

/**
 * 操作员DAO
 * <p>Title: OperatorDao</p>  
 * <p>Description: 操作员DAO</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface OperatorDao extends PermissionBaseDao<Operator> {

	/**
	 * 根据操作员登录名获取操作员信息.
	 * 
	 * @param loginName
	 *            .
	 * @return operator .
	 */
	Operator findByLoginName(String loginName);

	/**
	 * 根据角色ID找到操作员列表.
	 * 
	 * @param roleId
	 * @return
	 */
	List<Operator> listByRoleId(Long roleId);
}
