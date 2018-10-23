package com.douples.pay.permission.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.douples.pay.permission.dao.MenuDao;
import com.douples.pay.permission.entity.Menu;

/**
 * 菜单DAO实现类
 * <p>Title: MenuDaoImpl</p>  
 * <p>Description: 菜单DAO实现类</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Repository("pmsMenuDao")
public class MenuDaoImpl extends PermissionBaseDaoImpl<Menu> implements MenuDao {

	@SuppressWarnings("rawtypes")
	@Override
	public List listByRoleIds(String roleIdsStr) {
		List<String> roldIds = Arrays.asList(roleIdsStr.split(","));
		return super.getSqlSession().selectList(getStatement("listByRoleIds"), roldIds);
	}

	/**
	 * 根据父菜单ID获取该菜单下的所有子孙菜单.<br/>
	 * 
	 * @param parentId
	 *            (如果为空，则为获取所有的菜单).<br/>
	 * @return menuList.
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List listByParent(Long parentId) {
		return super.getSqlSession().selectList(getStatement("listByParent"), parentId);
	}

	/**
	 * 根据菜单ID查找菜单（可用于判断菜单下是否还有子菜单）.
	 * 
	 * @param parentId
	 *            .
	 * @return menuList.
	 */
	@Override
	public List<Menu> listByParentId(Long parentId) {
		return super.getSqlSession().selectList(getStatement("listByParentId"), parentId);
	}

	/***
	 * 根据名称和是否叶子节点查询数据
	 * 
	 * @param isLeaf
	 *            是否是叶子节点
	 * @param name
	 *            节点名称
	 * @return
	 */
	public List<Menu> getMenuByNameAndIsLeaf(Map<String, Object> map) {
		return super.getSqlSession().selectList(getStatement("listBy"), map);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List listMenuDataByOperatorId(Long operatorId) {
		return super.getSqlSession().selectList(getStatement("listMenuDataByOperatorId"), operatorId);
	}
}