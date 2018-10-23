package com.douples.pay.permission.dao;

import java.util.List;
import java.util.Map;

import com.douples.pay.permission.entity.Menu;

/**
 * 菜单DAO
 * <p>Title: MenuDao</p>  
 * <p>Description: 菜单DAO</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface MenuDao extends PermissionBaseDao<Menu> {
	/**
	 * 根据角色id查找菜单列表
	 * 
	 * @param roleIdsStr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List listByRoleIds(String roleIdsStr);

	/**
	 * 根据父菜单ID获取该菜单下的所有子孙菜单.<br/>
	 * 
	 * @param parentId
	 *            (如果为空，则为获取所有的菜单).<br/>
	 * @return menuList.
	 */
	@SuppressWarnings("rawtypes")
	public List listByParent(Long parentId);

	/**
	 * 根据菜单ID查找菜单（可用于判断菜单下是否还有子菜单）.
	 * 
	 * @param parentId
	 *            .
	 * @return menuList.
	 */
	public List<Menu> listByParentId(Long parentId);

	/***
	 * 根据名称和是否叶子节点查询数据
	 * 
	 * @param isLeaf
	 *            是否是叶子节点
	 * @param name
	 *            节点名称
	 * @return
	 */
	public List<Menu> getMenuByNameAndIsLeaf(Map<String, Object> map);

	
	/**
	 * 根据操作员ID获取菜单数据
	 * 
	 * @param operatorId 操作源ID
	 * */
	@SuppressWarnings("rawtypes")
	public List listMenuDataByOperatorId(Long operatorId) ;
}