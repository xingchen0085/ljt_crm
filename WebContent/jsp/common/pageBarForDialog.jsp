<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="panelBar">
    <div class="pages">
        <span>显示</span>
        <select theme="simple" cssClass="combox" name="numPerPage"
                value="${pageBean.numPerPage}" onchange="dialogPageBreak({numPerPage:this.value})">
            <c:forEach begin="15" end="90" step="15" varStatus="s">
                <option value="${s.index}" ${pageBean.numPerPage eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
            </c:forEach>
        </select>
        <c:if test="${empty pageBean}">
          <span> 条,共<a style="color: red">0</a>条, 共0页, 当前第0页 </span>
        </c:if>
        <c:if test="${not empty pageBean}">
          <span> 条,共<a style="color: red">${pageBean.totalCount}</a>条, 共${pageBean.totalPage}页, 当前第${pageBean.currentPage}页 </span>
        </c:if>
    </div>
    <div class="pagination" targetType="dialog"
         totalCount="${pageBean.totalCount}" numPerPage="${pageBean.numPerPage}"
         pageNumShown="10"
         currentPage="${pageBean.currentPage}"></div>
</div>
</div>