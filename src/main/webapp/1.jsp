<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="/js/lib/json2.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.min.js"></script>
		<script type="text/javascript" src="/js/lib/jquery.validate.js"></script>
		<script type="text/javascript" src="/js/lib/xml2json.js"></script>
		<script type="text/javascript" src="/js/lib/avalon.js"></script>
		<script>
			$.extend($.validator.messages,{
				maxlength:$.format('长度不能超过{0}')
			});
			$.validator.addMethod("ip", function(value, element, param) {
				return this.optional(element) || /^(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)$/i.test(value);
			}, "ip格式有误");
			var server={
				blank:function(){
					return {id:'', ipAddr:'', isValid:'', isEnhance:'', deptId:''};
				},
				validate:function(s){
					s.validate({
						onkeyup : false,
						messages : {
							ipAddr : {
								required : 'ip地址不能为空'
							}
						},
						rules : {
							ipAddr : {
								required : true,
								ip : true
							}
						}
					});
					return s;
				},
				fetch:function(dept){
					$.post('/server/list.do',{deptId:dept.deptId},function(json){
						var servers=json;
						dept.servers=servers;
					});
				},
				add:function(s){
					console.log('add server');
				},
				update:function(s){
					console.log('update server');
				},
				del:function(s){
					console.log('del server');
				}
			}
			var dept={
				blank:function(){
					return {cname:'',deptNo:'',dsc:''};
				},
				validate:function(s){
					s.validate({
						onkeyup : false,
						messages : {
							cname : {
								required : '部门名不能为空'
							},
							deptNo : {
								required : '部门编号不能为空'
							},
							deptDsc : {
								required : '部门描述不能为空'
							}
						},
						rules : {
							cname : {
								required : true,
								maxlength : 16
							},
							deptNo : {
								required : true,
								maxlength : 6
							},
							deptDsc : {
								required : true,
								maxlength : 16
							}
						}
					});
					return s;
				},
				add:function(s){
					console.log('add dept');
				},
				update:function(s){
					console.log('update dept');
				},
				del:function(s){
					console.log('del dept');
				}
			}
			var app=avalon.define("app",function(vm){
				vm.depts=[];
				vm.operate='';
				vm.expand=function(dept){
					var isFold=dept.isFold=!dept.isFold;
					!isFold && server.fetch(dept);
     //修改部门
					operate.tmpl='deptTEMP';
					operate.action='update';
					operate.title=dept.cname;
					operate.dept=dept;
					operate.$dept=$.extend({},dept);
				}
			});	
			app.init=function(){
				$.post('/dept/list.do',function(json){
					var depts=json.data;
					$.each(depts,function(i,d){
						d.isFold=true;
						d.servers=[];
					})
					app.depts=depts;
				});
			}   
			var action=function(o,action,m,$m){
				return function(){
					var form= o.validate($('#content').find('form'));
					if(form.valid()){
						o[action](m.$model);
					}else{
      m=$m;
      }
				}
			}
			var operate=avalon.define("operate",function(vm){
				vm.tmpl='';
				vm.action='';
				vm.title='';
				vm.server=server.blank();
				vm.$server=server.blank();
				vm.dept=dept.blank();
				vm.$dept=dept.blank();

				vm.addServer=action(server,'add',vm.server);
				vm.updateServer=action(server,'update',vm.server,vm.$server);
				vm.addDept=action(dept,'add',vm.dept);
				vm.updateDept=action(dept,'update',vm.dept,vm.$dept);
				vm.delDept=action(dept,'del',vm.dept,vm.$dept);
			});

			avalon.define("main-operate",function(vm){
				vm.addServer=function(){
					operate.tmpl="addServerTEMP";
					operate.action='add';
					operate.title='添加服务器';
					operate.server=server.blank();
					operate.server.deptId=app.depts[0].deptId;
                    //$('content').find('form').validate().resetForm();
				}
				vm.addDept=function(){
					operate.tmpl="deptTEMP";
                    //$('content').find('form').validate().resetForm();
					operate.action='add';
					operate.title='添加部门';
					operate.dept=dept.blank();
				}
			});
			app.init();

		</script>
		<title>分布式网管系统</title>
		<style type="text/css">
			.error {
			color: red;
			font-style: italic;
			}
		</style>
	</head>
	<body style="text-align: center">
		<div style="border: 2px solid black; height: 100%; width: 900px">
			<div style="border: 2px solid grey; height: 40px; width: 99.5%; text-align: right;">
				<div ms-controller="main-operate" style="marging: 5x 2px; padding: 10px 2px">
					<div style="float: left">
						<input id='addServer' type="button" ms-click="addServer" value="添加服务器">
					</div>
					<div style="float: left">
						<input id='addDept' type="button" ms-click="addDept" value="添加部门">
					</div>
					登陆账户：${user}&nbsp;
				</div>
			</div>

			<div ms-controller="app">
				<div ms-each-dept="depts" style="float: left; border: 2px solid grey; width: 20%; height: 400px; overflow-y: auto; text-align: left; vertical-align: middle" id="menuList">
					<div class="depts" ms-click="expand(dept)" style="cursor: pointer">
						<img ms-src="{{dept.isFold?'/images/subheader_expand.png':'/images/subheader_fold.png'}}" align="middle">
						<span ms-text=dept.cname ></span>
					</div>
					<div ms-if="!dept.isFold" ms-each-server="dept.servers">
						<li><a href="javascript:void(0)">{{server.ipAddr}}</a></li>
					</div>
				</div>
				<div id='content' ms-controller="operate" ms-include="tmpl" style="border: 2px solid grey; width: 79%; height: 400px;">
				</div>
			</div>
		</div>

		<script id='deptTEMP' type="template">
			<div style="width: 100%; height: 50px">
				<div style="float: left">
					<p id="title" style="padding: 10px 20px; color: red; text-align: left">{{title}}</p>
				</div>
				<div id="operate" style="float: right; padding: 10px 20px">
					<input type="button" ms-click="addDept" ms-if="action=='add'" class="action" value="添加">
					<input type="button" ms-click="updateDept" ms-if="action=='update'" class="action" value="修改">
					<input type="button" ms-click="delDept" ms-if="action=='update'" class="del" value="删除">
				</div>
			</div>
			<div id="showResult" style="padding: 10px 20px">
				<form>
					<table>
						<tbody>
							<tr>
								<td>部门名称：</td>
								<td>
									<input type="text" id="deptName" name="cname" ms-duplex=dept.cname />
								</td>
							</tr>
							<tr></tr>
							<tr>
								<td>部门编号：</td>
								<td>
									<input type="text" id="deptNo" name="deptNo"  ms-duplex=dept.deptNo />
								</td>
							</tr>
							<tr></tr>
							<tr>
								<td>部门描述：</td>
								<td>
									<input type="text" id="deptDsc" name="deptDsc"  ms-duplex=dept.dsc />
								</td>
							</tr>
							<tr></tr>
						</tbody>
					</table>
				</form>
			</div>
		</script>
		<script id='addServerTEMP' type="template">
			<div ms-controler="operate-server" style="width: 100%; height: 50px">
				<div style="float: left">
					<p id="ip_addr" style="padding: 10px 20px; color: red; text-align: left">{{title}}</p>
				</div>
				<div id="operate" style="float: right; padding: 10px 20px">
					<input type="button" ms-if="action=='add'" ms-click='addServer' value="添加">
					<input type="button" ms-if="action=='update'" ms-click='updateServer' class="action" value="修改">
				</div>
			</div>
			<div id="showResult" style="padding: 10px 20px">
				<form id="serverForm">
					<table>
						<tbody>
							<tr>
								<td>服务器IP：</td>
								<td>
									<input type="hidden" ms-duplex=server.id name="id"/>
									<input type="text" name="ipAddr" id="ipAddr" maxlength="20" ms-duplex=server.ipAddr>
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
									<select id="deptId" name="deptId" style="width:142px" ms-duplex="server.deptId" ms-each-dept=depts >
										<option ms-value="dept.deptId" >{{dept.cname}}</option>
									</select>
							</td></tr>
							<tr></tr>
						</tbody>
					</table>
				</form>
			</div>
			<div id="showResult" style="padding:10px 20px">
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
