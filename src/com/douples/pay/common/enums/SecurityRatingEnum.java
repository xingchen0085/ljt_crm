package com.douples.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 安全等级枚举
 * <p>Title: SecurityRatingEnum</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-4
 */
public enum SecurityRatingEnum {

	MD5("1", "MD5"),

	MD5_IP("2", "MD5+IP白名单");

	private String key;
	/** 描述 */
	private String desc;

	private SecurityRatingEnum(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static SecurityRatingEnum getEnum(String enumName) {
		SecurityRatingEnum resultEnum = null;
		SecurityRatingEnum[] enumAry = SecurityRatingEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].name().equals(enumName)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static SecurityRatingEnum getEnumByKey(String key) {
		SecurityRatingEnum resultEnum = null;
		SecurityRatingEnum[] enumAry = SecurityRatingEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getKey().equals(key)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	public static Map<String, Map<String, String>> toMap() {
		SecurityRatingEnum[] ary = SecurityRatingEnum.values();
		Map<String, Map<String, String>> enumMap = new HashMap<String, Map<String, String>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, String> value = new HashMap<String, String>();
			String key = ary[num].name();
			value.put("key", ary[num].getKey());
			value.put("desc", ary[num].getDesc());
			enumMap.put(key, value);
		}
		return enumMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		SecurityRatingEnum[] ary = SecurityRatingEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", ary[i].getKey());
			map.put("name", ary[i].name());
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}

	/**
	 * 取枚举的json字符串
	 * 
	 * @return
	 */
	public static String getJsonStr() {
		SecurityRatingEnum[] enums = SecurityRatingEnum.values();
		StringBuffer jsonStr = new StringBuffer("[");
		for (SecurityRatingEnum senum : enums) {
			if (!"[".equals(jsonStr.toString())) {
				jsonStr.append(",");
			}
			jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
		}
		jsonStr.append("]");
		return jsonStr.toString();
	}
}
