package com.douples.pay.permission.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.utils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douples.pay.permission.dao.OperatorDao;
import com.douples.pay.permission.dao.OperatorRoleDao;
import com.douples.pay.permission.dao.RoleDao;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.entity.OperatorRole;
import com.douples.pay.permission.entity.Role;
import com.douples.pay.permission.service.OperatorRoleFacade;

/**
 * 操作员角色接口实现
 * <p>Title: OperatorRoleFacadeImpl</p>  
 * <p>Description: 操作员角色接口实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Service("pmsOperatorRoleService")
public class OperatorRoleFacadeImpl implements OperatorRoleFacade {
	@Autowired
	private OperatorRoleDao operatorRoleDao;

	@Autowired
	private OperatorDao operatorDao;

	@Autowired
	private RoleDao roleDao;

	/**
	 * 根据操作员ID获得该操作员的所有角色id所拼成的String，每个ID用“,”分隔
	 * 
	 * @param operatorId
	 *            操作员ID
	 * @return roleIds
	 */
	public String getRoleIdsByOperatorId(Long operatorId) {
		// 得到操作员和角色列表
		List<OperatorRole> rpList = operatorRoleDao.listByOperatorId(operatorId);
		// 构建StringBuffer来拼字符串
		StringBuffer roleIdsBuf = new StringBuffer("");
		for (OperatorRole rp : rpList) {
			roleIdsBuf.append(rp.getRoleId()).append(",");
		}
		String roleIds = roleIdsBuf.toString();
		// 截取字符串
		if (StringUtils.isNotBlank(roleIds) && roleIds.length() > 0) {
			roleIds = roleIds.substring(0, roleIds.length() - 1);
		}
		return roleIds;
	}

	/**
	 * 根据操作员id，获取该操作员所有角色的编码集合
	 * 
	 * @param operatorId
	 * @return
	 */
	public Set<String> getRoleCodeByOperatorId(Long operatorId) {
		// 得到操作员和角色列表
		List<OperatorRole> rpList = operatorRoleDao.listByOperatorId(operatorId);
		Set<String> roleCodeSet = new HashSet<String>();

		for (OperatorRole rp : rpList) {
			Long roleId = rp.getRoleId();
			Role role = roleDao.getById(roleId);
			if (role == null) {
				continue;
			}
			roleCodeSet.add(role.getRoleCode());
		}

		return roleCodeSet;

	}

	/**
	 * 根据角色ID统计有多少个操作员关联到此角色.
	 * 
	 * @param roleId
	 *            .
	 * @return count.
	 */
	public int countOperatorByRoleId(Long roleId) {
		List<OperatorRole> operatorList = operatorRoleDao.listByRoleId(roleId);
		if (operatorList == null || operatorList.isEmpty()) {
			return 0;
		} else {
			return operatorList.size();
		}
	}

	/**
	 * 根据操作员ID获得所有操作员－角色关联列表
	 */
	public List<OperatorRole> listOperatorRoleByOperatorId(Long operatorId) {
		return operatorRoleDao.listByOperatorId(operatorId);
	}

	/**
	 * 保存操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param OperatorRoleStr
	 *            .
	 */
	public void saveOperator(Operator pmsOperator, String OperatorRoleStr,Operator operator) {
		// 保存操作员信息
		operatorDao.insert(pmsOperator);
		// 保存角色关联信息
		if (StringUtils.isNotBlank(OperatorRoleStr) && OperatorRoleStr.length() > 0) {
			saveOrUpdateOperatorRole(pmsOperator.getId(), OperatorRoleStr,operator);
		}
	}

	/**
	 * 根据角色ID查询用户
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Operator> listOperatorByRoleId(Long roleId) {
		return operatorDao.listByRoleId(roleId);
	}

	/**
	 * 修改操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param OperatorRoleStr
	 *            .
	 */
	public void updateOperator(Operator pmsOperator, String OperatorRoleStr,Operator operator) {
		operatorDao.update(pmsOperator);
		// 更新角色信息
		saveOrUpdateOperatorRole(pmsOperator.getId(), OperatorRoleStr,operator);
	}

	/**
	 * 保存用户和角色之间的关联关系
	 */
	private void saveOrUpdateOperatorRole(Long operatorId, String roleIdsStr,Operator operator) {
		// 删除原来的角色与操作员关联
		List<OperatorRole> listPmsOperatorRoles = operatorRoleDao.listByOperatorId(operatorId);
		Map<Long, OperatorRole> delMap = new HashMap<Long, OperatorRole>();
		for (OperatorRole pmsOperatorRole : listPmsOperatorRoles) {
			delMap.put(pmsOperatorRole.getRoleId(), pmsOperatorRole);
		}
		if (StringUtils.isNotBlank(roleIdsStr)) {
			// 创建新的关联
			String[] roleIds = roleIdsStr.split(",");
			for (int i = 0; i < roleIds.length; i++) {
				Long roleId = Long.valueOf(roleIds[i]);
				if (delMap.get(roleId) == null) {
					OperatorRole pmsOperatorRole = new OperatorRole();
					pmsOperatorRole.setOperatorId(operatorId);
					pmsOperatorRole.setRoleId(roleId);

                    PropertyUtils.addDefaultProperty(pmsOperatorRole, PropertyAddFlagEnum.INSERT,
                            operator.getId(), operator.getRealName());
					operatorRoleDao.insert(pmsOperatorRole);
				} else {
					delMap.remove(roleId);
				}
			}
		}

		Iterator<Long> iterator = delMap.keySet().iterator();
		while (iterator.hasNext()) {
			Long roleId = iterator.next();
			operatorRoleDao.deleteByRoleIdAndOperatorId(roleId, operatorId);
		}
	}

}
