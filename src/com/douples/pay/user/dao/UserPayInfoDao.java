package com.douples.pay.user.dao;

import com.douples.pay.common.dao.BaseDao;
import com.douples.pay.user.entity.UserPayInfo;

/**
 * 用户第三方支付信息DAO
 * <p>Title: UserPayInfoDao</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface UserPayInfoDao  extends BaseDao<UserPayInfo> {

    /**
     * 通过商户编号获取商户第三方支付信息
     * @param userNo
     * @param payWayCode
     * @return
     */
    public  UserPayInfo getByUserNo(String userNo, String payWayCode);

}