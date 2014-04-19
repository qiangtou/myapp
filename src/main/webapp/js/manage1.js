$(function() {
	var sync = {
		servers : {
			fetch : function(deptId,fn) {
				$.ajax( {
					url : '/server/list.do',
					type:'post',
					error : function() {
						alert('获取服务器错误');
					},
					data:{deptId:deptId},
					success : function(json) {
						if (json && json.length>=0 ) {
							fn(json)
						} else {
							alert('获取服务器失败');
						}
					}
				});
			}
		},
		depts : {
			fetch : function(fn) {
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
			}
		}
	}

	var main=window.m = avalon.define("main", function(vm) {
		vm.depts = [];
		vm.showServers = function(dept) {
			if (!dept.hasFetchServers) {
				sync.servers.fetch(dept.deptId,function(servers){
					dept.servers=servers;
				});
				dept.hasFetchServers = true;
				dept.expanded=true;
			} else {
				dept.expanded = !dept.expanded;
			}
		}
	});

	sync.depts.fetch(function(depts){
		depts.forEach(function(dept){
			dept.servers=[];
			dept.hasFetchServers=false;
			dept.expanded=false;
		});
		main.depts=depts;
	});
});
