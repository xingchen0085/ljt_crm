package com.douples.pay.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付类型枚举类
 * <p>Title: PayTypeEnum</p>
 * <p>Description: </p>
 *
 * @author hexuefeng
 * @date 2017-12-4
 */
public enum PayTypeEnum {

    WECHAT_SCAN("1001", "微信主动扫码"),

    WECHAT_PUBLIC("1002", "微信公众号"),

    WECHAT_APP("1003", "微信APP"),

    WECHAT_UNACTIVESCAN("1007", "微信被动扫码"),

    WECHAT_H5("1008", "微信H5"),

    WECHAT_SCANS0("1004", "微信主动扫码—S0"),

    WECHAT_PUBLICS0("1005", "微信公众号—S0"),

    WECHAT_APPS0("1006", "微信APP—S0"),

    WECHAT_H5S0("1009", "微信H5—S0"),

    ALI_SCAN("2001", "支付宝主动扫码"),

    ALI_SERVICEWINDOW("2002", "支付宝服务窗"),

    ALI_APP("2003", "支付宝APP"),

    ALI_SCANS0("2004", "支付宝主动扫码—S0"),

    ALI_SERVICEWINDOWS0("2005", "支付宝服务窗—S0"),

    ALI_APPS0("2006", "支付宝APP—S0"),

    ALI_UNACTIVESCAN("2007", "支付宝被动扫码"),

    /*	ALI_UNACTIVESCANS0("2008", "支付宝被动扫码—S0"),*/

    ALI_TRADE_CREATE("2010", "支付宝收单交易创建"),

    UNION_SCAN("3001", "银联主动扫码"),

    UNION_UNACTIVESCAN("3002", "银联被动扫码"),

    UNION_CREDIT_CARD("3003", "银联刷卡支付"),

    ACTIVE_PAY("4001", "主动支付T0"),

    /* MESSAGE_QUICK("5001", "快捷支付"),*/

    SMS_APPLY("5001", "短信快捷借记卡"),

    SMS_APPLY_CREDIT("5002", "短信快捷贷记卡"),

    MESSAGE_QUICKS0("5003", "快捷支付—S0"),

    SMS_CONFIRM("5004", "短信快捷收款确认"),

    UNION_ONLINE("6001", "个人网银借记卡"),

    UNION_ONLINE_CREDIT("6002", "个人网银贷记卡"),

    B2B_UNION_ONLINE("6003", "企业网银"),

    WAP_UNION_ONLINE("6004", "手机网银"),

    QQWALLET_SCAN("7001", "QQ钱包扫码"),

    QQWALLET_SCANS0("7002", "QQ钱包扫码—S0"),

    THIRD_PARTY_PLATFORM("9001", "第三方支付平台");
    /**
     * 所属支付方式
     */
    private String way;

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    /**
     * 描述
     */
    private String desc;

    private PayTypeEnum(String way, String desc) {
        this.desc = desc;
        this.way = way;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        PayTypeEnum[] ary = PayTypeEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List toList() {
        PayTypeEnum[] ary = PayTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("desc", ary[i].getDesc());
            map.put("name", ary[i].name());
            list.add(map);
        }
        return list;
    }

    public static PayTypeEnum getEnum(String name) {
        PayTypeEnum[] arry = PayTypeEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    /**
     * 根据way获取枚举
     * <p>Title: getEnum</p>
     * <p>Description: </p>
     *
     * @param name
     * @return
     */
    public static PayTypeEnum getEnumByName(String name) {
        PayTypeEnum[] arry = PayTypeEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].getWay().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List getWayList(String way) {
        PayTypeEnum[] ary = PayTypeEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            if (ary[i].way.equals(way)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("desc", ary[i].getDesc());
                map.put("name", ary[i].name());
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        PayTypeEnum[] enums = PayTypeEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (PayTypeEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }


}
