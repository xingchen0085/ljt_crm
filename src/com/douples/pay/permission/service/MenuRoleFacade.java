package com.douples.pay.permission.service;

import com.douples.pay.permission.entity.Operator;

/**
 * 菜单角色接口
 * <p>Title: MenuRoleFacade</p>
 * <p>Description: 菜单角色接口</p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
public interface MenuRoleFacade {

    /**
     * 根据角色ID统计关联到此角色的菜单数.
     *
     * @param roleId 角色ID.
     * @return count.
     */
    public int countMenuByRoleId(Long roleId);

    /**
     * 根据角色id，删除该角色关联的所有菜单权限
     *
     * @param roleId
     */
    public void deleteByRoleId(Long roleId);

    public void saveRoleMenu(Long roleId, String roleMenuStr, Operator operator);

}
