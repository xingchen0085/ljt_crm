package com.douples.pay.user.dao;

import com.douples.pay.common.dao.BaseDao;
import com.douples.pay.user.entity.UserPayConfig;

/**
 * 用户支付配置DAO
 * <p>Title: UserPayConfigDao</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface UserPayConfigDao  extends BaseDao<UserPayConfig> {

    /**
     * 根据用户编号获取用户支付信息
     * @param userNo
     * @return
     */
    UserPayConfig getByMerchantNo(String userNo, String auditStatus);

    int updatePosKeys(UserPayConfig payConfig);
}