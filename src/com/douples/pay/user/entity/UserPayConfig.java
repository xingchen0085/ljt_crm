package com.douples.pay.user.entity;

import java.io.Serializable;

import com.douples.pay.common.annotation.EntityInfo;
import com.douples.pay.common.entity.BaseEntity;
import com.douples.pay.common.enums.SecurityRatingEnum;
import com.douples.pay.user.enums.FundInfoTypeEnum;

/**
 * 用户支付配置实体类
 * <p>
 * Title: UserPayConfig
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author hexuefeng
 * @date 2017-12-1
 */
public class UserPayConfig extends BaseEntity implements Serializable {

	//安全类型
	@EntityInfo(desc="安全等级")
	private String securityType;
	//自动结算 0:不自动结算 1:自动结算
	@EntityInfo(desc="是否自动结算")
	private String autoSettle;
    //商家编号
	@EntityInfo(desc="商家编号")
	private String merchantNo;
    //收款类型 1：平台收款 2：商家收款
	@EntityInfo(desc="收款方式")
	private String fundIntoType;
    //支付密钥
	@EntityInfo(desc="支付秘钥")
	private String paySecret;
    //服务器IP，暂时最多支持两个IP，用英文逗号隔开
	@EntityInfo(desc="IP白名单")
	private String serverIp;
    //商家名称
	@EntityInfo(desc="商家名称")
	private String merchantName;

	//主密钥
	private String tmkSecret;
	//tpk
	private String tpkSecret;
	//tak
	private String takSecret;

	public String getPaySecret() {
		return paySecret;
	}

	public void setPaySecret(String paySecret) {
		this.paySecret = paySecret;
	}

	public String getFundIntoType() {
		return fundIntoType;
	}

	public void setFundIntoType(String fundIntoType) {
		this.fundIntoType = fundIntoType;
	}

	private static final long serialVersionUID = 1L;

	public String getFundIntoTypeDesc() {
		return FundInfoTypeEnum.getEnumByKey(this.getFundIntoType()).getDesc();
	}

	public String getSecurityType() {
		return securityType;
	}

	public String getSecurityTypeDesc() {
		return SecurityRatingEnum.getEnumByKey(this.getSecurityType()).getDesc();
	}
	
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getAutoSettle() {
		return autoSettle;
	}

	public void setAutoSettle(String autoSettle) {
		this.autoSettle = autoSettle;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTmkSecret() {
		return tmkSecret;
	}

	public void setTmkSecret(String tmkSecret) {
		this.tmkSecret = tmkSecret;
	}

	public String getTpkSecret() {
		return tpkSecret;
	}

	public void setTpkSecret(String tpkSecret) {
		this.tpkSecret = tpkSecret;
	}

	public String getTakSecret() {
		return takSecret;
	}

	public void setTakSecret(String takSecret) {
		this.takSecret = takSecret;
	}
}