package com.douples.pay.permission.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.dao.RoleDao;
import com.douples.pay.permission.entity.Role;
import com.douples.pay.permission.service.RoleFacade;

/**
 * 角色接口实现
 * <p>Title: RoleFacadeImpl</p>  
 * <p>Description: 角色接口实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Service("pmsRoleService")
public class RoleFacadeImpl implements RoleFacade {
	@Autowired
	private RoleDao roleDao;

	/**
	 * 创建pmsOperator
	 */
	public void saveData(Role pmsRole) {
		roleDao.insert(pmsRole);
	}

	/**
	 * 修改pmsOperator
	 */
	public void updateData(Role pmsRole) {
		roleDao.update(pmsRole);
	}

	/**
	 * 根据id获取数据pmsOperator
	 * 
	 * @param id
	 * @return
	 */
	public Role getDataById(Long id) {
		return roleDao.getById(id);

	}

	/**
	 * 分页查询pmsOperator
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsOperator
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Role pmsRole) {
		Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
		paramMap.put("roleName", pmsRole.getRoleName()); // 角色名称（模糊查询）
		return roleDao.listPage(pageParam, paramMap);
	}

	/**
	 * 获取所有角色列表，以供添加操作员时选择.
	 * 
	 * @return roleList .
	 */
	public List<Role> listAllRole() {
		return roleDao.listAll();
	}

	/**
	 * 判断此权限是否关联有角色
	 * 
	 * @param permissionId
	 * @return
	 */
	public List<Role> listByPermissionId(Long permissionId) {
		return roleDao.listByPermissionId(permissionId);
	}

	/**
	 * 根据角色名或者角色编号查询角色
	 * 
	 * @param roleName
	 * @param roleCode
	 * @return
	 */
	public Role getByRoleNameOrRoleCode(String roleName, String roleCode) {
		return roleDao.getByRoleNameOrRoleCode(roleName, roleCode);
	}

	/**
	 * 删除
	 * 
	 * @param roleId
	 */
	public void delete(Long roleId) {
		roleDao.delete(roleId);
	}
}
