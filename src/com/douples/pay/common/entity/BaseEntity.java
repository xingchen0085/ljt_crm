package com.douples.pay.common.entity;

import java.util.Date;

import com.douples.pay.common.annotation.EntityInfo;
import com.douples.pay.common.utils.StringUtil;

/**
 * 实体基类
* <p>Title: BaseEntity</p>  
* <p>Description: 实体基类</p>  
* @author hexuefeng  
* @date 2017-11-30
 */
public class BaseEntity {

	/**
	 * 主键ID
	 */
	@EntityInfo(desc="ID")
	private String id = StringUtil.get32UUID();
	/**
	 * 版本号默认为0
	 */
	private Integer version = 0;
	
	/**
	 * 状态
	 */
	@EntityInfo(desc="状态")
	private String status;
	
	/**
	 * 创建时间
	 */
	@EntityInfo(desc="创建时间")
	private Date createTime = new Date();

	/**
	 * 当前日期
	 */
	@EntityInfo(desc="创建日期")
	private Date createDate;
	
	/**
	 * 更新时间
	 */
	@EntityInfo(desc="更新时间")
	private Date updateTime;
	
	/**
	 * 更新日期
	 */
	@EntityInfo(desc="更新日期")
	private Date updateDate;
	
	@EntityInfo(desc="创建人ID")
	private Long createUserId;
	
	@EntityInfo(desc="创建人名称")
	private String createUserName;
	
	@EntityInfo(desc="修改人ID")
	private Long updateUserId;
	
	@EntityInfo(desc="修改人名称")
	private String updateUserName;
	
	/**
	 * 描述
	 */
	@EntityInfo(desc="备注")
	private String remark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the createUserId
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * @param createUserId the createUserId to set
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * @param createUserName the createUserName to set
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * @return the updateUserId
	 */
	public Long getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * @param updateUserId the updateUserId to set
	 */
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * @return the updateUserName
	 */
	public String getUpdateUserName() {
		return updateUserName;
	}

	/**
	 * @param updateUserName the updateUserName to set
	 */
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
}
