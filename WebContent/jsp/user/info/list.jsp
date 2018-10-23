<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<form id="pagerForm" method="post" action="${baseURL }/user/info/list">
    <%@include file="../../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
    <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL }/user/info/list"
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
    <div class="panelBar">
        <ul class="toolBar">
            <li>
                <shiro:hasPermission name="user:userInfo:add">
                    <a id="添加" class="add"
                       href="${baseURL }/user/info/addUI" target="dialog"
                       rel="input"
                       width="600" height="400" title="添加">
                        <span>添加</span>
                    </a>
                </shiro:hasPermission>
            </li>
            <li class="line">line</li>
        </ul>
    </div>
    <table class="table" width="101%" layoutH="109">
        <thead>
        <tr>
            <th width="3%">序号</th>
            <th width="15%">用户编号</th>
            <th width="15%">用户名称</th>
            <th width="10%">手机号</th>
            <th width="15%">账户编号</th>
            <th width="5%">状态</th>
            <th width="15%">创建时间</th>
            <th width="35%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${pageBean.recordList}" varStatus="s">
            <tr>
                <td>${s.index + 1}</td>
                <td>${item.userNo}</td>
                <td>${item.userName}</td>
                <td>${item.mobile}</td>
                <td>${item.accountNo}</td>
                <td>${item.statusDesc}</td>
                <td>
                    <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>

                    <shiro:hasPermission name="user:userInfo:edit">
                        &nbsp;[
                        <a id="修改" class="edit"
                           href="${baseURL }/user/info/editUI?id=${item.id}"
                           target="dialog"
                           rel="input" width="600" height="400" title="修改">
                            <span style="color:blue;">修改</span>
                        </a>
                        ]
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:userInfo:delete">
                        &nbsp;[
                        <a title="确定要删除【${item.userName }】用户吗?" target="ajaxTodo" method="remove"
                           href="${baseURL }/user/info/delete?id=${item.id}" style="color: blue;">
                            删除
                        </a>
                        ]
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:userInfo:view">
                        &nbsp;[
                        <a id="查看" class="info"
                           href="${baseURL }/user/info/infoUI?id=${item.id}"
                           target="dialog"
                           rel="input" width="850" height="460" title="查看">
                            <span style="color:blue;">查看</span>
                        </a>
                        ]
                    </shiro:hasPermission>


                    <shiro:hasPermission name="user:userInfo:edit">

                        <c:if test="${item.status eq '1'}">
                            &nbsp;[
                            <a title="确定要停用【${item.userName }】用户吗?" target="ajaxTodo"
                               href="${baseURL }/user/info/changeStatus?id=${item.id}"
                               style="color: blue;">
                                停用
                            </a>
                            ]
                        </c:if>

                        <c:if test="${item.status eq '0'}">
                            &nbsp;[
                            <a title="确定要启用【${item.userName }】用户吗?" target="ajaxTodo"
                               href="${baseURL }/user/info/changeStatus?id=${item.id}"
                               style="color: blue;">
                                启用
                            </a>
                            ]
                        </c:if>

                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:userInfo:view">
                        &nbsp;[
                        <a id="重置密码" class="info" title="重置后密码默认为：123456，确定重置？"
                           href="${baseURL }/user/info/resetPwd?userId=${item.id}"
                           target="ajaxTodo" method="remove" style="color: blue;">
                            <span style="color:blue;">重置密码</span>
                        </a>
                        ]
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
    <%@include file="../../common/pageBar.jsp" %>
</div>
