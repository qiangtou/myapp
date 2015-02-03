<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>用户登录</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<script type="text/javascript" data-main="/js/login" src="/js/lib/require.js"></script>
<style>
.error {
	color: red;
	font-style: italic;
}
</style>
	</head>
	<body>
<div class="container">
<h1 class="container-title">分布式网管系统</h1>
      <form class="form-signin" action="/login.do">
        <h2 class="form-signin-heading">请登录</h2>
        <label for="userName" class="sr-only">用户名</label>
        <input type="text" id="userName" name="name" class="form-control" placeholder="用户名" required="" autofocus="">
        <label for="inputPassword" class="sr-only">密码</label>
        <input type="password" id="inputPassword" name="md5pwd" class="form-control" placeholder="密码" required="">
        <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
      </form>
    </div>
	</body>
</html>
