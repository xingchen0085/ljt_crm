package com.douples.pay.user.entity;

import java.io.Serializable;

import com.douples.pay.common.entity.BaseEntity;

/**
 * 此实体没有关联的表，只作用于序列查找时传参使用 
 * <p>Title: SeqBuild</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class SeqBuild extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 序列名称 **/
	private String seqName;

	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

}