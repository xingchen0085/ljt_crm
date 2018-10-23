package com.douples.pay.user.service;

import java.util.List;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.user.entity.UserInfo;

/**
 * 用户信息接口
 * <p>Title: UserInfoFacade</p>
 * <p>Description: 用户信息接口</p>
 *
 * @author hexuefeng
 * @date 2017-11-30
 */
public interface UserInfoFacade {

    /**
     * 保存
     */
    void saveData(UserInfo userInfo);

    /**
     * 更新
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 根据id获取数据
     *
     * @param id
     * @return
     */
    UserInfo getById(String id);


    /**
     * 获取分页数据
     *
     * @param pageParam
     * @return
     */
    PageBean listPage(PageParam pageParam, UserInfo userInfo);

    /**
     * 用户线下注册
     *
     * @param operator 当前用户
     * @param userName 用户名
     * @param mobile   手机号
     * @param password 密码
     * @return
     */
    void registerOffline(UserInfo userInfo);

    /**
     * 根据商户编号获取商户信息
     *
     * @param merchantNo
     * @return
     */
    UserInfo getDataByMerchentNo(String merchantNo);

    /**
     * 根据手机号获取商户信息
     *
     * @param mobile
     * @return
     */
    UserInfo getDataByMobile(String mobile);

    /**
     * 获取所有用户
     *
     * @return
     */
    List<UserInfo> listAll();

    void deleteUserInfo(String id);

    /**
     * 获取已激活/未激活 的用户
     */
    PageBean listPageWithStatus(PageParam pageParam, UserInfo userInfo);

    /**
     * 重置密码
     */
    void reSetPwd(UserInfo userInfo);

    /**
     * 查询用户，不考虑状态
     */
    UserInfo getDataByUserNoWithoutStatus(String merchantNo);

    /***
     * 查询用户，状态+未绑定
     * @param pageParam
     * @param userInfo
     * @return
     */
    PageBean listPageWithStatusAndUserNo(PageParam pageParam, UserInfo userInfo);

    UserInfo getByAccountNo(String accountNo);
}