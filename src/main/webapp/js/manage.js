$(function() {
	// 取html模板的辅助工具
	var getTemplate = function(id) {
		return $.trim($('#' + id).html().replace(/[\r\n]/g, '')
				.replace(/>\s+</g, '><'));
	}
	//中间的内容区
	var $content = $('#content'), 
	//需要用到的html模板
	addServerTEMP = getTemplate('addServerTEMP'), 
	serverListTEMP = getTemplate('serverListTEMP'), 
	showServerTEMP = getTemplate('showServerTEMP'), 
	serverStatusTEMP = getTemplate('serverStatusTEMP'), 
	deptTEMP = getTemplate('deptTEMP'), 
	deptListTEMP = getTemplate('deptListTEMP');

	// 页面缓存,存放部门和服务器,及两者关系
	var cache={
			dept:{}	,
			server:{},
			deptServer:{}
	}
	//服务器点击事件
	var $menuList = $('#menuList').on('click','a',function(){
			var serverId=this.id.replace('server',''),
			server=cache.server[serverId];
			$content.html(replace(showServerTEMP,server));
			fetchServerStatus();
			/*$content.off('click','#updateServerBT')
			.off('click','#activeServerBT')
			.off('click','#delServerBT')
			.on('click','#updateServerBT',function(){
				console.log(this.id);
			})
			.on('click','#activeServerBT',function(){
				console.log(this.id);
			})
			.on('click','#delServerBT',function(){
				console.log(this.id);
			});*/

	})	
	// 左侧目录委托事件:点击部门展开与关闭
	.on('click','.depts',function() {
						var $this = $(this), deptId = this.id, deptObj = {}, img = $this
								.children('img')[0], menu = $this.next(), deptdata = $
								.attr(this, 'deptdata').split(',');

						deptObj.id = deptId;
						deptObj.cname = deptdata[0];
						deptObj.deptNo = deptdata[1];
						deptObj.dsc = deptdata[2];
						cache.dept[deptId]=$.extend({},deptObj);
						deptObj.title = deptObj.cname;
						deptObj.action = '修改';
						
						$content.html(replace(deptTEMP, deptObj))
						.off('click','.action')
						.on('click','.action',function(){
							// 做修改部门操作
							var data = {
									deptId:deptId,
									cname : $content.find('#deptName').val(),
									deptNo : $content.find('#deptNo').val(),
									dsc : $content.find('#deptDsc').val()
							};
							sync.update('/dept/update.do',data,function(result){
								renderDeptList(result.data,'update');
							});							
						})
						.off('click','.del')
						.on('click','.del',function(){
							// 做删除部门操作
							if(!cache.deptServer[deptId]){
								if(confirm("确定删除?")){
									sync.del('/dept/del.do',{deptId:deptId},function(){
										delete cache.dept[deptId];
										$('#'+deptId).remove();
									});
								}
							}else{
								alert("该部门无法删除，请先转移或删除该部门下的服务器");
							}
						});
						if (isFolded(img)) {
							$.post('/server/list.do', {
								deptId : deptId
							}, function(servers) {
								if (servers && servers.length > 0) {
									cache.deptServer[deptId]=servers;
									menu.html(generateServers(servers)).show();
								}
							});
							img.src = '/images/subheader_fold.png';
						} else {
							menu.hide();
							img.src = '/images/subheader_expand.png';
						}
					});
	
	// 添加服务器事件
	$('#addServer').click(function() {
		$content.html(replace(addServerTEMP, {
			title : '添加服务器',
			action : '添加'
		}))
		//确定添加
		.off('click', '.action')
		.on('click', '.action', function() {
			var data = {
				ipAddr : $content.find('#ipAddr').val(),
				deptId : $content.find('#deptId').val()
			};
			//持久化到后台
			sync.save('/server/add.do',data,renderServerList);
		});
		$content.find('.del').remove();
	});
	// 添加部门事件
	$('#addDept').click(function() {
		$content.html(replace(deptTEMP, {
			title : '添加部门',
			action : '添加'
		}))
		//确定添加
		.off('click', '.action')
		.on('click', '.action', function() {
			var data = {
				cname : $content.find('#deptName').val(),
				deptNo : $content.find('#deptNo').val(),
				dsc : $content.find('#deptDsc').val()
			};
			//持久化到后台
			sync.save('/dept/add.do',data,function(result){
				renderDeptList(result.data,'add');
			});
		});
		$content.find('.del').remove();
	});
	
	var fetchServerStatus=function(serverId){
		sync.getJson('/server/status.do',{serverId:serverId},function(result){
			var status=result.data;
			if(status){
				$content.find('#showResult').html(replace(serverStatusTEMP,status))
			}else{
				//TODO 未激活的情况
				console.log('未激活的情况');
			}
		});
	}
	//持久化工具
	var sync = {
			getJson:function(url,data,callback){
			$.ajax( {
			url : url,
			type : 'post',
			data : data,
			dataType : "json",
			error : function() {},
			success:function(result){
				if (result.success) {
					if(typeof callback=='function')
						callback(result);
				}else if(result.msg){
					alert(result.msg);
				}
			}
			});
		},
		base:function(action,url, data, callback){
		$.ajax( {
			url : url,
			contentType : 'application/json',
			type : 'post',
			data : JSON.stringify(data),
			dataType : "json",
			error : function() {
				alert(action+'失败');
			},
			success : function(result) {
				if (result.success) {
					if(typeof callback=='function')
					callback(result);
					alert(action+'成功');
				} else if(result.msg){
					alert(result.msg);
				}else {
					alert(action+'失败');
				}
			}
		});
		},
		del:function(){
			var args=Array.prototype.slice.call(arguments);
			args.unshift('删除');
			sync.base.apply(null,args);		
		},
		save : function(url, data, callback) {
			var args=Array.prototype.slice.call(arguments);
			args.unshift('添加');
			sync.base.apply(null,args);
		},
		update: function(url, data, callback) {
			var args=Array.prototype.slice.call(arguments);
			args.unshift('修改');
			sync.base.apply(null,args);			
		}
	}
	//添加,修改服务器成功后的回调
	var renderServerList = function(obj) {
		if (!obj) return;
		console.log(obj);
		// TODO 显示服务器
		// $menuList.append(replace(menuTEMP,obj));
	}
	//添加,修改部门成功后的回调
	var renderDeptList = function(obj, action) {
		if (!obj)return;
		// deptData="${dept.cname },${dept.deptNo },${dept.dsc }"
		obj.deptData = '' + [ obj.cname, obj.deptNo, obj.dsc ];
		( {
			add : function() {
				$menuList.append(replace(deptListTEMP, obj));
			},
			update : function() {
				console.log('进入修改后的回调');
				$('#' + obj.deptId).attr('deptdata', obj.deptData).find('span')
						.html(obj.cname);
				$content.find('#title').html(obj.cname);
			}
		})[action]();

	}
	//判断折叠状态
	var isFolded = function(img) {
		return img.src.indexOf('subheader_expand') > -1;
	}
	//模板替换工具
	var replace = function(template, data) {
		return template.replace(/{\w+}/g, function(key) {
			key = key.slice(1, -1);
			return data[key] ? data[key] : '';
		});
	}
	//展开时要生成的服务器列表html
	var generateServers = function(servers) {
		var server, html, arr = [];
		for ( var i = 0, len = servers.length; i < len; i++) {
			server = servers[i];
			cache.server[server.id]=server;
			html=replace(serverListTEMP,server);
			arr.push(html);
		}
		return arr.join('');
	}
});