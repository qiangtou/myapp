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
			<div ms-controller="manager"
				style="border: 2px solid grey; height: 40px; width: 99.5%; text-align: right;">
				<div style="marging: 5x 2px; padding: 10px 2px">
					<div style="float: left">
						<input ms-click='addServer' type="button" value="添加服务器">
					</div>
					<div style="float: left">
						<input ms-click='addDept' type="button" value="添加部门">
					</div>
					登陆账户：${user}&nbsp;
				</div>
			</div>

			<div ms-controller="menu">
				<div
					style="float: left; border: 2px solid grey; width: 20%; height: 400px; overflow-y: auto; text-align: left; vertical-align: middle"
					id="menuList">
					<div ms-repeat='depts'>
						<div ms-click='showServers(el)' class="depts"
							 style="cursor: pointer">
							<img  ms-src="/images/subheader_{{el.expanded?'fold':'expand'}}.png"
								align="middle">
							<span >{{el.cname}}</span>
						</div>
						<div ms-visible='el.expanded'>
							<li ms-repeat='el.servers'>
								<a href="javascript:void(0)"
									ms-click="updateServer(el)">{{el.ipAddr}}</a>
							</li>
						</div>
					</div>
				</div>
				<div id='content' ms-controller="content" ms-include="{{tmpl}}"
					style="border: 2px solid grey; width: 79%; height: 400px;">
				</div>
			</div>
		</div>
		<script id='deptTEMP' type="template">
		<div style="width: 100%; height: 50px">
			<div style="float: left">
				<p id="title" style="padding: 10px 20px; color: red; text-align: left">{{title}}</p>
			</div>
			<div id="operate" style="float: right; padding: 10px 20px">
				<input type="button" ms-visible="action=='add'" ms-click="addDept" value="添加">
				<input type="button" ms-visible="action=='update'" ms-click="updateDept" value="修改">
				<input type="button" ms-visible="action!='add'" ms-click="delDept" value="删除">
			</div>
		</div>
		<div id="showResult" style="padding: 10px 20px">
<form>
			<table>
				<tbody>
					<tr>
						<td>部门名称：</td>
						<td>
							<input type="text" id="deptName" name="deptName" ms-duplex='dept.cname'>
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>部门编号：</td>
						<td>
							<input type="text" id="deptNo" name="deptNo"  ms-duplex='dept.deptNo' >
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>部门描述：</td>
						<td>
							<input type="text" id="deptDsc" name="deptDsc" ms-duplex='dept.dsc'>
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
				<p id="ip_addr" style="padding: 10px 20px; color: red; text-align: left">{{title}}</p>
			</div>
			<div id="operate" style="float: right; padding: 10px 20px">
				<input type="button" ms-click="addServer(server)" ms-visible='action=="add"' value="添加">
				<input type="button" ms-click="updateServer(server)" ms-visible='action=="update"' value="修改">
				<input type="button" ms-click="deleteServer(server)" ms-visible='action=="update"' value="删除">
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
							<input type="text" name="ipAddr" id="ipAddr" maxlength="20" ms-duplex="server.ipAddr" >
						</td>
					</tr>
					<tr></tr>
					<tr>
						<td>附加功能：</td>
						<td>
							<input type="checkbox" name="isEnhance" id="isEnhance" ms-duplex="server.isEnhance" >增强
						</td>
					</tr>
					<tr></tr>
					<tr><td>所属部门：</td>
<td>
				<select ms-duplex='server.deptId' name="deptId" style="width:142px" >
						<option ms-repeat="depts" ms-value="el.deptId">{{el.cname}}</option>
				</select>
</td></tr>
					<tr></tr>
				</tbody>
			</table>
</form>
		</div>
<div id="showResult" style="padding:10px 20px">
				</script>
		<script id='deptListTEMP' type="template">
				<div class="depts" id="{deptId}" deptData="{deptData}" style="cursor: pointer" >
					<img  src="/images/subheader_expand.png" align="middle">
					<span >{cname}</span>
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
				<script type="template" id='empty'></script>
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