package com.douples.pay.user.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.douples.pay.common.exception.BizException;

/**
 * 用户业务异常类
 * <p>Title: UserBizException</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class UserBizException extends BizException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6402548375645868682L;

	/** 用户不存在 **/
    public static final int USER_IS_NULL = 101;

    /** 用户支付配置有误 **/
    public static final int USER_PAY_CONFIG_ERROR = 102;

    /** 用户终端配置有误 **/
    public static final int USER_TERMINAL_CONFIG_ERROR = 105;
    
    /** 用户证件类型重复 **/
    public static final int USER_CERT_IS_EXIST = 103;
    
    /** 图片地址填写有误 **/
    public static final int IMG_URL_ERROR = 104;
    
    public static final UserBizException USER_BANK_ACCOUNT_IS_NULL = new UserBizException(10010002, "用户未设置银行账户信息!");

    private static final Log LOG = LogFactory.getLog(UserBizException.class);

    public UserBizException() {
    }

    public UserBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
    }

    public UserBizException(int code, String msg) {
        super(code, msg);
    }

    public UserBizException print() {
        LOG.info("==>BizException, code:" + this.code + ", msg:" + this.msg);
        return this;
    }
}
