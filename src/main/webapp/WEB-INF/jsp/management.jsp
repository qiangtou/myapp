<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="/css/style.css">
		<script type="text/javascript" src="/js/lib/json2.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
		<script type="text/javascript" src="/js/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.validate.min.js"></script>
		<script type="text/javascript" src="/js/lib/xml2json.js"></script>
		<script type="text/javascript" src="/js/manage.js"></script>
		<title>分布式网管系统</title>
		<style>
			.error {
			color: red;
			font-style: italic;
			}
			body {
			padding-top: 0px;
			}
			.container-content .panel{
			height:500px;
			}
           #menuList{
            height:455px;
            overflow:auto;
            }
		</style>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col-md-12"> 
					<div class="panel panel-default" style="marging: 5x 0px; padding: 10px 0px">
						<button id='addServer' type="button" class="btn btn-primary">添加服务器</button>
						<button id='addDept' type="button" class="btn btn-primary">添加部门</button>
						<div style="float:right;margin-right:10px;">
							当前在线&nbsp;
							<span id="online_count">${onlinenum}</span>
							&nbsp;人&nbsp;${user}&nbsp;
							<a id='logout' href="#" title='注销'>
								<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
								注销
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="row container-content">
				<div class="col-md-3"> 
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
							部门列表
						</div>
						<div class="panel-body" id="menuList">
							<c:forEach var="dept" items="${deptList}">
							<div class="depts" id="${dept.deptId }"
								deptData="${dept.cname },${dept.deptNo },${dept.dsc }"
								style="cursor: pointer">
								<span id="img{deptId}" class="glyphicon glyphicon-plus" aria-hidden="true"></span>
								<span id="span${dept.deptId}">${dept.cname }</span>
							</div>
							<div id="menu${dept.deptId}" style="display: none"></div>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div  id='content' class="panel panel-default">
					</div>
				</div>
				<div class="col-md-3">
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span>在线用户
						</div>
						<div class="panel-body" id="online-user" style="height:100%">
							<ol class="onlineuser"></ol>
							<ul class="pagination" style="bottom: 10px;position: absolute;">
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script id='deptTEMP' type="template">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
				{title}</div>
			<div class="panel-body">
				<div id="showResult" style="padding: 10px 20px">
					<form>
						<table>
							<tbody>
								<tr>
									<td>部门名称</td>
									<td>
										<input type="text" class="form-control" id="deptName" name="deptName" value="{cname}">
									</td>
								</tr>
								<tr></tr>
								<tr>
									<td>部门编号</td>
									<td>
										<input type="text" class="form-control" id="deptNo" name="deptNo"  value="{deptNo}">
									</td>
								</tr>
								<tr></tr>
								<tr>
									<td>部门描述</td>
									<td>
										<input type="text" class="form-control" id="deptDsc" name="deptDsc"  value="{dsc}">
									</td>
								</tr>
								<tr></tr>
							</tbody>
						</table>
					</form>
				</div>
				<div id="operate" style="padding: 10px 20px">
					<button type="button" class="action btn btn-primary">{action}</button>
					<button type="button" class="del btn btn-primary">删除</button>
				</div>
			</div>
		</script>
		<script id='addServerTEMP' type="template">
			<div class="panel-heading">
				<span class="glyphicon glyphicon-hdd" aria-hidden="true"></span>
				{title}</div>
			<div class="panel-body">
				<div id="showResult" style="padding: 10px 20px">
					<form id="serverForm">
						<table>
							<tbody>
								<tr>
									<td>服务器IP</td>
									<td>
										<input type="hidden" value="{id}" name="id"/>
										<input type="text" class="form-control" name="ipAddr" id="ipAddr" maxlength="20" value="{ipAddr}">
									</td>
								</tr>
								<tr></tr>
								<tr>
									<td>附加功能</td>
									<td>
										<label><input type="checkbox" class="checkbox-inline"  name="isEnhance" id="isEnhance" {isEnhance} value="1">增强</label>
									</td>
								</tr>
								<tr></tr>
								<tr><td>所属部门</td>
									<td>
										<select id="deptId" class='form-control' name="deptId" style="width:142px">
											{deptOption}
								</select></td></tr>
								<tr></tr>
							</tbody>
						</table>
					</form>
				</div>
				<div id="operate" style="padding: 10px 20px">
					<button type="button" class="action btn btn-primary">{action}</button>
				</div>
			</div>
		</script>
		<script id='deptListTEMP' type="template">
			<div class="depts" id="{deptId}" deptData="{deptData}" style="cursor: pointer">
				<span id="img{deptId}" class="glyphicon" aria-hidden="true"></span>
				<span id="span{deptId}">{cname}</span>
			</div>
			<div id="menu{deptId}" style="display: none"/>
			</script>
			<script id='serverListTEMP' type="template">
				<li>
				<a id="server{id}" href="javascript:void(0)">
					<span class="glyphicon glyphicon-hdd" aria-hidden="true"></span>{ipAddr}
				</a>
				</li>
			</script>
			<script id='showServerTEMP' type="template">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-hdd" aria-hidden="true"></span>{ipAddr}
				</div>
				<div class="panel-body">
					<div id="showResult" style="padding:10px 20px">正在查询中，请稍候。。。</div>
					<div id="operate" class="col-md-12"></div>
				</div>
			</script>
			<script id='serverOperaTEMP' type="template">
				<div class="col-md-2"><button type="button" id="updateServerBT" class="btn btn-primary">修改</button></div>
				<div class="col-md-2"><button type="button" id="{status}"  class="btn btn-primary">{statusVal}</button></div>
				<div class="col-md-2"><button type="button" id="delServerBT" class="btn btn-primary">删除</button></div>
			</script>
			<script id='serverStatusTEMP' type="template">
				<div class="row">
					<div class="col-md-3">正在转码数</div>
					<div class="col-md-9">{analysising}</div>
				</div>
				<div class="row">
					<div class="col-md-3">等待转码数</div>
					<div class="col-md-9">{waitingTranscoding}</div>
				</div>
				<div class="row">
					<div class="col-md-3">正在分析数</div>
					<div class="col-md-9">{transcoding}</div>
				</div>
				<div class="row">
					<div class="col-md-3">等待分析数</div>
					<div class="col-md-9">{waitinganAlysising}</div>
				</div>
				<div class="row">
					<div class="col-md-3">硬盘剩余容量</div>
					<div class="col-md-9">{free_disk}GB</div>
				</div>
				<div class="row">
					<div class="col-md-3">硬盘总容量</div>
					<div class="col-md-9">{total_disk}GB</div>
				</div>
				<div class="row">
					<div class="col-md-3">使用比率</div>
					<div class="col-md-9">
						<div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="{used_disk}" aria-valuemin="0" aria-valuemax="100" style="width:{used_disk}%;">
								{used_disk}%
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">内存占用</div>
					<div class="col-md-9">
						<div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="{com_mem}" aria-valuemin="0" aria-valuemax="100" style="width:{com_mem}%;">
								{com_mem}%
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">CPU占用</div>
					<div class="col-md-9">
						<div class="progress">
							<div class="progress-bar" role="progressbar" aria-valuenow="{com_cpu}" aria-valuemin="0" aria-valuemax="100" style="width:{com_cpu}%;">
								{com_cup}%
							</div>
						</div>
					</div>
				</div>
			</script>
			<script id='pageTEMP' type="template">
				<li class="{class}"><a href="#" >{page}</a></li>
			</script>
		</body>
	</html>
