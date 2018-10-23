package com.douples.pay.permission.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douples.pay.permission.dao.MenuDao;
import com.douples.pay.permission.dao.MenuRoleDao;
import com.douples.pay.permission.entity.Menu;
import com.douples.pay.permission.entity.MenuRole;
import com.douples.pay.permission.service.MenuFacade;

/**
 * 菜单接口实现
 * <p>Title: MenuFacadeImpl</p>  
 * <p>Description: 菜单接口实现</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Service("pmsMenuService")
public class MenuFacadeImpl implements MenuFacade {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private  MenuRoleDao menuRoleDao;

	/**
	 * 保存菜单menuDao
	 * 
	 * @param menu
	 */
	public void savaMenu(Menu menu) {
		menuDao.insert(menu);
	}

	/**
	 * 根据父菜单ID获取该菜单下的所有子孙菜单.<br/>
	 * 
	 * @param parentId
	 *            (如果为空，则为获取所有的菜单).<br/>
	 * @return menuList.
	 */
	@SuppressWarnings("rawtypes")
	public List getListByParent(Long parentId) {
		return menuDao.listByParent(parentId);
	}

	/**
	 * 根据id删除菜单
	 */
	public void delete(Long id) {
		this.menuDao.delete(id);
	}

	/**
	 * 根据角色id串获取菜单
	 * 
	 * @param roleIdsStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByRoleIds(String roleIdsStr) {
		return this.menuDao.listByRoleIds(roleIdsStr);
	}

	/**
	 * 根据菜单ID查找菜单（可用于判断菜单下是否还有子菜单）.
	 * 
	 * @param parentId
	 *            .
	 * @return menuList.
	 */
	public List<Menu> listByParentId(Long parentId) {
		return menuDao.listByParentId(parentId);
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
		return menuDao.getMenuByNameAndIsLeaf(map);
	}

	/**
	 * 根据菜单ID获取菜单.
	 * 
	 * @param pid
	 * @return
	 */
	public Menu getById(Long pid) {
		return menuDao.getById(pid);
	}

	/**
	 * 更新菜单.
	 * 
	 * @param menu
	 */
	public void update(Menu menu) {
		menuDao.update(menu);

	}

	/**
	 * 根据角色查找角色对应的菜单ID集
	 * 
	 * @param roleId
	 * @return
	 */
	public String getMenuIdsByRoleId(Long roleId) {
		List<MenuRole> menuList = menuRoleDao.listByRoleId(roleId);
		StringBuffer menuIds = new StringBuffer("");
		if (menuList != null && !menuList.isEmpty()) {
			for (MenuRole rm : menuList) {
				menuIds.append(rm.getMenuId()).append(",");
			}
		}
		return menuIds.toString();

	}

	/**
	 * 根据操作员ID获取菜单数据
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List listMenuDataByOperatorId(Long operatorId) {
		List menuList = menuDao.listMenuDataByOperatorId(operatorId);
		return menuList;
	}
}
