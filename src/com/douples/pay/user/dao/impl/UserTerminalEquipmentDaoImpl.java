package com.douples.pay.user.dao.impl;

import com.douples.pay.common.dao.impl.BaseDaoImpl;
import com.douples.pay.user.dao.UserTerminalEquipmentDao;
import com.douples.pay.user.entity.UserTerminalEquipment;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserTerminalEquipmentDaoImpl
 * @Description 商户终端设备DAO实现类
 * @Author luohailong
 * @Date 2018/9/20
 **/
@Repository("userTerminalEquipmentDao")
public class UserTerminalEquipmentDaoImpl extends BaseDaoImpl<UserTerminalEquipment> implements UserTerminalEquipmentDao {

    public void updatePosKeys(UserTerminalEquipment userTerminalEquipment){
        getSessionTemplate().update("updatePosKeys",userTerminalEquipment);
    }
}
