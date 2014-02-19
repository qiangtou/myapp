<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="/js/lib/json2.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
		<script type="text/javascript" src="/js/manage.js"></script>
		<title>分布式网管系统</title>
	</head>
	<body style="text-align: center">
		<div style="border: 2px solid black; height: 100%; width: 900px">
			<div
				style="border: 2px solid grey; height: 40px; width: 99.5%; text-align: right;">
				<div style="marging: 5x 2px; padding: 10px 2px">
					<div style="float: left">
						<input id='addServer' type="button" value="添加服务器" >
					</div>
					<div style="float: left">
						<input id='addDept' type="button" value="添加部门" >
					</div>
					登陆账户：admin&nbsp;
				</div>
			</div>

			<div>
				<div
					style="float: left; border: 2px solid grey; width: 20%; height: 400px; overflow-y: auto; text-align: left; vertical-align: middle"
					id="menuList">
					<c:forEach var="dept" items="${deptList}">
						<div class="depts" id="${dept.deptId }"
							deptData="${dept.cname },${dept.deptNo },${dept.dsc }"
							style="cursor: pointer"">
							<img id="img1" src="/images/subheader_expand.png" align="middle">
							<span id="span1">${dept.cname }</span>
						</div>
						<div id="menu${dept.deptId}" style="display: none">
							<li id="1">
								<a href="javascript:void(0)" id="192.168.1.61">192.168.1.61</a>
							</li>
						</div>
					</c:forEach>
				</div>
				<div id='content'
					style="border: 2px solid grey; width: 79%; height: 400px;">

				</div>
			</div>
		</div>
		<script id='deptTEMP' type="templete">
		<div style="width: 100%; height: 50px">
			<div style="float: left">
				<p id="ip_addr" style="padding: 10px 20px; color: red; text-align: left">{title}</p>
			</div>
			<div id="operate" style="float: right; padding: 10px 20px">
				<input type="button" class="action" value="{action}">
				<input type="button" class="del" value="删除">
			</div>
		</div>
		<div id="showResult" style="padding: 10px 20px">
			<table>
				<tbody>
					<tr>
						<td>部门名称：</td>
						<td>
							<input type="text" id="deptName" maxlength="16" value="{cname}">
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>部门编号：</td>
						<td>
							<input type="text" id="deptNo" maxlength="6" value="{deptNo}">
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>部门描述：</td>
						<td>
							<input type="text" id="deptDsc" maxlength="16" value="{dsc}">
						</td>
					</tr>
					<tr></tr>
				</tbody>
			</table>
		</div>
				</script>
		<script id='serverTEMP' type="templete">
		<div style="width: 100%; height: 50px">
			<div style="float: left">
				<p id="ip_addr" style="padding: 10px 20px; color: red; text-align: left">{title}</p>
			</div>
			<div id="operate" style="float: right; padding: 10px 20px">
				<input type="button" class="action" value="{action}">
				<input type="button" class="del" value="删除">
			</div>
		</div>
		<div id="showResult" style="padding: 10px 20px">
			<table>
				<tbody>
					<tr>
						<td>服务器IP：</td>
						<td>
							<input type="text" id="ipAddr" maxlength="20" value="{ipAddr}">
						</td>
					</tr>
					<tr></tr>
					<tr><td>所属部门：</td>
<td>
<select id="deptId" style="width:142px">
<c:forEach var="dept" items="${deptList}">
<option style="text-align:center" value="${dept.deptId}">${dept.cname}</option>
</c:forEach>
</select></td></tr>
					<tr></tr>
				</tbody>
			</table>
		</div>
				</script>
	</body>
</html>