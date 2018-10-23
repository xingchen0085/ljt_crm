package com.douples.pay.user.entity;

import java.io.Serializable;

import com.douples.pay.common.entity.BaseEntity;
import com.douples.pay.common.enums.PublicStatusEnum;
import com.douples.pay.common.enums.StatusEnum;

/**
 * 用户信息
 * <p>Title: UserInfo</p>
 * <p>Description: </p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
public class UserInfo extends BaseEntity implements Serializable {

    private String userNo;

    private String userName;

    private String accountNo;

    private static final long serialVersionUID = 1L;

    private String mobile;

    private String password;
    /**
     * 支付密码
     */
    private String payPwd;

    //这里的UserId：原因是父类中的ID有默认值，这里用作区分，数据库查询出来的主键封装进去的时 ID 而不是这个UserId
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getStatusDesc() {
        return StatusEnum.getEnumByKey(this.getStatus()).getDesc();
    }

}