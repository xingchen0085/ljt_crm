package com.douples.pay.permission.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.douples.pay.permission.utils.Constants;

/**
 * 验证码校验
 * <p>Title: CaptchaValidateFilter</p>  
 * <p>Description: 验证码校验</p>  
 * @author hexuefeng  
 * @date 2017-12-4
 */
public class CaptchaValidateFilter extends AccessControlFilter {

	/**
	 * 是否开启验证码支持
	 */
	private boolean captchaEnabled = true;

	/**
	 *  前台提交的验证码参数名
	 */
	private String captchaParam = Constants.CAPTCHA_CODE;

	/**
	 * 验证码验证后存储消息属性名 主要用于失败消息
	 */
	private String failureKeyAttribute = Constants.CAPTCHA_VALIDATE_MSG; 
	 
	public void setCaptchaEnabled(boolean captchaEnabled) {
		this.captchaEnabled = captchaEnabled;
	}



	/**
	 * @param captchaParam the captchaParam to set
	 */
	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}


	/**
	 * @param failureKeyAttribute the failureKeyAttribute to set
	 */
	public void setFailureKeyAttribute(String failureKeyAttribute) {
		this.failureKeyAttribute = failureKeyAttribute;
	}



	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// 1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
		request.setAttribute(Constants.CAPTCHA_EBABLED, captchaEnabled);

		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		// 2、判断验证码是否禁用 或不是表单提交（允许访问）
		if (captchaEnabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
			return true;
		}
		// 3、此时是表单提交，验证验证码是否正确
		// 获取页面提交的验证码
		String submitCaptcha = httpServletRequest.getParameter(captchaParam);
		// 获取session中的验证码
		String captcha = (String) httpServletRequest.getSession().getAttribute(Constants.CAPTCHA);
		if (submitCaptcha.equals(captcha)) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 如果验证码失败了，存储失败key属性
		request.setAttribute(failureKeyAttribute, "验证码错误！");
		return true;
	}

}
