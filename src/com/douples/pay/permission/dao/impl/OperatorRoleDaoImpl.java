package com.douples.pay.permission.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.douples.pay.permission.dao.OperatorRoleDao;
import com.douples.pay.permission.entity.OperatorRole;

/**
 * 权限-操作员与角色DAO实现
 * <p>Title: OperatorRoleDaoImpl</p>  
 * <p>Description: 权限-操作员与角色DAO实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Repository
public class OperatorRoleDaoImpl extends PermissionBaseDaoImpl<OperatorRole> implements OperatorRoleDao {

	/**
	 * 根据操作员ID查找该操作员关联的角色.
	 * 
	 * @param operatorId
	 *            .
	 * @return list .
	 */
	public List<OperatorRole> listByOperatorId(Long operatorId) {
		return super.getSqlSession().selectList(getStatement("listByOperatorId"), operatorId);
	}

	/**
	 * 根据角色ID查找该操作员关联的操作员.
	 * 
	 * @param roleId
	 * @return
	 */
	public List<OperatorRole> listByRoleId(Long roleId) {
		return super.getSqlSession().selectList(getStatement("listByRoleId"), roleId);
	}

	/**
	 * 根据操作员ID删除与角色的关联记录.
	 * 
	 * @param operatorId
	 *            .
	 */
	public void deleteByOperatorId(Long operatorId) {

		super.getSqlSession().delete(getStatement("deleteByOperatorId"), operatorId);
	}

	/**
	 * 根据角色ID删除操作员与角色的关联关系.
	 * 
	 * @param roleId
	 *            .
	 */
	public void deleteByRoleId(Long roleId) {
		super.getSqlSession().delete(getStatement("deleteByRoleId"), roleId);
	}

	/**
	 * 根据角色ID和操作员ID删除关联数据(用于更新操作员的角色).
	 * 
	 * @param roleId
	 *            角色ID.
	 * @param operatorId
	 *            操作员ID.
	 */
	@Override
	public void deleteByRoleIdAndOperatorId(Long roleId, Long operatorId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleId", roleId);
		paramMap.put("operatorId", operatorId);
		super.getSqlSession().delete(getStatement("deleteByRoleIdAndOperatorId"), paramMap);
	}

}
