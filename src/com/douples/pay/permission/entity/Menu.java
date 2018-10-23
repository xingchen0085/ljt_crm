package com.douples.pay.permission.entity;

import com.douples.pay.common.entity.PermissionBaseEntity;

/**
 * 菜单实体
 * <p>Title: Menu</p>  
 * <p>Description: 菜单实体</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class Menu extends PermissionBaseEntity {
	private static final long serialVersionUID = 1L;

	/** 菜单名称 **/
	private String name;

	/** 菜单地址 **/
	private String url;

	/** 菜单编号（用于显示时排序） **/
	private String number;

	/** 是否为叶子节点 **/
	private String isLeaf;

	/** 菜单层级 **/
	private Long level;

	/** 父节点:一级菜单为0 **/
	private Menu parent;

	/** 目标名称（用于DWZUI的NAVTABID） **/
	private String targetName;

	public Menu() {
		super();
	}

	/** 菜单名称 **/
	public String getName() {
		return name;
	}

	/** 菜单名称 **/
	public void setName(String name) {
		this.name = name;
	}

	/** 菜单地址 **/
	public String getUrl() {
		return url;
	}

	/** 菜单地址 **/
	public void setUrl(String url) {
		this.url = url;
	}

	/** 菜单编号（用于显示时排序） **/
	public String getNumber() {
		return number;
	}

	/** 菜单编号（用于显示时排序） **/
	public void setNumber(String number) {
		this.number = number;
	}

	/** 是否为叶子节点 **/
	public String getIsLeaf() {
		return isLeaf;
	}

	/** 是否为叶子节点 **/
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	/** 菜单层级 **/
	public Long getLevel() {
		return level;
	}

	/** 菜单层级 **/
	public void setLevel(Long level) {
		this.level = level;
	}

	/** 父节点:一级菜单为0 **/
	public Menu getParent() {
		return parent;
	}

	/** 父节点:一级菜单为0 **/
	public void setParent(Menu parent) {
		this.parent = parent;
	}

	/** 目标名称（用于DWZUI的NAVTABID） **/
	public String getTargetName() {
		return targetName;
	}

	/** 目标名称（用于DWZUI的NAVTABID） **/
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

}
