<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="/js/lib/json2.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.validate.min.js"></script>
		<script type="text/javascript" src="/js/lib/avalon.js"></script>
		<script type="text/javascript" src="/js/lib/xml2json.js"></script>
		<script type="text/javascript" src="/js/manage1.js"></script>
		<title>分布式网管系统</title>
		<style>
.error {
	color: red;
	font-style: italic;
}
</style>
	</head>
	<body style="text-align: center">
		<div
			style="margin: 0 auto; border: 2px solid black; height: 100%; width: 900px">
			<div
				style="border: 2px solid grey; height: 40px; width: 99.5%; text-align: right;">
				<div style="marging: 5x 2px; padding: 10px 2px">
					<div style="float: left">
						<input id='addServer' type="button" value="添加服务器">
					</div>
					<div style="float: left">
						<input id='addDept' type="button" value="添加部门">
					</div>
					登陆账户：${user}&nbsp;
				</div>
			</div>

			<div ms-controller="main">
				<div
					style="float: left; border: 2px solid grey; width: 20%; height: 400px; overflow-y: auto; text-align: left; vertical-align: middle"
					id="menuList">
					<div ms-repeat-dept='depts'>
						<div ms-click='showServers(dept)' class="depts"
							ms-attr-id="deptId" style="cursor: pointer">
							<img ms-attr-id="img{{deptId }}"
								ms-attr-src="/images/subheader_{{expanded?'fold':'expand'}}.png"
								align="middle">
							<span ms-attr-id="span{{deptId}}">{{cname }}</span>
						</div>
						<div ms-attr-id="menu{{deptId}}" ms-visible='expanded'>
							<li ms-repeat-server='servers'>
								<a ms-attr-id="server{{id}}" href="javascript:void(0)" ms-click="">{{ipAddr}}</a>
							</li>
						</div>
					</div>
				</div>


				<div id='content'
					style="border: 2px solid grey; width: 79%; height: 400px;">
				</div>
			</div>
		</div>
		<script id='deptTEMP' type="template">
		<div style="width: 100%; height: 50px">
			<div style="float: left">
				<p id="title" style="padding: 10px 20px; color: red; text-align: left">{title}</p>
			</div>
			<div id="operate" style="float: right; padding: 10px 20px">
				<input type="button" class="action" value="{action}">
				<input type="button" class="del" value="删除">
			</div>
		</div>
		<div id="showResult" style="padding: 10px 20px">
<form>
			<table>
				<tbody>
					<tr>
						<td>部门名称：</td>
						<td>
							<input type="text" id="deptName" name="deptName" value="{cname}">
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>部门编号：</td>
						<td>
							<input type="text" id="deptNo" name="deptNo"  value="{deptNo}">
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>部门描述：</td>
						<td>
							<input type="text" id="deptDsc" name="deptDsc"  value="{dsc}">
						</td>
					</tr>
					<tr></tr>
				</tbody>
			</table>
</form>
		</div>
		</script>
		<script id='addServerTEMP' type="template">
		<div style="width: 100%; height: 50px">
			<div style="float: left">
				<p id="ip_addr" style="padding: 10px 20px; color: red; text-align: left">{title}</p>
			</div>
			<div id="operate" style="float: right; padding: 10px 20px">
				<input type="button" class="action" value="{action}">
			</div>
		</div>
		<div id="showResult" style="padding: 10px 20px">
<form id="serverForm">
			<table>
				<tbody>
					<tr>
						<td>服务器IP：</td>
						<td>
<input type="hidden" value="{id}" name="id"/>
							<input type="text" name="ipAddr" id="ipAddr" maxlength="20" value="{ipAddr}">
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>附加功能：</td>
						<td>
							<input type="checkbox" name="isEnhance" id="isEnhance" {isEnhance} value="1">增强
						</td>
					</tr>
					<tr></tr>
					<tr><td>所属部门：</td>
<td>
<select id="deptId" name="deptId" style="width:142px" >
{deptOption}
</select></td></tr>
					<tr></tr>
				</tbody>
			</table>
</form>
		</div>
<div id="showResult" style="padding:10px 20px">
				</script>
		<script id='deptListTEMP' type="template">
				<div class="depts" id="{deptId}" deptData="{deptData}" style="cursor: pointer" >
					<img id="img{deptId}" src="/images/subheader_expand.png" align="middle">
					<span id="span{deptId}">{cname}</span>
				</div>
				<div id="menu{deptId}" style="display: none">
				</div>
				</script>
		<script id='serverListTEMP' type="template">
				   <li>
					<a id="server{id}" href="javascript:void(0)" >{ipAddr}</a>
                   </li>
				</script>
		<script id='showServerTEMP' type="template">
		  	<div style="width:100%;height:50px">
		  		<div style="float:left">
			 		 <p id="ipAddr" style="padding:10px 20px;color:red;text-align:left">{ipAddr}</p>
			 	</div>
			  <div id="operate" style="float:right;padding:10px 20px">
			</div>
			  </div>
<div id="showResult" style="padding:10px 20px">正在查询中，请稍候。。。</div>
				</script>
		<script id='serverOperaTEMP' type="template">
<input type="button" id="updateServerBT" value="修改"/>
<input type="button" id="{status}" value="{statusVal}" />
<input type="button" id="delServerBT" value="删除" />
		  		</script>
		<script id='serverStatusTEMP' type="template">
		<p>正在转码数：{analysising}</p>
		<p>等待转码数：{waitinganAlysising}</p>
		<p>正在分析数：{transcoding}</p>
		<p>等待分析数：{waitingTranscoding}</p>
		<p>内存占用：{com_mem}%</p>
		<p>CPU占用：{com_cup}%</p>
		  		</script>

	</body>
</html>