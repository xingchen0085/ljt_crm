package com.douples.pay.user.entity;

import com.douples.pay.common.annotation.EntityInfo;
import com.douples.pay.common.entity.BaseEntity;

/**
 * @ClassName UserTerminalEquipment
 * @Description 商户终端设备实体类
 * @Author luohailong
 * @Date 2018/9/19
 **/
public class UserTerminalEquipment extends BaseEntity {

    //终端标识码
    @EntityInfo(desc="终端编码")
    private String terminalNo;

    //商家编号
    @EntityInfo(desc="商户编码")
    private String merchantNo;

    //商家名称
    @EntityInfo(desc="商户名称")
    private String merchantName;

    //当前终端批次号，结算完成后批次号加一
    @EntityInfo(desc="当前批次号")
    private String batchNo;

    //POS终端TMK秘钥,与终端约定保持一致
    @EntityInfo(desc="TMK密钥")
    private String tmkSecret;

    //POS终端TPK秘钥（生成PIN），每次签到重新生成
    @EntityInfo(desc="TPK密钥")
    private String tpkSecret;

    //POS终端TAK秘钥（生成MAC），每次签到重新生成
    @EntityInfo(desc="TAK密钥")
    private String takSecret;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTerminalNo() {
        return terminalNo;
    }

    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getTmkSecret() {
        return tmkSecret;
    }

    public void setTmkSecret(String tmkSecret) {
        this.tmkSecret = tmkSecret;
    }

    public String getTpkSecret() {
        return tpkSecret;
    }

    public void setTpkSecret(String tpkSecret) {
        this.tpkSecret = tpkSecret;
    }

    public String getTakSecret() {
        return takSecret;
    }

    public void setTakSecret(String takSecret) {
        this.takSecret = takSecret;
    }
}
