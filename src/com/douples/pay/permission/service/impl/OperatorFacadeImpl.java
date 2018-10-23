package com.douples.pay.permission.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douples.pay.common.enums.PublicStatusEnum;
import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.dao.OperatorDao;
import com.douples.pay.permission.dao.OperatorRoleDao;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.entity.OperatorRole;
import com.douples.pay.permission.service.OperatorFacade;

/**
 * 操作员接口实现
 * <p>Title: OperatorFacadeImpl</p>  
 * <p>Description: 操作员接口实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Service("pmsOperatorService")
public class OperatorFacadeImpl implements OperatorFacade {
	@Autowired
	private OperatorDao operatorDao;

	@Autowired
	private OperatorRoleDao operatorRoleDao;

	/**
	 * 创建pmsOperator
	 */
	public void saveData(Operator pmsOperator) {
		operatorDao.insert(pmsOperator);
	}

	/**
	 * 修改pmsOperator
	 */
	public void updateData(Operator pmsOperator) {
		operatorDao.update(pmsOperator);
	}

	/**
	 * 根据id获取数据pmsOperator
	 * 
	 * @param id
	 * @return
	 */
	public Operator getDataById(Long id) {
		return operatorDao.getById(id);

	}

	/**
	 * 分页查询pmsOperator
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsOperator
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Operator pmsOperator) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", pmsOperator.getLoginName()); // 操作员登录名（精确查询）
		paramMap.put("realName", pmsOperator.getRealName()); // 操作员姓名（模糊查询）
		paramMap.put("status", pmsOperator.getStatus()); // 状态

		return operatorDao.listPage(pageParam, paramMap);
	}

	/**
	 * 根据ID删除一个操作员，同时删除与该操作员关联的角色关联信息. type="1"的超级管理员不能删除.
	 * 
	 * @param id
	 *            操作员ID.
	 */
	public void deleteOperatorById(Long operatorId) {
		Operator pmsOperator = operatorDao.getById(operatorId);
		if (pmsOperator != null) {
			if ("admin".equals(pmsOperator.getType())) {
				throw new RuntimeException("【" + pmsOperator.getLoginName() + "】为超级管理员，不能删除！");
			}
			operatorDao.delete(operatorId);
			// 删除原来的角色与操作员关联
			operatorRoleDao.deleteByOperatorId(operatorId);
		}
	}

	/**
	 * 更新操作员信息.
	 * 
	 * @param operator
	 */
	public void update(Operator operator) {
		operatorDao.update(operator);

	}

	/**
	 * 根据操作员ID更新操作员密码.
	 * 
	 * @param operatorId
	 * @param newPwd
	 *            (已进行SHA1加密)
	 */
	public void updateOperatorPwd(Long operatorId, String newPwd) {
		Operator pmsOperator = operatorDao.getById(operatorId);
		pmsOperator.setLoginPwd(newPwd);
		operatorDao.update(pmsOperator);
	}

	/**
	 * 根据登录名取得操作员对象
	 */
	public Operator findOperatorByLoginName(String loginName) {
		return operatorDao.findByLoginName(loginName);
	}

	/**
	 * 保存操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */

	@Transactional
	public void saveOperator(Operator pmsOperator, String roleOperatorStr) {
		// 保存操作员信息
		operatorDao.insert(pmsOperator);
		// 保存角色关联信息
		if (StringUtils.isNotBlank(roleOperatorStr) && roleOperatorStr.length() > 0) {
			saveOrUpdateOperatorRole(pmsOperator, roleOperatorStr);
		}
	}

	/**
	 * 保存用户和角色之间的关联关系
	 */
	private void saveOrUpdateOperatorRole(Operator pmsOperator, String roleIdsStr) {
		// 删除原来的角色与操作员关联
		List<OperatorRole> listPmsOperatorRoles = operatorRoleDao.listByOperatorId(pmsOperator.getId());
		Map<Long, OperatorRole> delMap = new HashMap<Long, OperatorRole>();
		for (OperatorRole pmsOperatorRole : listPmsOperatorRoles) {
			delMap.put(pmsOperatorRole.getRoleId(), pmsOperatorRole);
		}
		if (StringUtils.isNotBlank(roleIdsStr)) {
			// 创建新的关联
			String[] roleIds = roleIdsStr.split(",");
			for (int i = 0; i < roleIds.length; i++) {
				long roleId = Long.parseLong(roleIds[i]);
				if (delMap.get(roleId) == null) {
					OperatorRole pmsOperatorRole = new OperatorRole();
					pmsOperatorRole.setOperatorId(pmsOperator.getId());
					pmsOperatorRole.setRoleId(roleId);
					pmsOperatorRole.setCreateUserName(pmsOperator.getCreateUserName());
					pmsOperatorRole.setCreateTime(new Date());
					pmsOperatorRole.setStatus(PublicStatusEnum.ACTIVE.name());
					operatorRoleDao.insert(pmsOperatorRole);
				} else {
					delMap.remove(roleId);
				}
			}
		}

		Iterator<Long> iterator = delMap.keySet().iterator();
		while (iterator.hasNext()) {
			long roleId = iterator.next();
			operatorRoleDao.deleteByRoleIdAndOperatorId(roleId, pmsOperator.getId());
		}
	}

	/**
	 * 修改操作員信息及其关联的角色.
	 * 
	 * @param pmsOperator
	 *            .
	 * @param roleOperatorStr
	 *            .
	 */
	public void updateOperator(Operator pmsOperator, String roleOperatorStr) {
		operatorDao.update(pmsOperator);
		// 更新角色信息
		this.saveOrUpdateOperatorRole(pmsOperator, roleOperatorStr);
	}

}
