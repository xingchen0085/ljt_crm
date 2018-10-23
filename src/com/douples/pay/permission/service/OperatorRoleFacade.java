package com.douples.pay.permission.service;

import java.util.List;
import java.util.Set;

import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.entity.OperatorRole;

/**
 * 操作员角色接口
 * <p>Title: OperatorRoleFacade</p>  
 * <p>Description: 操作员角色接口</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface OperatorRoleFacade {

	/**
	 * 根据操作员ID获得该操作员的所有角色id所拼成的String，每个ID用“,”分隔
	 * 
	 * @param operatorId
	 *            操作员ID
	 * @return roleIds
	 */
	public String getRoleIdsByOperatorId(Long operatorId);

	/**
	 * 根据操作员id，获取该操作员所有角色的编码集合
	 * 
	 * @param operatorId
	 * @return
	 */
	public Set<String> getRoleCodeByOperatorId(Long operatorId);

	/**
	 * 根据角色ID查询用户
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Operator> listOperatorByRoleId(Long roleId);

	/**
	 * 保存操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	public void saveOperator(Operator pmsOperator, String roleOperatorStr,Operator operator);

	/**
	 * 修改操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	public void updateOperator(Operator pmsOperator, String roleOperatorStr,Operator operator);

	/**
	 * 根据角色ID统计有多少个操作员关联到此角色.
	 * 
	 * @param roleId
	 *            .
	 * @return count.
	 */
	public int countOperatorByRoleId(Long roleId);

	/**
	 * 根据操作员ID获得所有操作员－角色关联列表
	 */
	public List<OperatorRole> listOperatorRoleByOperatorId(Long operatorId);
	
	

}
