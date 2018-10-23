package com.douples.pay.user.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金流入类型
 * <p>Title: FundInfoTypeEnum</p>  
 * <p>Description: 资金流入类型</p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public enum FundInfoTypeEnum {

	 MERCHANT_RECEIVES("2", "商家收款"),
	 PLAT_RECEIVES("1", "平台收款");

	private String key;
    /** 描述 */
    private String desc;

    private FundInfoTypeEnum(String key, String desc) {
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
		FundInfoTypeEnum[] ary = FundInfoTypeEnum.values();
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
        FundInfoTypeEnum[] ary = FundInfoTypeEnum.values();
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

    public static FundInfoTypeEnum getEnum(String name) {
        FundInfoTypeEnum[] arry = FundInfoTypeEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    public static FundInfoTypeEnum getEnumByKey(String key) {
        FundInfoTypeEnum[] arry = FundInfoTypeEnum.values();
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
        FundInfoTypeEnum[] enums = FundInfoTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (FundInfoTypeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }
}
