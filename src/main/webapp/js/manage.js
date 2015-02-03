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
	deptListTEMP = getTemplate('deptListTEMP'),
	pageTEMP = getTemplate('pageTEMP');

	// 页面缓存,存放部门和服务器,及两者关系
	var Servers={},
	Depts={};
	var getServers=function(deptId){
		var arr=[],s;
		for(var sid in Servers){
			s=Servers[sid]
			if(s.deptId==deptId){
				arr.push(s);
			}
		}
		return arr;
	}

	$('.depts').each(function(){
		var $this=$(this),
		deptdata=$this.attr('deptdata').split(',');
		Depts[this.id]={
				deptId:this.id,
				cname:deptdata[0],
				deptNo:deptdata[1],
				des:deptdata[2]
		};
	});
	//服务器点击事件
	var $menuList = $('#menuList').on('click','a',function(){
			var self=this,serverId=this.id.replace('server',''),
			server=Servers[serverId];
			$content.html(tmpl(showServerTEMP,server));
			fetchServerStatus(server);
	})	
	// 左侧目录委托事件:点击部门展开与关闭
	.on('click','.depts',function() {
						var $this = $(this), deptId = this.id, deptObj = {}, img = $this
								.children('.glyphicon')[0], menu = $this.next(), deptdata = $
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
							 var servers=getServers(deptId);
							//var servers=cache.deptServer[deptId];
							if(!servers||servers.length==0){
								if(confirm("确定删除?")){
									sync.action('删除')('/dept/del.do',{deptId:deptId},function(){
										delete Depts[deptId];
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
									//TODO 维护关系用,要删
									//cache.deptServer[deptId]=servers;
									for(var i=servers.length;i--;){
										Servers[servers[i].id]=servers[i];
									}
									menu.html(tmpl(serverListTEMP,servers)).show();
								}
							});
							$(img).removeClass('glyphicon-plus').addClass('glyphicon-minus');
						} else {
							menu.hide();
							$(img).removeClass('glyphicon-minus').addClass('glyphicon-plus');
						}
					});
	
	// 添加服务器事件
	$('#addServer').click(function() {
		saveOrUpdateServer('添加服务器','添加','/server/add.do');
	});
	var saveOrUpdateServer=function(title,action,url,server){
		if(server){
			var dept=Depts[server.deptId];
			dept.selected='selected';
		}
		
		var data=$.extend({},server,{
			title : title,
			action : action,
			isEnhance:server && server.isEnhance?'checked':'',
			deptOption:tmpl('<option {selected} value="{deptId}">{cname}</option>',_toArray(Depts))
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
	
	var formatDisk=function(n){
	    n=(+n)|0;
	    var gb=n/1024;	   
	   return gb.toFixed(2);
	}
	var fetchServerStatus=function(server){
		sync.getXML('/server/status.do',{serverId:server.id,sid:Math.random()},function(result){
			var statusOperate;
			if(result && result.ret==0 && result.isValid==1){
				result.used_disk = Math.round(((result.total_disk-result.free_disk)/result.total_disk*100)*100/100);
				result.free_disk=formatDisk(result.free_disk);
				result.total_disk=formatDisk(result.total_disk);
				$content.find('#showResult').html(tmpl(serverStatusTEMP,result));
				statusOperate={status:'disable',statusVal:'禁用'}
			}else {
				$("#showResult").html("<h4>服务器未激活</h4>");
				statusOperate={status:'enable',statusVal:'激活'}
			}
			
			$content.find('#operate').html(tmpl(serverOperaTEMP,statusOperate))
			.off('click','#updateServerBT')
			.off('click','#delServerBT')
			.on('click','#updateServerBT',function(){
				saveOrUpdateServer('修改服务器','修改','/server/update.do',Servers[server.id]);
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
					delete Servers[serverId];
					//TODO 维护关系用,要删
					//cache.deptServer[server.deptId]=_toArray(Servers);
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
			if(!xml){
			    alert('无法查到状态数据!');
			}
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
		if(Servers[serverId]){
			$('#server'+serverId).html(server.ipAddr);
		}else{			
			var deptId=server.deptId;
			var img=$('#img'+deptId);
			//如果不是折叠状态则显示
			if(!isFolded(img[0])){
				$('#menu'+deptId).append(tmpl(serverListTEMP,server)).show();
			}
		}
		Servers[serverId]=server;
	}
	//添加,修改部门成功后的回调
	var renderDeptList = function(obj, action) {
		if (!obj)return;
		// deptData="${dept.cname },${dept.deptNo },${dept.dsc }"
		obj.deptData = '' + [ obj.cname, obj.deptNo, obj.dsc ];
		Depts[obj.deptId]=obj;
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
		return $(img).is('.glyphicon-plus');
	}
	
	//验证相关
	$.extend($.validator.messages,{
		maxlength:$.format('长度不能超过{0}')
	});
	$.validator.addMethod("ip", function(value, element, param) {
		return this.optional(element) || /^(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)\.(25[0-5]|2[0-4]\d|[01]?\d\d?)$/i.test(value);
	}, "ip格式有误");
	$.validator.addMethod("num", function(value, element, param) {
		return this.optional(element) || /^\d+$/i.test(value);
	}, "必须是数字");
	$.validator.addMethod("ipExist", function(value, element, param) {
		console.log(Servers)
		var isExist=false;
		for(var i in Servers){
			isExist=isExist||(Servers[i].ipAddr==value);
		}
		return !isExist;
	}, "此ip已存在");

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
					ip : true,
					ipExist:true
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
					maxlength : 6,
					num:true
				},
				deptDsc : {
					required : true,
					maxlength : 16
				}
			}
		});
	}

	//退出事件
	$('#logout').click(function(){
		location.href='/logout.do'
	});
	var $online_user=$('#online-user'),
	$online_count=$('#online_count'),
	$online_pager=$online_user.find('ul.pagination'),
	pager={
		page:1,
		totalPage:1,
		rows:15
	};

	$online_pager.on('click','li',function(){
		var clickPage=$.trim($(this).text());
		var curPage=pager.page;

		if(clickPage.indexOf('next')!=-1){
			var next=curPage+1;
			if(next>pager.totalPage){
				next=pager.totalPage;
			}
			pager.page=next;
		}else if(clickPage.indexOf('prev')!=-1){
			var prev=curPage-1;
			if(prev<1){
				prev=1;
			}
			pager.page=prev;
		}else{
			pager.page=clickPage|0;
		}
		console.log(pager)
		loadOnlineUser();
	});



	var loadOnlineUser=function(){
	    page=pager.page;
		$.post('/user/loginedUserList.do',pager,function(result){
			var d,user,arr=[];
			if(result && $.isArray(result.rows)){
				for(var i=0,len=result.rows.length;i<len;i++){
					user=result.rows[i];
					arr.push('<li>'+user.name+'</li>');
				}
				$online_user.find('ol.onlineuser').html(arr.join(""));
				$online_count.html(len);
				renderPager(result.total);
			}
		});
	}
	var renderPager=function(total){
		var page=pager.page;
		if(total==0){
		    pager.page=1;
			$online_pager.html('');
			return;
		}
		var arr=[],
		prev='<li><a href="#"><span aria-hidden="true">&laquo;</span><span class="sr-only">prev</span></a></li>',
		next='<li><a href="#"><span aria-hidden="true">&raquo;</span><span class="sr-only">next</span></a></li>';
		var totalPage=((total-1)/pager.rows+1)|0;
		if(page>totalPage)page=totalPage;

		pager.totalPage=totalPage;
		pager.page=page;

		for(var i=1;i<=totalPage;i++){
			if(page==i){
			arr.push(tmpl(pageTEMP,{page:page,class:'active'}))
			}else if(i>=page-3 && i<=page+3){
			arr.push(tmpl(pageTEMP,{page:i}))
			}
		}
		if(arr.length==7){
		  arr.pop();
		  arr.shift();
		}
		if(arr.length==6){
			arr.pop();
		}
		arr.unshift(prev);
		arr.push(next);
		$online_pager.html(arr.join(''));

	}
	loadOnlineUser();
	setInterval(loadOnlineUser,3000)

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
