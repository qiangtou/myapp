package com.mycompany.app.web;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.app.Vo.Result;
import com.mycompany.app.model.User;


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
		String userName = "admin";
		if (null != user && userName.equals(user.getLoginName())) {
			session.setAttribute("user", user.getLoginName());
			return true;
		}
		return false;
	}

}
