package com.douples.pay.common.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;

import com.douples.pay.common.dwz.DWZ;
import com.douples.pay.common.dwz.DwzAjax;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.utils.Constants;


/**
 * 控制基类
 * <p>Title: BaseController</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public abstract class BaseController {
	/**
	 * 从shiro中获取session
	 * <p>Title: getSession</p>  
	 * <p>Description: 从shiro中获取session</p>  
	 * @return
	 */
	protected Session getSession() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		return session;
	}

	/**
	 * 获取当前用户信息
	 * <p>Title: getPmsOperator</p>  
	 * <p>Description: </p>  
	 * @return
	 */
	protected Operator getOperator() {
		Operator operator = (Operator) this.getSession().getAttribute(Constants.CURR_SESSION_OPERATOR);
		return operator;
	}

	/**
	 * 响应DWZ的ajax失败请求,跳转到ajaxDone视图
	 * <p>Title: operateError</p>  
	 * <p>Description: </p>  
	 * @param message
	 * @param model
	 * @return
	 */
	protected String operateError(String message, Model model) {
		DwzAjax dwz = new DwzAjax();
		dwz.setStatusCode(DWZ.ERROR);
		dwz.setMessage(message);
		model.addAttribute("dwz", dwz);
		return "common/ajaxDone";
	}

	/**
	 * 响应DWZ的ajax成功请求,跳转到ajaxDone视图
	 * <p>Title: operateSuccess</p>  
	 * <p>Description: </p>  
	 * @param model
	 * @param dwz 页面传过来的dwz参数
	 * @return
	 */
	protected String operateSuccess(Model model, DwzAjax dwz) {
		dwz.setStatusCode(DWZ.SUCCESS);
		dwz.setMessage("操作成功");
		model.addAttribute("dwz", dwz);
		return "common/ajaxDone";
	}
}
