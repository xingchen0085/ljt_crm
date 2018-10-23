package com.douples.pay.permission.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.permission.entity.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.dao.PermissionDao;
import com.douples.pay.permission.dao.RolePermissionDao;
import com.douples.pay.permission.entity.Permission;
import com.douples.pay.permission.entity.RolePermission;
import com.douples.pay.permission.service.OperatorRoleFacade;
import com.douples.pay.permission.service.RolePermissionFacade;

/**
 * 角色权限接口实现
 * <p>Title: RolePermissionFacadeImpl</p>  
 * <p>Description: 角色权限接口实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Service("pmsRolePermissionService")
public class RolePermissionFacadeImpl implements RolePermissionFacade {
	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private OperatorRoleFacade operatorRoleFacade;

	/**
	 * 根据操作员ID，获取所有的功能权限集
	 * 
	 * @param operatorId
	 */
	public Set<String> getPermissionsByOperatorId(Long operatorId) {
		// 根据操作员Id查询出关联的所有角色id
		String roleIds = operatorRoleFacade.getRoleIdsByOperatorId(operatorId);

		String permissionIds = getActionIdsByRoleIds(roleIds);
		Set<String> permissionSet = new HashSet<String>();

		// 根据角色ID字符串得到该用户的所有权限拼成的字符串
		if (!StringUtils.isEmpty(permissionIds)) {
			List<Permission> permissions = permissionDao.findByIds(permissionIds);
			for (Permission permission : permissions) {
				permissionSet.add(permission.getPermission());
			}
		}
		return permissionSet;
	}

	/**
	 * 根据角色ID集得到所有权限ID集
	 * 
	 * @param roleIds
	 * @return actionIds
	 */
	private String getActionIdsByRoleIds(String roleIds) {
		// 得到角色－权限表中roleiId在ids中的所有关联对象
		List<RolePermission> listRolePermission = rolePermissionDao.listByRoleIds(roleIds); // 构建StringBuffer
		StringBuffer actionIdsBuf = new StringBuffer("");
		// 拼接字符串
		for (RolePermission pmsRolePermission : listRolePermission) {
			actionIdsBuf.append(pmsRolePermission.getPermissionId()).append(",");
		}
		String actionIds = actionIdsBuf.toString();
		// 截取字符串
		if (StringUtils.isEmpty(actionIds) && actionIds.length() > 0) {
			actionIds = actionIds.substring(0, actionIds.length() - 1); // 去掉最后一个逗号
		}
		return actionIds;
	}

	// /////////////////////////////下面：基本操作方法///////////////////////////////////////////////

	/**
	 * 创建pmsOperator
	 */
	public void saveData(RolePermission pmsRolePermission) {
		rolePermissionDao.insert(pmsRolePermission);
	}

	/**
	 * 修改pmsOperator
	 */
	public void updateData(RolePermission pmsRolePermission) {
		rolePermissionDao.update(pmsRolePermission);
	}

	/**
	 * 根据id获取数据pmsOperator
	 * 
	 * @param id
	 * @return
	 */
	public RolePermission getDataById(Long id) {
		return rolePermissionDao.getById(id);

	}

	/**
	 * 分页查询pmsOperator
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsOperator
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, RolePermission pmsRolePermission) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return rolePermissionDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 保存角色和权限之间的关联关系
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveRolePermission(Long roleId, String rolePermissionStr, Operator operator){
		// 删除原来的角色与权限关联
		rolePermissionDao.deleteByRoleId(roleId);
		if (!StringUtils.isEmpty(rolePermissionStr)) {
			// 创建新的关联
			String[] permissionIds = rolePermissionStr.split(",");
			for (int i = 0; i < permissionIds.length; i++) {
				Long permissionId = Long.valueOf(permissionIds[i]);
				RolePermission item = new RolePermission();
				item.setPermissionId(permissionId);
				item.setRoleId(roleId);

                PropertyUtils.addDefaultProperty(item, PropertyAddFlagEnum.INSERT,
                        operator.getId(), operator.getRealName());
				rolePermissionDao.insert(item);
			}
		}
	}

}
