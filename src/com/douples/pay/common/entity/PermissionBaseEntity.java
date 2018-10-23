package com.douples.pay.common.entity;

import com.douples.pay.common.enums.PublicStatusEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限基类
 * <p>Title: PermissionBaseEntity</p>
 * <p>Description: 权限基类</p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
public class PermissionBaseEntity implements Serializable {

    private static final long serialVersionUID = -1164227376672815589L;
    private Long id;// 主键ID.
    private Integer version = 0;// 版本号默认为0
    private String status;// 状态 PublicStatusEnum

    private Date createTime = new Date();// 创建时间.

	private Date createDate;
	
	private Date updateTime;
	
	private Date updateDate;
	
	private Long createUserId;
	
	private String createUserName;
	
	private Long updateUserId;
	
	private String updateUserName;
	
    private String remark;// 描述

    public String getStatusDesc() {
        if (null != getStatus()) {
            return PublicStatusEnum.getEnum(getStatus()).getDesc();
        }

        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

}
