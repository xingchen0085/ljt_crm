package com.douples.pay.user.service;

/**
 * 生成编号接口
 * <p>Title: BuildNoFacade</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface BuildNoFacade {

	/** 获取用户编号 **/
	String buildUserNo();

	/** 获取账户编号 **/
	String buildAccountNo();

	/** 获取支付流水号 **/
	String buildTrxNo();

	/** 获取银行订单号 **/
	String buildBankOrderNo();

	/** 获取对账批次号 **/
	String buildReconciliationNo();

}
