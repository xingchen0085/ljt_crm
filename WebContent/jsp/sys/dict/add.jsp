<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<div class="pageContent">
    <form action="${baseURL }/sys/dict/add" cssClass="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);" method="post">
        <%--<input type="hidden" name="navTabId" value="sjzdflgl">--%>
        <input type="hidden" name="rel" value="dict_cateBox">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <input type="hidden" name="categroyId" value="${sysDict.categroyId}">
        <div class="tabsContent pageFormContent" layoutH="56">
            <div>
                <fieldset style="padding-bottom: 60px">
                    <legend>基本信息</legend>
                    <dl>
                        <dt>字典编号：</dt>
                        <dd>
                            <input type="text" name="dictCode" maxlength="32" class="required"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>字典名称：</dt>
                        <dd>
                            <input type="text" name="dictName" maxlength="64" class="required"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>序列值：</dt>
                        <dd>
                            <input type="number" name="orderSeq" maxlength="11" class="required"/>
                        </dd>
                    </dl>                    
                    <dl style="height:auto;width:auto;">
                        <dt>字典值：</dt>
                        <dd style="height:auto;width:auto;">
                             <textarea rows="5" cols="85" type="text" name="dictValue" minlength="0" maxlength="512" class="required"
                                      ></textarea>
                        </dd>
                    </dl>
                    <dl style="width:670px">
                        <dt>备注：</dt>
                        <dd>
                            <textarea rows="5" cols="85" name="remark" minlength="0" maxlength="512"
                                      value=""></textarea>
                        </dd>
                    </dl>
                </fieldset>
            </div>
        </div>
        <div class="formBar">
            <ul style="float: right;">
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