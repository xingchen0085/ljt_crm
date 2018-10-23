<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<div class="pageContent">
    <div class="tabsContent pageFormContent" layoutH="56">
        <div>
            <fieldset style="padding-bottom: 30px">
                <legend>基本信息</legend>
                <dl>
                    <dt>分类编号：</dt>
                    <dd>
                        ${sysDictCategory.categroyCode}
                    </dd>
                </dl>
                <dl>
                    <dt>分类名称：</dt>
                    <dd>
                        ${sysDictCategory.categroyName}
                    </dd>
                </dl>                
                <dl>
                    <dt>序列值：</dt>
                    <dd>
                        ${sysDictCategory.orderSeq}
                    </dd>
                </dl>
				<dl style="height:auto;width:auto;">
					<dt>备注：</dt>
					<dd style="height:auto;width:auto;">
						<textarea style="height:86px;width:620px;background-color:transparent;" readonly>${sysDictCategory.remark }</textarea>
					</dd>
				</dl>
            </fieldset>
            <fieldset>
                <legend>时间信息</legend>
                <dl>
                    <dt>创建人：</dt>
                    <dd>
                        ${sysDictCategory.createUserName}
                    </dd>
                </dl>
                <dl>
                    <dt>创建时间：</dt>
                    <dd>
                        <fmt:formatDate value="${sysDictCategory.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </dd>
                </dl>
                <dl>
                    <dt>更新人：</dt>
                    <dd>
                        ${sysDictCategory.updateUserName}
                    </dd>
                </dl>
                <dl>
                    <dt>更新时间：</dt>
                    <dd>
                        <fmt:formatDate value="${sysDictCategory.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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