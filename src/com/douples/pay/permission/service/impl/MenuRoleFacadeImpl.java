package com.douples.pay.permission.service.impl;

import java.util.List;

import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.permission.entity.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.douples.pay.permission.dao.MenuRoleDao;
import com.douples.pay.permission.entity.MenuRole;
import com.douples.pay.permission.service.MenuRoleFacade;

/**
 * 菜单角色接口实现
 * <p>Title: MenuRoleFacadeImpl</p>
 * <p>Description: 菜单角色接口实现</p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
@Service("pmsMenuRoleService")
public class MenuRoleFacadeImpl implements MenuRoleFacade {

    @Autowired
    private MenuRoleDao menuRoleDao;

    /**
     * 根据角色ID统计关联到此角色的菜单数.
     *
     * @param roleId 角色ID.
     * @return count.
     */
    public int countMenuByRoleId(Long roleId) {
        List<MenuRole> meunList = menuRoleDao.listByRoleId(roleId);
        if (meunList == null || meunList.isEmpty()) {
            return 0;
        } else {
            return meunList.size();
        }
    }

    /**
     * 根据角色id，删除该角色关联的所有菜单权限
     *
     * @param roleId
     */
    public void deleteByRoleId(Long roleId) {
        menuRoleDao.deleteByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenu(Long roleId, String roleMenuStr, Operator operator) {
        // 删除原来的角色与权限关联
        menuRoleDao.deleteByRoleId(roleId);
        if (!StringUtils.isEmpty(roleMenuStr)) {
            // 创建新的关联
            String[] menuIds = roleMenuStr.split(",");
            for (int i = 0; i < menuIds.length; i++) {
                Long menuId = Long.valueOf(menuIds[i]);
                MenuRole item = new MenuRole();
                item.setMenuId(menuId);
                item.setRoleId(roleId);

                PropertyUtils.addDefaultProperty(item, PropertyAddFlagEnum.INSERT,
                        operator.getId(), operator.getRealName());
                menuRoleDao.insert(item);
            }
        }
    }
}
