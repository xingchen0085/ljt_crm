package com.douples.pay.user.dao;

import com.douples.pay.common.dao.BaseDao;
import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.user.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户DAO
 * <p>Title: UserInfoDao</p>
 * <p>Description: 用户DAO</p>
 *
 * @author hexuefeng
 * @date 2017-11-30
 */
public interface UserInfoDao extends BaseDao<UserInfo> {
    /**
     * 重置密码
     */
    void reSetPwd(UserInfo userInfo);

    /**
     * 查询：状态可用 + 未与商户绑定的用户
     * @return
     */
    PageBean listPageWithStatusAndUserNo(PageParam pageParam, Map<String, Object> paramMap);

}