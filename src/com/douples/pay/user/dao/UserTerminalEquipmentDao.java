package com.douples.pay.user.dao;

import com.douples.pay.common.dao.BaseDao;
import com.douples.pay.user.entity.UserTerminalEquipment;

/**
 * @ClassName UserTerminalEquipmentDao
 * @Description 商户终端设备DAO
 * @Author luohailong
 * @Date 2018/9/19
 **/
public interface UserTerminalEquipmentDao extends BaseDao<UserTerminalEquipment> {

    void updatePosKeys(UserTerminalEquipment userTerminalEquipment);
}
