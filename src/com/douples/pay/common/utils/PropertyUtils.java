/**
 * <p>Title: PropertyUtils.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: www.douples.com</p>
 *
 * @author hexuefeng
 * @date 2017-12-26
 * @version 1.0
 */
package com.douples.pay.common.utils;

import java.util.Date;

import com.douples.pay.common.entity.BaseEntity;
import com.douples.pay.common.entity.PermissionBaseEntity;
import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.enums.StatusEnum;

/**
 * 添加公共属性
 * <p>Title: PropertyUtils</p>
 * <p>Description: </p>
 *
 * @author hexuefeng
 * @date 2017-12-26
 */
public class PropertyUtils {

    /**
     * 添加默认属性
     * <p>Title: addDefaultProperty</p>
     * <p>Description: </p>
     *
     * @param entity       添加实体
     * @param methodName   添加的方式  （主要是添加和修改两种）
     * @param operatorId   操作员ID
     * @param operatorName 操作员真实姓名
     */
    public static void addDefaultProperty(BaseEntity entity, PropertyAddFlagEnum methodName, Long operatorId, String operatorName) {
        Date date = new Date();
        if (PropertyAddFlagEnum.INSERT.equals(methodName)) {
            entity.setCreateDate(date);
            entity.setCreateTime(date);
            entity.setUpdateDate(date);
            entity.setUpdateTime(date);
            entity.setCreateUserId(operatorId);
            entity.setCreateUserName(operatorName);
            entity.setUpdateUserId(operatorId);
            entity.setUpdateUserName(operatorName);

            //设置默认状态为：可用  --cxh 2-5
            entity.setStatus(StatusEnum.ENABLE.getKey());

        } else if (PropertyAddFlagEnum.UPDATE.equals(methodName)) {
            entity.setUpdateDate(date);
            entity.setUpdateTime(date);
            entity.setUpdateUserId(operatorId);
            entity.setUpdateUserName(operatorName);
        }
    }

    /**
     * 针对权限和菜单等
     * @param entity
     * @param methodName
     * @param operatorId
     * @param operatorName
     */
    public static void addDefaultProperty(PermissionBaseEntity entity, PropertyAddFlagEnum methodName, Long operatorId, String operatorName) {
        Date date = new Date();
        if (PropertyAddFlagEnum.INSERT.equals(methodName)) {
            entity.setCreateDate(date);
            entity.setCreateTime(date);
            entity.setUpdateDate(date);
            entity.setUpdateTime(date);
            entity.setCreateUserId(operatorId);
            entity.setCreateUserName(operatorName);
            entity.setUpdateUserId(operatorId);
            entity.setUpdateUserName(operatorName);

        } else if (PropertyAddFlagEnum.UPDATE.equals(methodName)) {
            entity.setUpdateDate(date);
            entity.setUpdateTime(date);
            entity.setUpdateUserId(operatorId);
            entity.setUpdateUserName(operatorName);
        }
    }
}
