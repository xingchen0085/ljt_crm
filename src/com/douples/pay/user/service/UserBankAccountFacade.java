package com.douples.pay.user.service;

import com.douples.pay.permission.entity.Operator;
import com.douples.pay.user.entity.UserBankAccount;

/**
 * 用户银行账户接口
* <p>Title: userBankAccountFacade</p>  
* <p>Description: </p>  
* @author hexuefeng  
* @date 2017-11-30
 */
public interface UserBankAccountFacade{
	
	/**
	 * 保存
	 */
	void saveData(UserBankAccount rpUserBankAccount);

	/**
	 * 更新
	 */
	void updateData(UserBankAccount rpUserBankAccount);

	/**
	 * 根据用户编号获取银行账户
	 */
	UserBankAccount getByUserNo(String userNo);

	/**
	 * 创建或更新
	 * @param rpUserBankAccount
	 */
	void createOrUpdate(Operator operator,UserBankAccount rpUserBankAccount);
}