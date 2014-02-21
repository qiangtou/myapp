package cn.jiuling.distributedmanagement.web;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jiuling.distributedmanagement.model.User;

@Controller
public class LoginController {
	private Logger logger=Logger.getLogger(LoginController.class);
	
	@RequestMapping(value="index.do")
	public String index(){
		logger.info("进入登录页面");
		return "login";
	}
	
	@RequestMapping(value="login.do")
	public String login(@ModelAttribute("user") User user,HttpSession session){
		String forward;
		logger.info(user);
		if(valideUser(user,session)){
			forward="redirect:/";
		}else{
			forward="login";
		}
		return forward;
	}

	private boolean valideUser(User user, HttpSession session) {
		//TODO 验证用户,这里写死了
		if (null != user && "admin".equals(user.getLoginName())) {
			session.setAttribute("user", user.getLoginName());
			return true;
		}
		return false;
	}

}
