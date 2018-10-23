<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../../common/taglib/taglib.jsp" %>
<div class="pageContent">
    <form action="${baseURL }/user/info/add" cssClass="pageForm required-validate"
          onsubmit="return validateCallback(this, dialogAjaxDone);" method="post">
        <input type="hidden" name="navTabId" value="yhxx">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <div class="tabsContent pageFormContent" layoutH="56">
            <div>
                <fieldset>
                    <legend>用户信息</legend>
                    <dl>
                        <dt>用户名称：</dt>
                        <dd>
                            <input type="text" name="userName" class="required" size="25" maxlength="128"/>
                        </dd>
                    </dl>
                    <dl style="width: 400px">
                        <dt>手机号：</dt>
                        <dd style="width: 260px">
                            <input type="number" class="required" style="width: 176px" name="mobile" size="25" maxlength="11" id="mobile" onchange="checkPhone()"/>
                            <span id="tips"></span>
                        </dd>
                    </dl>
                    <dl>
                        <dt>登录密码：</dt>
                        <dd>
                            <input type="text" value="123456" name="password" class="required readonly" size="25" maxlength="50"/>
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
                            <button type="submit" id="add">保存</button>
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
<script>  
function isValidPhone(phone)     
{     
    var reg = /^[1][34578][0-9]{9}$/  
    return reg.test(phone);     
}    
  
  
function checkPhone()  
{   var stamp = document.getElementById("add");
	stamp.disabled=false;
	document.getElementById('tips').innerHTML = "";
	
	var textIps = $("#mobile").val();
    if(textIps.length == 0){
    	
    }else if(textIps.length > 11){
		stamp.disabled=true;
		document.getElementById("tips").innerHTML="<font color='red' size='2'>长度超过11位</font>"
	}else{
		if(!isValidPhone(textIps)){
			stamp.disabled=true;
			document.getElementById("tips").innerHTML="<font color='red' size='2'>无效的号码</font>"
		}
	}
	
}  
  
</script> 