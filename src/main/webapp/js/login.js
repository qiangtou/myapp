$(function() {
	var $passWord = $('#passWord'), 
	    $userName = $('#userName');

	// 自定义一个非法字符方法
	$.validator.addMethod('illegal', function(v, e) {
		return !this.optional(e) || (/^[0-9a-zA-Z_]{5,16}$/).test(v);
	}, $.format('{0}不能有非法字符'));

	var vForm = $('form').validate( {
		onkeyup : false,
		messages : {
			name : {
				required : '账号不能为空',
				rangelength : $.format('账号长度须大于{0},小于{1}')
			},
			md5pwd : {
				required : '密码不能为空',
				rangelength : $.format('密码长度须大于{0},小于{1}'),
				remote : '账号密码不匹配!!'
			}

		},
		rules : {
			name : {
				required : true,
				rangelength : [ 5, 16 ],
				illegal : [ '用户名' ]
			},
			md5pwd : {
				required : true,
				rangelength : [ 5, 16 ],
				illegal : [ '密码' ],
				remote : {
					url : '/login/valid.do',
					type : 'post',
					data : {
				        md5pwd : function() {
							return MD5_hexhash($passWord.val());
						},
						name : function() {
							return $userName.val()
						}
					}
				}
			}
		},
		submitHandler : function(form) {
			// 提交前密码做md5处理
		$passWord.val(MD5_hexhash($passWord.val()));
		form.submit();
	}
	});
})
