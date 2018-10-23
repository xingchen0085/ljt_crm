package com.douples.pay.permission.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.douples.pay.permission.dao.OperatorDao;
import com.douples.pay.permission.entity.Operator;

/**
 * 权限操作员DAO实现
 * <p>Title: OperatorDaoImpl</p>  
 * <p>Description: 权限操作员DAO实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Repository
public class OperatorDaoImpl extends PermissionBaseDaoImpl<Operator> implements OperatorDao {

	/**
	 * 根据操作员登录名获取操作员信息.
	 * 
	 * @param loginName
	 *            .
	 * @return operator .
	 */

	public Operator findByLoginName(String loginName) {
		return super.getSqlSession().selectOne(getStatement("findByLoginName"), loginName);
	}

	@Override
	public List<Operator> listByRoleId(Long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}

}
