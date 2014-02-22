$(function(){
	var form=$('form').submit(function(event){
		if(!validate($(this))){
			event.preventDefault();
		}
	})
	var validate=function(form){
		var name=form.find('#username'),
		pw=form.find('#password');
		if(name.val()==''){
			alert('用户名不能不空')
			return false;
		}else if(pw.val()==''){
			alert('密码不能不空')
			return false;
		}
		pw.val(MD5_hexhash(pw.val()));
		return true
	}
})
