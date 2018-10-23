package com.douples.pay.permission.service;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.permission.entity.OperatorLog;

/**
 * 操作日志接口
 * <p>Title: OperatorLogFacade</p>  
 * <p>Description: 操作日志接口</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface OperatorLogFacade {

	/**
	 * 创建pmsOperatorLog
	 */
	void saveData(OperatorLog pmsOperatorLog);

	/**
	 * 修改pmsOperatorLog
	 */
	void updateData(OperatorLog pmsOperatorLog);

	/**
	 * 根据id获取数据pmsOperatorLog
	 * 
	 * @param id
	 * @return
	 */
	OperatorLog getDataById(Long id);

	/**
	 * 分页查询pmsOperatorLog
	 * 
	 * @param pageParam
	 * @param ActivityVo
	 *            PmsOperatorLog
	 * @return
	 */
	PageBean listPage(PageParam pageParam, OperatorLog pmsOperatorLog);

}
