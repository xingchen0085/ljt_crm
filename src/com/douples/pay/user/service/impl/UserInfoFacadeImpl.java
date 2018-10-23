package com.douples.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douples.pay.common.enums.StatusEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.user.dao.UserInfoDao;
import com.douples.pay.user.entity.UserInfo;
import com.douples.pay.user.service.BuildNoFacade;
import com.douples.pay.user.service.UserInfoFacade;
import com.douples.pay.common.utils.EncryptUtil;
import com.douples.pay.common.utils.StringUtil;

/**
 * 用户信息实现类
 * <p>Title: UserInfoFacadeImpl</p>
 * <p>Description: 用户信息实现类</p>
 *
 * @author hexuefeng
 * @date 2017-11-30
 * <p>
 * update chenxinghua
 */
@Service("userInfoFacade")
public class UserInfoFacadeImpl implements UserInfoFacade {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private BuildNoFacade buildNoFacade;

    @Override
    public void saveData(UserInfo userInfo) {
        userInfoDao.insert(userInfo);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        UserInfo byId = userInfoDao.getById(userInfo.getId());
        userInfo.setAccountNo(byId.getAccountNo());
//        userInfo.setUserNo(byId.getUserNo());
        userInfoDao.update(userInfo);
    }

    @Override
    public UserInfo getById(String id) {
        return userInfoDao.getById(id);
    }

    @Override
    public PageBean listPage(PageParam pageParam, UserInfo userInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", userInfo.getUserNo());
        //模糊查询
        paramMap.put("userName", userInfo.getUserName());
        return userInfoDao.listPage(pageParam, paramMap);
    }

    /**
     * 用户线下注册
     *
     * @param userName 用户名
     * @param mobile   手机号
     * @param password 密码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerOffline(UserInfo userInfo) {
        String accountNo = buildNoFacade.buildAccountNo();
        userInfo.setAccountNo(accountNo);
        userInfo.setCreateTime(new Date());
        userInfo.setId(StringUtil.get32UUID());
        userInfo.setPassword(EncryptUtil.encodeMD5String(userInfo.getPassword()));
        userInfo.setPayPwd(EncryptUtil.encodeMD5String("123456"));
        userInfo.setVersion(0);
        this.saveData(userInfo);
    }

    /**
     * 根据商户编号获取商户信息
     *
     * @param merchantNo
     * @return
     */
    @Override
    public UserInfo getDataByMerchentNo(String merchantNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", merchantNo);
        paramMap.put("status", StatusEnum.ENABLE.getKey());
        return userInfoDao.getBy(paramMap);
    }

    /**
     * 根据手机号获取商户信息
     *
     * @param mobile
     * @return
     */
    @Override
    public UserInfo getDataByMobile(String mobile) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mobile", mobile);
//        payParamMap.put("status", StatusEnum.ENABLE.getKey());
        return userInfoDao.getBy(paramMap);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @Override
    public List<UserInfo> listAll() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
//        payParamMap.put("status", StatusEnum.ENABLE.getKey());  查出全部，不带状态条件
        return userInfoDao.listBy(paramMap);
    }

    @Override
    public void deleteUserInfo(String id) {
        //账户信息仍然存留？
        userInfoDao.delete(id);
    }

    @Override
    public PageBean listPageWithStatus(PageParam pageParam, UserInfo userInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", StatusEnum.ENABLE.getKey());
        return userInfoDao.listPage(pageParam, paramMap);
    }

    @Override
    public void reSetPwd(UserInfo userInfo) {
        userInfoDao.reSetPwd(userInfo);
    }

    @Override
    public UserInfo getDataByUserNoWithoutStatus(String merchantNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", merchantNo);
        return userInfoDao.getBy(paramMap);
    }

    @Override
    public PageBean listPageWithStatusAndUserNo(PageParam pageParam, UserInfo userInfo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", StatusEnum.ENABLE.getKey());
        return userInfoDao.listPageWithStatusAndUserNo(pageParam, paramMap);
    }

    @Override
    public UserInfo getByAccountNo(String accountNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("accountNo", accountNo);
        List<UserInfo> userInfoList = userInfoDao.listBy(paramMap);
        if (null != userInfoList && userInfoList.size() > 0) {
            return userInfoList.get(0);
        }
        return null;
    }
}











