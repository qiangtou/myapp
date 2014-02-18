$(function() {
	// 加载服务器
	var $content = $('#content'), deptTEMP = $('#deptTEMP').html().replace(
			/[\r\n]/g, '').replace(/>\s+</g, '><').trim();
	serverTEMP = $('#serverTEMP').html().replace(/[\r\n]/g, '').replace(
			/>\s+</g, '><').trim();

	$('#menuList')
			.on(
					'click',
					'.depts',
					function() {
						var $this = $(this), deptId = this.id, deptObj = {}, img = $this
								.children('img')[0], menu = $this.next(), deptdata = $
								.attr(this, 'deptdata').split(',');

						deptObj.id = deptId;
						deptObj.cname = deptdata[0];
						deptObj.deptNo = deptdata[1];
						deptObj.dsc = deptdata[2];
						deptObj.title = deptObj.cname;
						deptObj.action = '修改';

						$content.html(replace(deptTEMP, deptObj));
						if (isFolded(img)) {
							$.post('/server/list.do', {
								deptId : deptId
							}, function(servers) {
								if (servers && servers.length > 0) {
									menu.html(generateServers(servers)).show();
								}
							});
							img.src = '/images/subheader_fold.png';
						} else {
							menu.hide();
							img.src = '/images/subheader_expand.png';
						}

					});

	$('#addServer').click(function() {
		$content.html(replace(serverTEMP, {
			title : '添加服务器',
			action : '添加'
		})).off('click', '.action').on('click', '.action', function() {
			console.log('添加服务器');
			var ipAddr = $content.find('#ipAddr').val();
			var deptId = $content.find('#deptId').val();
			var data = {
				ipAddr : ipAddr,
				deptId : deptId
			};
			$.ajax( {
				url : '/server/add.do',
				contentType : 'application/json',
				type : 'post',
				data : JSON.stringify(data),
				dataType : "json",
				error : function() {
					alert('添加失败');
				},
				success : function(result) {
					if (result.success) {
						alert('添加成功');
					}else{
						alert('添加失败');
					}
				}
			});
		});
		$content.find('.del').remove();
	});
	$('#addDept').click(function() {
		$content.html(replace(deptTEMP, {
			title : '添加部门',
			action : '添加'
		})).off('click', '.action').on('click', '.action', function() {
			console.log('添加部门');
		});
		$content.find('.del').remove();

	});
	var isFolded = function(img) {
		return img.src.indexOf('subheader_expand') > -1;
	}
	var replace = function(template, data) {
		return template.replace(/{\w+}/g, function(key) {
			key = key.slice(1, -1);
			return data[key] ? data[key] : '';
		});
	}
	var generateServers = function(servers) {
		var server, html, arr = [];
		for ( var i = 0, len = servers.length; i < len; i++) {
			server = servers[i];
			html = '<li id="4">'
					+ '<a href="javascript:void(0)" id="192.168.1.52" ">'
					+ server.ipAddr + '</a>' + '</li>';
			arr.push(html);
		}
		return arr.join('');
	}
});
