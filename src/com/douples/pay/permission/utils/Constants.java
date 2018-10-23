/**  
 * <p>Title: Constants.java</p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2017</p>  
 * <p>Company: www.douples.com</p>  
 * @author hexuefeng  
 * @date 2017-12-4  
 * @version 1.0  
 */  
package com.douples.pay.permission.utils;

/**  常量类
 * <p>Title: Constants</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-4  
 */
public class Constants {

	/**
	 * 验证码
	 */
	public static final String CAPTCHA = "captcha";
	
	/**
	 * 验证码开启标识
	 */
	public static final String CAPTCHA_EBABLED = "captchaEbabled";
	
	/**
	 * 登录界面提交的验证码参数名
	 */
	public static final String CAPTCHA_CODE = "captchaCode";
	
	/**
	 * 验证码校验消息存储KEY
	 */
	public static final String CAPTCHA_VALIDATE_MSG = "captchaValidateMsg";
	
	/**
	 * 当前操作员
	 */
	public static final String CURR_SESSION_OPERATOR = "currSessionOperator";
	
	/**
	 * 当前session角色
	 */
	public static final String CURR_SESSION_ROLES = "currSessionRoles";
	
	/**
	 * 当前session权限
	 */
	public static final String CURR_SESSION_PERMISSIONS = "currSessionPermissions";
	
	/**
	 * 密码重试缓存key
	 */
	public static final String SPASSWORD_RETRY_CACHE = "passwordRetryCache";
	
	/**
	 * 支付渠道key
	 */
	public static final String TRADE_CHANNEL_CACHE = "tradechannelCache";
	
	/**
	 * 产品类别key
	 */
	public static final String TRADE_PRODUCT_TYPE_CACHE = "tradeproducttypeCache";

    /**
     *仪表板数据 key
     */
    public static final String DASH_BOARD_DATA_CACHE = "dashBoardDataCache";

}
