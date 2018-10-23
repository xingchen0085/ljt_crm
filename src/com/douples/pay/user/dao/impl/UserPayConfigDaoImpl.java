package com.douples.pay.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.douples.pay.common.dao.impl.BaseDaoImpl;
import com.douples.pay.user.dao.UserPayConfigDao;
import com.douples.pay.user.entity.UserPayConfig;
import com.douples.pay.common.enums.StatusEnum;

/**
 * 用户支付配置dao实现类
 * <p>Title: UserPayConfigDaoImpl</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Repository
public class UserPayConfigDaoImpl  extends BaseDaoImpl<UserPayConfig> implements UserPayConfigDao{
    @Override
    public UserPayConfig getByMerchantNo(String merchantNo, String status) {
        Map<String , Object> paramMap = new HashMap<String , Object>();
        paramMap.put("merchantNo",merchantNo);
        paramMap.put("status", status);
        //payParamMap.put("auditStatus", auditStatus);
        return super.getBy(paramMap);
    }

    @Override
    public int updatePosKeys(UserPayConfig payConfig) {
        return getSessionTemplate().update("updatePosKeys",payConfig);
    }
}