<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>用户登录</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
		<script type="text/javascript" src="/js/lib/md5.js"></script>
		<script type="text/javascript" src="/js/login.js"></script>
	</head>

	<body>
		<div id="wrap">
			<div id="top_content">
				<div id="header">
					<div id="rightheader">
					</div>
					<div id="topheader">
						<h1 id="title">
						</h1>
					</div>
					<div id="navigation">
					</div>
				</div>
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
						登陆
					</h1>
					<form action="/login.do" method="post">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tbody>
								<tr>
									<td valign="middle" align="right">
										账号:
									</td>
									<td valign="middle" align="left">
										<input type="text" class="inputgri" name="userName"
											id="username">
									</td>
								</tr>
								<tr>
									<td valign="middle" align="right">
										密码:
									</td>
									<td valign="middle" align="left">
										<input style="height: 16px; width: 147px" type="password"
											name="passWord" class="inputgri" id="password">
										<span style="color: red; font-style: italic;"> </span>
									</td>
								</tr>


							</tbody>
						</table>
						<p>
							<input type="submit" class="button" value="确认" />
						</p>
					</form>
				</div>
			</div>
			<div id="footer">
				<div id="footer_bg">
				</div>
			</div>
		</div>
	</body>
</html>
