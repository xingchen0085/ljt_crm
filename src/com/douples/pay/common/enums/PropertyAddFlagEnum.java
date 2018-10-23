package com.douples.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 属性添加标识
 * <p>Title: PropertyAddFlagEnum</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public enum PropertyAddFlagEnum {

	/** 插入 **/
	INSERT("插入"),
	/** 更新 **/
	UPDATE("更新");

	/** 描述 */
	private String desc;

	private PropertyAddFlagEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static PropertyAddFlagEnum getEnum(String value) {
		PropertyAddFlagEnum resultEnum = null;
		PropertyAddFlagEnum[] enumAry = PropertyAddFlagEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(value)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, Map<String, Object>> toMap() {
		PropertyAddFlagEnum[] ary = PropertyAddFlagEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].name()));
			map.put("value", ary[num].name());
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		PropertyAddFlagEnum[] ary = PropertyAddFlagEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", ary[i].name());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

}
