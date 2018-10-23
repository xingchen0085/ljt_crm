package com.douples.pay.permission.entity;

import com.douples.pay.common.entity.PermissionBaseEntity;

/**
 * 操作日志
 * <p>Title: OperatorLog</p>  
 * <p>Description: 操作日志</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class OperatorLog extends PermissionBaseEntity {

	private static final long serialVersionUID = 742891253537618199L;

	private Long operatorId; // 操作员ID
	private String operatorName; // 操作员登录名
	private String operateType; // 操作类型（参与枚举:OperatorLogTypeEnum,1:增加,2:修改,3:删除,4:查询,5:登录）
	private String ip; // IP地址
	private String content; // 操作内容

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
