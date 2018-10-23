package com.douples.pay.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.permission.entity.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douples.pay.common.enums.PublicEnum;
import com.douples.pay.common.enums.PublicStatusEnum;
import com.douples.pay.user.dao.UserBankAccountDao;
import com.douples.pay.user.entity.UserBankAccount;
import com.douples.pay.user.enums.BankCodeEnum;
import com.douples.pay.user.service.UserBankAccountFacade;
import com.douples.pay.common.utils.StringUtil;

/**
 * 用户银行账户实现类
 * <p>Title: UserBankAccountFacadeImpl</p>
 * <p>Description: 用户银行账户实现类</p>
 *
 * @author hexuefeng
 * @date 2017-11-30
 */
@Service("userBankAccountFacade")
public class UserBankAccountFacadeImpl implements UserBankAccountFacade {

    @Autowired
    private UserBankAccountDao userBankAccountDao;

    @Override
    public void saveData(UserBankAccount rpUserBankAccount) {
        userBankAccountDao.insert(rpUserBankAccount);
    }

    @Override
    public void updateData(UserBankAccount rpUserBankAccount) {
        userBankAccountDao.update(rpUserBankAccount);
    }

    /**
     * 根据用户编号获取银行账户
     */
    @Override
    public UserBankAccount getByUserNo(String userNo) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userNo", userNo);
        paramMap.put("status", PublicStatusEnum.ACTIVE.name());
        return userBankAccountDao.getBy(paramMap);
    }

    /**
     * 创建或更新
     *
     * @param rpUserBankAccount
     */
    @Override
    public void createOrUpdate(Operator operator, UserBankAccount rpUserBankAccount) {
        UserBankAccount bankAccount = getByUserNo(rpUserBankAccount.getUserNo());
        if (bankAccount == null) {
            bankAccount = new UserBankAccount();


            PropertyUtils.addDefaultProperty(bankAccount, PropertyAddFlagEnum.INSERT,
                    operator.getId(), operator.getRealName());

            bankAccount.setId(StringUtil.get32UUID());
            Date date = new Date();
            bankAccount.setCreateTime(date);
            bankAccount.setCreateDate(date);
            bankAccount.setUpdateTime(date);
            bankAccount.setUpdateDate(date);
            bankAccount.setAreas(rpUserBankAccount.getAreas());
            bankAccount.setBankAccountName(rpUserBankAccount.getBankAccountName());
            bankAccount.setBankAccountNo(rpUserBankAccount.getBankAccountNo());
            bankAccount.setBankAccountType(rpUserBankAccount.getBankAccountType());
            bankAccount.setBankCode(rpUserBankAccount.getBankCode());
            bankAccount.setBankName(BankCodeEnum.getEnum(rpUserBankAccount.getBankCode()).getDesc());
            bankAccount.setCardNo(rpUserBankAccount.getCardNo());
            bankAccount.setCardType(rpUserBankAccount.getCardType());
            bankAccount.setCity(rpUserBankAccount.getCity());
            bankAccount.setIsDefault(PublicEnum.YES.name());
            bankAccount.setMobileNo(rpUserBankAccount.getMobileNo());
            bankAccount.setProvince(rpUserBankAccount.getProvince());
            bankAccount.setRemark(rpUserBankAccount.getRemark());
            bankAccount.setStatus(PublicStatusEnum.ACTIVE.name());
            bankAccount.setUserNo(rpUserBankAccount.getUserNo());
            bankAccount.setStreet(rpUserBankAccount.getStreet());
            userBankAccountDao.insert(bankAccount);
        } else {

            PropertyUtils.addDefaultProperty(bankAccount, PropertyAddFlagEnum.UPDATE,
                    operator.getId(), operator.getRealName());


            bankAccount.setUpdateTime(new Date());
            bankAccount.setUpdateDate(new Date());
            bankAccount.setAreas(rpUserBankAccount.getAreas());
            bankAccount.setBankAccountName(rpUserBankAccount.getBankAccountName());
            bankAccount.setBankAccountNo(rpUserBankAccount.getBankAccountNo());
            bankAccount.setBankAccountType(rpUserBankAccount.getBankAccountType());
            bankAccount.setBankCode(rpUserBankAccount.getBankCode());
            bankAccount.setBankName(BankCodeEnum.getEnum(rpUserBankAccount.getBankCode()).getDesc());
            bankAccount.setCardNo(rpUserBankAccount.getCardNo());
            bankAccount.setCardType(rpUserBankAccount.getCardType());
            bankAccount.setCity(rpUserBankAccount.getCity());
            bankAccount.setIsDefault(PublicEnum.YES.name());
            bankAccount.setMobileNo(rpUserBankAccount.getMobileNo());
            bankAccount.setProvince(rpUserBankAccount.getProvince());
            bankAccount.setRemark(rpUserBankAccount.getRemark());
            bankAccount.setStatus(PublicStatusEnum.ACTIVE.name());
            bankAccount.setUserNo(rpUserBankAccount.getUserNo());
            bankAccount.setStreet(rpUserBankAccount.getStreet());
            userBankAccountDao.update(bankAccount);
        }
    }
}