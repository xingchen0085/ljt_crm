<%--
  Created by IntelliJ IDEA.
  User: chenxinghua
  Date: 2018\2\5 0029
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/common/taglib/taglib.jsp" %>
<div class="pageContent">
    <div class="tabsContent pageFormContent" layoutH="56">
        <div>
            <fieldset>
                <legend>基本信息</legend>
                <dl>
                    <dt>用户编号：</dt>
                    <dd>
                        ${userInfo.userNo}
                    </dd>
                </dl>
                <dl>
                    <dt>账户编号：</dt>
                    <dd>
                        ${userInfo.accountNo}
                    </dd>
                </dl>
                <dl>
                    <dt>用户名称：</dt>
                    <dd>
                        ${userInfo.userName}
                    </dd>
                </dl>
                <dl>
                    <dt>手机号码：</dt>
                    <dd>
                        ${userInfo.mobile}
                    </dd>
                </dl>
                <dl>
                    <dt>状态：</dt>
                    <dd>
                        ${userInfo.statusDesc}
                    </dd>
                </dl>
            </fieldset>
            <fieldset style="padding-bottom: 15px">
                <legend>绑定商户信息</legend>
                <c:if test="${not empty merchant}">
                    <dl>
                        <dt>商户编号：</dt>
                        <dd>
                                ${merchant.merchantNo}
                        </dd>
                    </dl>

                    <dl>
                        <dt>商户名称：</dt>
                        <dd>
                                ${merchant.merchantName}
                        </dd>
                    </dl>
                </c:if>
                <c:if test="${empty merchant}">
                    <dl>
                        <dt>该用户未绑定商户;</dt>
                        <dd></dd>
                    </dl>
                </c:if>
            </fieldset>
            <fieldset>
                <legend>时间信息</legend>
                <dl>
                    <dt>创建人：</dt>
                    <dd>
                        ${userInfo.createUserName}
                    </dd>
                </dl>
                <dl>
                    <dt>创建时间：</dt>
                    <dd>
                        <fmt:formatDate value="${userInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </dd>
                </dl>
                <dl>
                    <dt>更新人：</dt>
                    <dd>
                        ${userInfo.updateUserName}
                    </dd>
                </dl>
                <dl>
                    <dt>更新时间：</dt>
                    <dd>
                        <fmt:formatDate value="${userInfo.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </dd>
                </dl>
            </fieldset>
        </div>
    </div>
    <div class="formBar">
        <ul style="float: right;">
            <li>
                <div class="button">
                    <div class="buttonContent">
                        <button type="button" class="close">取消</button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>