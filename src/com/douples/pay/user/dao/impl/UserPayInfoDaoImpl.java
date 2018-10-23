package com.douples.pay.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.douples.pay.common.dao.impl.BaseDaoImpl;
import com.douples.pay.user.dao.UserPayInfoDao;
import com.douples.pay.user.entity.UserPayInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户第三方支付信息dao实现类
 * <p>Title: UserPayInfoDaoImpl</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Repository
public class UserPayInfoDaoImpl  extends BaseDaoImpl<UserPayInfo> implements UserPayInfoDao{
    /**
     * 通过商户编号获取商户第三方支付信息
     *
     * @param userNo
     * @param payWayCode
     * @return
     */
    @Override
    public UserPayInfo getByUserNo(String userNo, String payWayCode) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("userNo",userNo);
        paramMap.put("payWayCode",payWayCode);
        return super.getBy(paramMap);
    }
}