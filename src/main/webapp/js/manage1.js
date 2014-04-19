$(function() {
	//和后台同步的工具
	var sync = {
		server : {
			list : function(deptId, fn) {
				$.ajax( {
					url : '/server/list.do',
					type : 'post',
					error : function() {
						alert('获取服务器错误');
					},
					data : {
						deptId : deptId
					},
					success : function(json) {
						if (json ) {
							fn(json)
						} else {
							alert('获取服务器失败');
						}
					}
				});
			},
			add:function(server,fn){
				//TODO 同步到后台，新增一个服务器,fn为成功回调
				fn(server)
			},
			update:function(server,fn){
				//TODO 同步到后台，更新一个服务器,fn为成功回调
				fn(server);
			},
			del:function(serverId,fn){
				//TODO 同步到后台，删除一个服务器,fn为成功回调
				fn(serverId);
			}
			
		},
		dept : {
			list : function(fn) {
				$.ajax( {
					url : '/dept/list.do',
					error : function() {
						alert('获取部门错误');
					},
					success : function(json) {
						if (json && json.success) {
							fn(json.data)
						} else {
							alert('获取部门失败');
						}
					}
				});
			},
			add:function(dept,fn){
				//TODO 同步到后台，删除一个部门,fn为成功回调
				fn(dept);
			},
			update:function(dept,fn){
				//TODO 同步到后台，删除一个部门,fn为成功回调
				fn(dept);
			},
			del:function(deptId,fn){
				//TODO 同步到后台，删除一个部门,fn为成功回调
				fn(deptId);
			}
		}
	}
	//取得空对象的辅助方法
	var getEmpty = {
		server : function() {
			return {
				deptId:'',
				ipAddr : '',
				isEnhance : []
			};
		},
		dept : function() {
			return dept = {
				cname : '',
				deptNo : '',
				dsc : "",
				expanded:false
			};
		}
	}
	//定义上方的操作按键
	var manager = avalon.define("manager", function(vm) {
		vm.addServer = function() {
			content.tmpl = "addServerTEMP";
			content.action = 'add';
			var depts=menu.depts.$model;
			content.depts=depts;
			content.server = getEmpty.server();
			content.server.deptId=depts[0].deptId;
			content.title = '添加服务器';
		}
		vm.addDept = function() {
			content.tmpl = "deptTEMP";
			content.action = 'add';
			content.title = '添加部门';
			content.dept = getEmpty.dept();
		}

	});
	//定义左侧目录
	var menu = avalon.define("menu", function(vm) {
		vm.depts = [];
		vm.showServers = function(dept) {
			if (!dept.hasFetchServers) {
				sync.server.list(dept.deptId, function(servers) {
					dept.servers = servers;
				});
				dept.hasFetchServers = true;
				dept.expanded = true;
			} else {
				dept.expanded = !dept.expanded;
			}
			content.tmpl = "deptTEMP";
			content.action = 'update';
			content.title = dept.cname;
			content.dept = content.$old=dept;
		}
		vm.updateServer=function(server){
			content.tmpl = "addServerTEMP";
			content.action = 'update';
			content.title = server.ipAddr;
			content.server =content.$old=server;
		}
	});
	//初始化部门列表
	sync.dept.list(function(depts) {
		depts.forEach(function(dept) {
			dept.servers = [];
			dept.hasFetchServers = false;
			dept.expanded = false;
		});
		menu.depts = depts;
	});
//定义中间的操作区
	var content = avalon.define("content", function(vm) {
		vm.tmpl = null;
		vm.action = null;
		vm.title = null;
		vm.dept = getEmpty.dept();
		vm.deptId=null
		vm.depts = menu.depts;
		vm.$old={};
		vm.server = getEmpty.server();
		//添加服务器
		vm.addServer=function(){
			server=content.server.$model;
			sync.server.add(server,function(){
				menu.depts.$model.forEach(function(dept,i){
					if(dept.deptId==server.deptId){
						menu.depts[i].servers.push(server);
						return false;
					}
				});
			});
		}
		//修改服务器
		vm.updateServer=function(){
			server=content.server.$model;
			sync.server.update(server,function(){
				$.extend(content.$old,server)
			});
		}
		//删除服务器
		vm.deleteServer=function(){
			var server = content.server;
			var deptId=server.deptId;
			sync.server.del(server.serverId,function(){
				var servers=menu.depts.filter(function(d){
					return d.deptId==deptId;
				})[0].servers;
				content.tmpl='empty';
				servers.remove(content.$old);
			});
		
		}
		//添加部门
		vm.addDept=function(){
			dept=content.dept.$model;
			sync.dept.add(dept,function(){
				menu.depts.push(dept)
			})
		}
		//修改部门
		vm.updateDept=function(){
			var dept=content.dept.$model;
			sync.dept.update(dept,function(){
				$.extend(content.$old,dept);
			});
		}
		//删除部门
		vm.delDept=function(){
			sync.dept.del(content.dept.deptId,function(){
				content.tmpl='empty';
				menu.depts.remove(content.$old);
			});
		}
	});
});