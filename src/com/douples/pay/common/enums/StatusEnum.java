package com.douples.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum StatusEnum {
	
	ENABLE("1", "启用"),

	DISABLE("0", "停用");

	private String key;
    /** 描述 */
    private String desc;

    private StatusEnum(String key, String desc) {
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

	public static Map<String, Map<String, String>> toMap() {
		StatusEnum[] ary = StatusEnum.values();
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
        StatusEnum[] ary = StatusEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("key", ary[i].getKey());
            map.put("desc", ary[i].getDesc());
            map.put("name", ary[i].name());
            list.add(map);
        }
        return list;
    }

    public static StatusEnum getEnum(String name) {
        StatusEnum[] arry = StatusEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    public static StatusEnum getEnumByKey(String key) {
        StatusEnum[] arry = StatusEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].getKey().equalsIgnoreCase(key)) {
                return arry[i];
            }
        }
        return null;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        StatusEnum[] enums = StatusEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (StatusEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
