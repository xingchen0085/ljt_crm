package com.douples.pay.permission.service;

import java.util.List;
import java.util.Map;

import com.douples.pay.permission.entity.Menu;

/**
 * 菜单接口
 * <p>Title: MenuFacade</p>  
 * <p>Description: 菜单接口</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface MenuFacade {

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 */
	public void savaMenu(Menu menu);

	/**
	 * 根据父菜单ID获取该菜单下的所有子孙菜单.<br/>
	 * 
	 * @param parentId
	 *            (如果为空，则为获取所有的菜单).<br/>
	 * @return menuList.
	 */
	@SuppressWarnings("rawtypes")
	public List getListByParent(Long parentId);

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 根据角色id串获取菜单
	 * 
	 * @param roleIdsStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByRoleIds(String roleIdsStr);

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
	 * 根据菜单ID获取菜单.
	 * 
	 * @param pid
	 * @return
	 */
	public Menu getById(Long pid);

	/**
	 * 更新菜单.
	 * 
	 * @param menu
	 */
	public void update(Menu menu);

	/**
	 * 根据角色查找角色对应的菜单ID集
	 * 
	 * @param roleId
	 * @return
	 */
	public String getMenuIdsByRoleId(Long roleId);

	/**
	 * 根据操作员ID 获取菜单
	 * <p>Title: listByOperatorId</p>  
	 * <p>Description: </p>  
	 * @param operatorId
	 * @return
	 */
	public List listMenuDataByOperatorId(Long operatorId);
}
