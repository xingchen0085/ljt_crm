<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\2\6 0006
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<div class="pageContent">
    <form id="form" method="post" action="${baseURL}/user/info/resetPwd" class="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="60">
            <input type="hidden" name="navTabId" value="czygl">
            <input type="hidden" name="callbackType" value="closeCurrent">
            <input type="hidden" name="forwardUrl" value="">
            <input type="hidden" name="userId" value="${userId}">
            <div class="unit">
                <label>操作员登录名：</label>
                <input type="text" value="${operator.loginName }" readonly="readonly" size="30"/>
            </div>
            <div class="unit">
                <label>新密码：</label>
                <input type="password" id="newPwd" name="newPwd" class="required" minlength="6" maxlength="20"
                       size="30"/>
            </div>
            <div class="unit">
                <label>确认新密码：</label>
                <input type="password" name="newPwd2" class="required" equalTo="#newPwd" minlength="6" maxlength="20"
                       size="30"/>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </form>
</div>

