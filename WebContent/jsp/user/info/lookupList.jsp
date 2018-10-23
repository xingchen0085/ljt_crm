<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/taglib/taglib.jsp" %>
<form id="pagerForm" method="post"
      action="${baseURL }/user/info/lookupList<c:if test="${not empty isBinding}">?isBinding=${isBinding}</c:if>">
    <%@include file="/jsp/common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');"
          action="${baseURL }/user/info/lookupList<c:if test="${not empty isBinding}">?isBinding=${isBinding}</c:if>"
          method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>用户名称：
                        <input type="text" name="userName" value="${userInfo.userName}"/>
                    </td>
                    <td>
                        <div class="buttonActive">
                            <div class="buttonContent">
                                <button title="查询" type="submit">查&nbsp;询</button>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="pageContent">
    <table class="table" width="101%" layoutH="114">
        <thead>
        <tr>
            <th width="5%">序号</th>
            <th width="15%">用户编号</th>
            <th width="15%">用户名称</th>
            <th width="15%">账户编号</th>
            <th width="15%">状态</th>
            <th width="15%">创建时间</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
            <tr target="sid_user" rel="${id}"
                ondblclick="$.bringBack({userId:'${item.id}', userName:'${item.userName}'});">
                <td>${s.index + 1}</td>
                <td>${item.userNo}</td>
                <td>${item.userName}</td>
                <td>${item.accountNo}</td>
                <td>${item.statusDesc}</td>
                <td>
                    <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${pageBean.totalCount==0}">
            <tr>
                <td>暂无数据</td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <%@include file="../../common/pageBarForDialog.jsp" %>
</div>