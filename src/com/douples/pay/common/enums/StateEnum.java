package com.douples.pay.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: StateEnum
 * @Description: 状态枚举
 * @author queyao   
 * @date 2018-04-03 16:55:06
 */
public enum StateEnum {
	SUCCESS("1", "成功"),

	FAILED("0", "失败");

	private String key;

	private String value;

	private StateEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}



	public void setKey(String key) {
		this.key = key;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}

	public static Map<String, Map<String, String>> toMap() {
		StateEnum[] ary = StateEnum.values();
		Map<String, Map<String, String>> enumMap = new HashMap<String, Map<String, String>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, String> value = new HashMap<String, String>();
			String key = ary[num].name();
			value.put("key", ary[num].getKey());
			value.put("value", ary[num].getValue());
			enumMap.put(key, value);
		}
		return enumMap;
	}
}
