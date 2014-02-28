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
	serverOperaTEMP = getTemplate('serverOperaTEMP'),
	deptTEMP = getTemplate('deptTEMP'),	
	deptListTEMP = getTemplate('deptListTEMP');

	// 页面缓存,存放部门和服务器,及两者关系
	var cache={
			dept:{}	,
			server:{},
			deptServer:{}
	}
	window.c=cache;
	$('.depts').each(function(){
		var $this=$(this),
		deptdata=$this.attr('deptdata').split(',');
		cache.dept[this.id]={
				deptId:this.id,
				cname:deptdata[0],
				deptNo:deptdata[1],
				des:deptdata[2]
		};
	});
	//服务器点击事件
	var $menuList = $('#menuList').on('click','a',function(){
			var self=this,serverId=this.id.replace('server',''),
			server=cache.server[serverId];
			$content.html(tmpl(showServerTEMP,server));
			fetchServerStatus(server);
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
						deptObj.title = deptObj.cname;
						deptObj.action = '修改';
						
						$content.html(tmpl(deptTEMP, deptObj));
						var $form=$content.find('form')
						validDept($form);
						
						$content.off('click','.action')
						.on('click','.action',function(){
							// 做修改部门操作
							if($form.valid()){
							var data = {
									deptId:deptId,
									cname : $content.find('#deptName').val(),
									deptNo : $content.find('#deptNo').val(),
									dsc : $content.find('#deptDsc').val()
								};
								sync.action('修改')('/dept/update.do',data,function(result){
									renderDeptList(result.data,'update');
								});	
							}
						})
						.off('click','.del')
						.on('click','.del',function(){
							// 做删除部门操作
							var servers=cache.deptServer[deptId];
							if(!servers||servers.length==0){
								if(confirm("确定删除?")){
									sync.action('删除')('/dept/del.do',{deptId:deptId},function(){
										delete cache.dept[deptId];
										$('#'+deptId).remove();
										$content.html('');
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
								if ($.isArray(servers)&&servers.length>0) {
									cache.deptServer[deptId]=servers;
									for(var i=servers.length;i--;){
										cache.server[servers[i].id]=servers[i];
									}
									menu.html(tmpl(serverListTEMP,servers)).show();
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
		saveOrUpdateServer('添加服务器','添加','/server/add.do');
	});
	var saveOrUpdateServer=function(title,action,url,server){
		if(server){
			var dept=cache.dept[server.deptId];
			dept.selected='selected';
		}
		
		var data=$.extend({},server,{
			title : title,
			action : action,
			isEnhance:server && server.isEnhance?'checked':'',
			deptOption:tmpl('<option {selected} value="{deptId}">{cname}</option>',_toArray(cache.dept))
		});
		$content.html(tmpl(addServerTEMP, data));
		var $form=$content.find('form');
		validServer($form);
		$content.off('click', '.action')
		.on('click', '.action', function() {
			if ($form.valid()) {
				var data = {
					id : server ? server.id : '',
					ipAddr : $content.find('#ipAddr').val(),
					isEnhance : $content.find('#isEnhance:checked').val() || 0,
					deptId : $content.find('#deptId').val()
				};
				//持久化到后台
				sync.action(action)(url, data, renderServerList);
			}
		});
	}
	// 添加部门事件
	$('#addDept').click(function() {
		$content.html(tmpl(deptTEMP, {
			title : '添加部门',
			action : '添加'
		}));
		var $form=$content.find('form')
		validDept($form);
		$content.off('click', '.action')
		.on('click', '.action', function() {
			if($form.valid()){
			var data = {
				cname : $content.find('#deptName').val(),
				deptNo : $content.find('#deptNo').val(),
				dsc : $content.find('#deptDsc').val()
			};
			//持久化到后台
			sync.action('添加')('/dept/add.do',data,function(result){
				renderDeptList(result.data,'add');
			});
			}
		});
		$content.find('.del').remove();
	});
	
	var fetchServerStatus=function(server){
		sync.getXML('/server/status.do',{serverId:server.id,sid:Math.random()},function(result){
			var statusOperate;
			if(result.ret!=0){
                alert("服务器状态查询出错");
				return ;
			}
			if(result.isValid==1){
				$content.find('#showResult').html(tmpl(serverStatusTEMP,result));
				statusOperate={status:'disable',statusVal:'禁用'}
			}else if(result.isValid==0){
				$("#showResult").html("<h4>服务器未激活</h4>");
				statusOperate={status:'enable',statusVal:'激活'}
			}
			
			$content.find('#operate').html(tmpl(serverOperaTEMP,statusOperate))
			.off('click','#updateServerBT')
			.off('click','#delServerBT')
			.on('click','#updateServerBT',function(){
				saveOrUpdateServer('修改服务器','修改','/server/update.do',server);
			})
			.off('click','#'+statusOperate.status)
			.on('click','#'+statusOperate.status,function(){
				if(confirm('确定'+statusOperate.statusVal+server.ipAddr+'?'))
				sync.action(statusOperate.statusVal)('/server/'+statusOperate.status+'.do',{id:server.id},function(){
						$('#server'+server.id).click();
				});
			})
			.on('click','#delServerBT',function(){
				if(confirm('确定删除'+server.ipAddr+'?'))
				sync.action('删除')('/server/del.do',{id:serverId},function(){
					delete cache.server[serverId];
					cache.deptServer[server.deptId]=_toArray(cache.server);
					$(self).parent().remove();
					$content.html('');
				});
			});
			
			
		});
	}
	//持久化工具
	var sync = {
			getXML:function(url,data,callback){
			$.ajax( {
			url : url,
			type : 'post',
			data : data,
			dataType : "xml",
			timeout: 5000,
			error : function(jqXHR, textStatus) {
				if(textStatus==="timeout") {
			           alert('获取超时!!')
			     } 
				if(textStatus==="parsererror") {
					alert('返回数据格式错误!')
				} 
			},
			success:function(xml){
				var result=JSON.parse(xml2json(xml)).result;
				if (result) {
					if(typeof callback=='function')
						callback(result);
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
		action:function(actionStr){
			var self=this;
			return function(){
				var args=Array.prototype.slice.call(arguments);
				args.unshift(actionStr);
				self.base.apply(null,args);		
			}
		}
	}
	//添加,修改服务器成功后的回调
	var renderServerList = function(obj) {
		var server=obj.data;
		if (!server) return;
		var serverId = server.id;
		var oldServer=cache.server[serverId];
		//维护缓存
		if(oldServer){
			$('#server'+serverId).parent().remove();
			var oldDeptId=oldServer.deptId;
			var servers=cache.deptServer[oldDeptId];
				if($.isArray(servers) && servers.length>0){
					for(var i=servers.length;i--;){
						if(servers[i].id==serverId){
							servers[i]=server;
						}
					}
				}
		}
		cache.server[serverId]=server;
		var img=$('#img'+server.deptId);
		//如果不是折叠状态则显示
		if(!isFolded(img[0])){
			$('#menu'+server.deptId).append(tmpl(serverListTEMP,server)).show();
		}
	}
	//添加,修改部门成功后的回调
	var renderDeptList = function(obj, action) {
		if (!obj.data)return;
		// deptData="${dept.cname },${dept.deptNo },${dept.dsc }"
		obj.deptData = '' + [ obj.cname, obj.deptNo, obj.dsc ];
		cache.dept[obj.deptId]=obj;
		( {
			add : function() {
				$menuList.append(tmpl(deptListTEMP, obj));
			},
			update : function() {
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
	
	//验证相关
	$.extend($.validator.messages,{
		maxlength:$.format('长度不能超过{0}')
	});
	$.validator.addMethod("ip", function(value, element, param) {
		return this.optional(element) || /^(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)$/i.test(value);
	}, "ip格式有误");

	var validServer = function (form) {
		form.validate({
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
	}
	var validDept = function (form) {
		form.validate({
			onkeyup : false,
			messages : {
				deptName : {
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
				deptName : {
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
	}
	
	
	//对象转数组工具
	var _toArray=function(obj){
		var arr=[];
		for(o in obj){
			arr.push(obj[o]);
		}
		return arr;
	}
	//模板替换工具
	var tmpl = function(template, data) {
		var d,arr=[],html = '';
		if (!$.isArray(data)) {
			data=data||{};
			data = [ data ];
		}
		for ( var i = 0, len = data.length; i < len; i++) {
			d = data[i];
			html = template.replace(/{\w+}/g, function(key) {
				key = key.slice(1, -1);
				return d[key] ? d[key] : '';
			});
			arr.push(html);
		}
		return arr.join('');
	}
});
