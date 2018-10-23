<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<form id="pagerForm" method="post" action="${baseURL }/sys/dict/list">
    <%@include file="../../common/pageParameter.jsp" %>
    <input type="hidden" name="categroyId" value="${sysDictInfo.categroyId}">
    <input type="hidden" name="rel" value="dict_cateBox">
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return divSearch(this, 'dict_cateBox');" action="${baseURL }/sys/dict/list"
          method="post">
        <input type="hidden" name="categroyId" value="${sysDictInfo.categroyId}">
        <input type="hidden" name="rel" value="dict_cateBox">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>字典编号：
                        <input type="text" name="dictCode" value="${sysDictInfo.dictCode}"/>
                    </td>
                    <td>字典名称：
                        <input type="text" name="dictName" value="${sysDictInfo.dictName}"/>
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
    <div class="panelBar">
        <c:if test="${not empty sysDictInfo.categroyId and sysDictInfo.categroyId ne '0'}">
            <ul class="toolBar">
                <li>
                    <shiro:hasPermission name="sys:dict:add">
                        <a id="添加" class="add"
                           href="${baseURL }/sys/dict/addUI?categroyId=${sysDictInfo.categroyId}" target="dialog"
                           rel="input"
                           width="830" height="490" title="添加">
                            <span>添加</span>
                        </a>
                    </shiro:hasPermission>
                </li>
            </ul>
        </c:if>
    </div>
    <table class="table" width="101%" layoutH="170">
        <thead>
        <tr>
            <th width="3%">序号</th>
            <th width="15%">字典编号</th>
            <th width="15%">字典名称</th>
            <th width="35%">字典值</th>
            <%--<th width="10%">序列值</th>--%>
            <th width="15%">创建时间</th>
            <th width="20%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
            <tr>
                <td>${s.index + 1}</td>
                <td>${item.dictCode}</td>
                <td>${item.dictName}</td>
                <td>
                    <c:if test="${fn:length(item.dictValue)>50 }">
                        ${fn:substring(item.dictValue, 0, 50)}...
                    </c:if>
                    <c:if test="${fn:length(item.dictValue)<=50 }">
                        ${item.dictValue}
                    </c:if>
                </td>
                    <%--<td>${item.orderSeq}</td>                --%>
                <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                    <shiro:hasPermission name="sys:dict:edit">
                        &nbsp;[
                        <a id="修改" class="edit"
                           href="${baseURL }/sys/dict/editUI?id=${item.dictId}"
                           target="dialog"
                           rel="input" width="830" height="490" title="修改">
                            <span style="color:blue;"> 修改</span>
                        </a>
                        ]
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:dict:delete">
                        &nbsp;[
                        <a title="确定要删除【${item.dictName}】吗?" target="ajaxTodo" method="remove"
                           href="${baseURL }/sys/dict/delete?dictId=${item.dictId}&rel=dict_cateBox"
                           style="color: blue;">
                            删除</a>
                        ]
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:dict:list">
                        [
                        <a id="查看" class="info"
                           href="${baseURL }/sys/dict/infoUI?id=${item.dictId}"
                           target="dialog"
                           rel="input" width="850" height="530" title="查看">
                            <span style="color:blue;"> 查看</span>
                        </a>
                        ]&nbsp;
                    </shiro:hasPermission>
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
    <%@include file="../../common/pageBarForDiv.jsp" %>
</div>
