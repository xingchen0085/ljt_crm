<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../common/taglib/taglib.jsp" %>
<form id="pagerForm" method="post" action="${baseURL}/sys/role/list">
    <%@include file="../common/pageParameter.jsp" %>
</form>
<div class="pageHeader">
    <form id="pagerForm" onsubmit="return navTabSearch(this);" action="${baseURL}/sys/role/list" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        角色名称：
                        <input type="text" name="roleName" value="${role.roleName}" size="30" alt="模糊查询"/>
                    </td>
                    <td>
                        <div class="subBar">
                            <ul>
                                <li>
                                    <div class="buttonActive">
                                        <div class="buttonContent">
                                            <button type="submit">查询</button>
                                        </div>
                                    </div>
                                </li>
                            </ul>
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
                <shiro:hasPermission name="sys:role:add">
                    <a class="add" href="${baseURL}/sys/role/addUI" target="dialog" width="550" height="300" rel="input"
                       title="添加角色">
                        <span>添加角色</span>
                    </a>
                </shiro:hasPermission>
            </li>
        </ul>
    </div>

    <table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="111">
        <thead>
        <tr>
            <th>序号</th>
            <th>角色名称</th>
            <th>角色编码</th>
            <th>描述</th>
            <th>创建时间</th>
            <th>操作</th>
            <!-- 图标列不能居中 -->
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${pageBean.recordList}" varStatus="st">
            <%-- 普通操作员看不到超级管理员角色 --%>
            <tr target="sid_user" rel="${id}">
                <td>${st.index+1}</td>
                <td>${item.roleName }</td>
                <td>${item.roleCode}</td>
                <td>${item.remark}</td>
                <td>
                    <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <shiro:hasPermission name="sys:role:edit">
                        [
                        <a href="${baseURL}/sys/role/editUI?roleId=${item.id}" title="修改角色【${item.roleName}】"
                           target="dialog" width="550" height="300" rel="input" style="color: blue">修改</a>
                        ]
                    </shiro:hasPermission>

                    <shiro:hasPermission name="sys:role:delete">
                        <c:if test="${roleType eq RoleTypeEnum.USER.value}">
                            &nbsp;[<a href="${baseURL}/sys/role/delete?roleId=${item.id}" title="删除角色【${item.roleName}】" target="ajaxTodo" style="color: blue">删除</a>]
                        </c:if>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:assignmenu">
                        &nbsp;[
                        <a href="${baseURL}/sys/role/assignMenuUI?roleId=${item.id}" title="为角色【${item.roleName}】分配菜单"
                           target="dialog" width="950" height="470" style="color: blue">分配菜单</a>
                        ] &nbsp;
                    </shiro:hasPermission>
                    <shiro:hasPermission name="sys:role:assignpermission">
                        [
                        <a href="${baseURL}/sys/role/assignPermissionUI?roleId=${item.id}"
                           title="为角色【${item.roleName}】分配权限" target="dialog" width="950" height="470"
                           style="color: blue">分配权限</a>
                        ]
                    </shiro:hasPermission>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- 分页条 -->
    <%@include file="../common/pageBar.jsp" %>
</div>
