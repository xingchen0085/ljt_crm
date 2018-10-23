<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<form id="pagerForm" method="post"
      action="${baseURL }/sys/dictionarygory/tree">
    <%@include file="../../common/pageParameter.jsp" %>
</form>
<div class="tabs">
    <div class="tabsHeader">
        <div class="tabsHeaderContent">
            <ul>
                <li class="selected"><a href="javascript:;"><span>数据字典</span></a></li>
            </ul>
        </div>
    </div>
    <div class="tabsContent">
        <div inited="1000" style="display: block;">
            <div class="panelBar">
                <ul class="toolBar">
                    <li>
                        <shiro:hasPermission name="sys:dict_cate:add">
                            <a id="addCate" class="add" href="${baseURL}/sys/dictionarygory/addUI"
                               target="dialog"
                               rel="input" width="850" height="400" title="添加">
                                <span>添加</span>
                            </a>
                        </shiro:hasPermission>
                    </li>
                    <li>
                        <shiro:hasPermission name="sys:dict_cate:edit">
                            <a id="editCate" class="edit bind" style="display: none"
                               href="${baseURL}/sys/dictionarygory/editUI"
                               width="800" height="400"
                               callback="navTabAjax" target="dialog" title="修改">
                                <span>修改</span>
                            </a>
                        </shiro:hasPermission>
                    </li>
                    <li>
                        <shiro:hasPermission name="sys:dict_cate:delete">
                            <a id="delCate" class="delete bind" style="display: none"
                               href="${baseURL}/sys/dictionarygory/delete"
                               callback="navTabAjax" target="ajaxTodo" rel="cataTreeBox" title="确定要删除吗？">
                                <span>删除</span>
                            </a>
                        </shiro:hasPermission>
                    </li>
                    <li>
                        <shiro:hasPermission name="sys:dict_cate:list">
                            <a id="infoCate" class="icon bind" style="display: none"
                               href="${baseURL}/sys/dictionarygory/infoUI"
                               width="850" height="530"
                               callback="navTabAjax" target="dialog" rel="cataTreeBox" title="查看">
                                <span>查看</span>
                            </a>
                        </shiro:hasPermission>
                    </li>
                </ul>
            </div>
            <div layouth="40" id="cataTreeBox"
                 style="float: left; display: block; overflow: auto; width: 240px; border: 1px solid rgb(204, 204, 204); line-height: 21px; background: rgb(255, 255, 255); height: 216px;">
                ${tree}
            </div>

            <div id="dict_cateBox" class="unitBox" style="margin-left:246px;">
                <div class="pageHeader">
                    <form rel="pagerForm" onsubmit="return divSearch(this, 'dict_cateBox');"
                          action="${baseURL }/sys/dict/list"
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
                        <ul class="toolBar">
                        </ul>
                    </div>
                    <table class="table" width="101%" layoutH="170">
                        <thead>
                        <tr>
                            <th width="3%">序号</th>
                            <th width="8%">字典编号</th>
                            <th width="11%">字典名称</th>
                            <th width="28%">字典值</th>
                            <th width="10%">序列值</th>
                            <th width="14%">创建时间</th>
                            <th width="25%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${pageBean.totalCount==0}">
                            <tr>
                                <td>暂无数据</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                    <%@include file="../../common/pageBarForDiv.jsp" %>
                </div>
            </div>
        </div>
    </div>
    <div class="tabsFooter">
        <div class="tabsFooterContent"></div>
    </div>
</div>
<script type="text/javascript">
    function dictCategoryBind(categoryId) {
        $(".bind").css("display", 'block');
        if (categoryId == "0" || categoryId == null) {
            $(".bind").css("display", 'none');
            $("#addCate").attr("href", "${baseURL}/risk/indexCategory/addUI?parentId=" + categoryId);
        } else {
            $("#addCate").attr("href", "${baseURL}/sys/dictionarygory/addUI?parentId=" + categoryId);
            $("#delCate").attr("href", "${baseURL}/sys/dictionarygory/delete?categoryId=" + categoryId);
            $("#editCate").attr("href", "${baseURL}/sys/dictionarygory/editUI?categoryId=" + categoryId);
            $("#infoCate").attr("href", "${baseURL}/sys/dictionarygory/infoUI?categoryId=" + categoryId);
        }
    }

    // 删除后的回调函数，刷新树形菜单
    function navTabAjax(json) {
        //navTabAjaxDone(json);
        navTab.reload();
    }
</script>

