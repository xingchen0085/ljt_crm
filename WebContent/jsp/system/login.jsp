<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="${baseURL }/common/taglib/taglib.jsp" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>达付通管理平台</title>
<link rel="stylesheet" type="text/css" href="${baseURL }/dwz/themes/css/login.css" />
<link type="favicon" rel="shortcut icon" href="${baseURL }/dwz/themes/default/images/favicon.ico" />
<script type="text/javascript" language="javascript" src="${baseURL }/common/js/login/login.js"></script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<a href="#">
					<img src="${baseURL }/dwz/themes/default/images/login_logo.png" />
				</a>
			</h1>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li>
							
						</li>
					</ul>
				</div>
				<h2 class="login_title">
					<img src="${baseURL }/dwz/themes/default/images/login_title.png" />
				</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form id="form1" action="${baseURL}/login" method="post">
					<div style="color: red; padding-left: 80px; padding-bottom: 10px;">${message}</div>
					<div class="login_user">
						<ul>
							<li>
								<label>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</label>
							</li>
							<li>
								<input type="text" name="username" size="18" class="login_input" />
							</li>
						</ul>
						<ul>
							<li>
								<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
							</li>
							<li>
								<input type="password" name="password" size="18" class="login_input" />
							</li>
						</ul>
					</div>
					<c:if test="${captchaEbabled}">
						<div class="login_code">
							<ul>
								<li>
									<label>验证码：</label>
								</li>
								<li class="input_code">
									<input id="code" name="captchaCode" size="6" />
								</li>
								
								<li>
									<span> <img onclick="changeCaptchaCode(this)" src="${baseURL}/captcha.jpg" alt="点击更换验证码" title="点击更换验证码" width="65" height="24" />
									</span>
								</li>
							</ul>
						</div>
					</c:if>
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
			<div class="login_banner">
				<img src="${baseURL }/dwz/themes/default/images/login_banner.jpg" />
			</div>
			<div class="login_main">
				<ul class="helpList">
				</ul>
				<div class="login_inner"></div>
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2015-2018
			<a href="http://www.douples.com" target="_blank">深圳达普信科技有限公司</a>
		</div>
	</div>

	<script>
	</script>
</body>
</html>