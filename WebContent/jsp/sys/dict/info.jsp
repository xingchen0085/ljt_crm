<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<div class="pageContent">
    <div class="tabsContent pageFormContent" layoutH="56">
        <div>
            <fieldset style="padding-bottom: 30px">
                <legend>基本信息</legend>
                <dl>
                    <dt>字典编号：</dt>
                    <dd>
                        ${sysDict.dictCode}
                    </dd>
                </dl>
                <dl>
                    <dt>字典名称：</dt>
                    <dd>
                        ${sysDict.dictName}
                    </dd>
                </dl>
                <dl>
                    <dt>序列值：</dt>
                    <dd>
                        ${sysDict.orderSeq}
                    </dd>
                </dl>
                <dl></dl>
                <dl style="height:auto;width:auto;">
                    <dt>字典值：</dt>
                    <dd>
                        <textarea style="height:86px;width:620px;background-color:transparent;" readonly>${sysDict.dictValue}</textarea>
                    </dd>
                </dl>
				<dl style="height:auto;width:auto;">
					<dt>备注：</dt>
					<dd style="height:auto;width:auto;">
						<textarea style="height:86px;width:620px;background-color:transparent;" readonly>${sysDict.remark }</textarea>
					</dd>
				</dl>
            </fieldset>
            <fieldset>
                <legend>时间信息</legend>
                <dl>
                    <dt>创建人：</dt>
                    <dd>
                        ${sysDict.createUserName}
                    </dd>
                </dl>
                <dl>
                    <dt>创建时间：</dt>
                    <dd>
                        <fmt:formatDate value="${sysDict.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </dd>
                </dl>
                <dl>
                    <dt>更新人：</dt>
                    <dd>
                        ${sysDict.updateUserName}
                    </dd>
                </dl>
                <dl>
                    <dt>更新时间：</dt>
                    <dd>
                        <fmt:formatDate value="${sysDict.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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