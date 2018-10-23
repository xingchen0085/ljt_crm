package com.douples.pay.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.dao.PermissionDao;
import com.douples.pay.permission.dao.RolePermissionDao;
import com.douples.pay.permission.entity.Permission;
import com.douples.pay.permission.entity.RolePermission;
import com.douples.pay.permission.service.PermissionFacade;

/**
 * 权限接口实现
 * <p>Title: PermissionFacadeImpl</p>  
 * <p>Description: 权限接口实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Service("pmsPermissionService")
public class PermissionFacadeImpl implements PermissionFacade {
	@Autowired
	private PermissionDao pmsPermissionDao;
	@Autowired
	private RolePermissionDao pmsRolePermissionDao;

	/**
	 * 创建pmsOperator
	 */
	public void saveData(Permission pmsPermission) {
		pmsPermissionDao.insert(pmsPermission);
	}

	/**
	 * 修改pmsOperator
	 */
	public void updateData(Permission pmsPermission) {
		pmsPermissionDao.update(pmsPermission);
	}

	/**
	 * 根据id获取数据pmsOperator
	 * 
	 * @param id
	 * @return
	 */
	public Permission getDataById(Long id) {
		return pmsPermissionDao.getById(id);

	}

	/**
	 * 分页查询pmsOperator
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsOperator
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Permission pmsPermission) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("permissionName", pmsPermission.getPermissionName()); // 权限名称（模糊查询）
		paramMap.put("permission", pmsPermission.getPermission()); // 权限（精确查询）
		return pmsPermissionDao.listPage(pageParam, paramMap);
	}

	/**
	 * 检查权限名称是否已存在
	 * 
	 * @param trim
	 * @return
	 */
	public Permission getByPermissionName(String permissionName) {
		return pmsPermissionDao.getByPermissionName(permissionName);
	}

	/**
	 * 检查权限是否已存在
	 * 
	 * @param permission
	 * @return
	 */
	public Permission getByPermission(String permission) {
		return pmsPermissionDao.getByPermission(permission);
	}

	/**
	 * 检查权限名称是否已存在(其他id)
	 * 
	 * @param permissionName
	 * @param id
	 * @return
	 */
	public Permission getByPermissionNameNotEqId(String permissionName, Long id) {
		return pmsPermissionDao.getByPermissionNameNotEqId(permissionName, id);
	}

	/**
	 * pmsPermissionDao 删除
	 * 
	 * @param permission
	 */
	public void delete(Long permissionId) {
		pmsPermissionDao.delete(permissionId);
	}

	/**
	 * 根据角色查找角色对应的功能权限ID集
	 * 
	 * @param roleId
	 * @return
	 */
	public String getPermissionIdsByRoleId(Long roleId) {
		List<RolePermission> rmList = pmsRolePermissionDao.listByRoleId(roleId);
		StringBuffer actionIds = new StringBuffer();
		if (rmList != null && !rmList.isEmpty()) {
			for (RolePermission rm : rmList) {
				actionIds.append(rm.getPermissionId()).append(",");
			}
		}
		return actionIds.toString();
	}

	/**
	 * 查询所有的权限
	 */
	public List<Permission> listAll() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return pmsPermissionDao.listBy(paramMap);
	}
}
