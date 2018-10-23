package com.douples.pay.user.entity;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.douples.pay.common.exception.BizException;

/**
 * 支付编码解析类
 * <p>Title: PayWayVO</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class PayWayVO implements Serializable {

	/** serialVersionUID*/ 
	private static final long serialVersionUID = 6714105320596308603L;
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = Logger.getLogger(PayWayVO.class);

	/**
	 * 编码字符串长度
	 */
	private static final int CHAR_LEN = 9;
	
	/**
	 * 
	 * <p>Title: </p>  
	 * <p>Description: </p>  
	 * @param payCode
	 */
	public PayWayVO(String payCode) {
		if(StringUtils.isEmpty(payCode)){
			throw new BizException("支付码为空或者支付码有误!");
		}
		//去空格
		payCode = payCode.replaceAll("\\s*", ""); 
		if(payCode.length() != CHAR_LEN){
			LOGGER.info("支付码长度有误! payCode=["+payCode+"]");
			throw new BizException("支付码长度有误!");
		}
		//设置相关编码
		String payWay = payCode.substring(0,2); 
		String payType = payCode.substring(2, 4);
		String bankCode = payCode.substring(4);
		this.setPayWayCode(payWay);
		this.setPayTypeCode(payType);
		this.setPayBankCode(bankCode);
	}

	/**
	 * 支付方式编码
	 */
	private String payWayCode;
	
	/**
	 * 支付方式名称
	 */
	private String payWayName;
	
	/**
	 * 支付类型编码
	 */
	private String payTypeCode;
	
	/**
	 * 支付类型名称
	 */
	private String payTypeName;
	
	/**
	 * 银行编码
	 */
	private String payBankCode;
	
	/**
	 * 银行名称
	 */
	private String payBankName;


	

	/**
	 * @return the payWayCode
	 */
	public String getPayWayCode() {
		return payWayCode;
	}

	/**
	 * @param payWayCode the payWayCode to set
	 */
	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	/**
	 * @return the payWayName
	 */
	public String getPayWayName() {
		return payWayName;
	}

	/**
	 * @param payWayName the payWayName to set
	 */
	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	/**
	 * @return the payTypeCode
	 */
	public String getPayTypeCode() {
		return payTypeCode;
	}

	/**
	 * @param payTypeCode the payTypeCode to set
	 */
	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	/**
	 * @return the payTypeName
	 */
	public String getPayTypeName() {
		return payTypeName;
	}

	/**
	 * @param payTypeName the payTypeName to set
	 */
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	/**
	 * @return the payBankCode
	 */
	public String getPayBankCode() {
		return payBankCode;
	}

	/**
	 * @param payBankCode the payBankCode to set
	 */
	public void setPayBankCode(String payBankCode) {
		this.payBankCode = payBankCode;
	}

	/**
	 * @return the payBankName
	 */
	public String getPayBankName() {
		return payBankName;
	}

	/**
	 * @param payBankName the payBankName to set
	 */
	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}
	
	public static void main(String[] args) {
		PayWayVO vo = new PayWayVO(" 100112345");
		LOGGER.info(vo.getPayWayCode()+" "+vo.getPayTypeCode()+" "+vo.getPayBankCode());
	}
}