package cn.jiuling.distributedmanagement.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.distributedmanagement.model.User;
import cn.jiuling.distributedmanagement.service.UserService;

@Controller
public class LoginController {
	private Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private UserService userService;

	@RequestMapping(value = "index.do")
	public String index() {
		logger.info("进入登录页面");
		return "login";
	}

	@RequestMapping(value = "login.do")
	public String login(@ModelAttribute("user") User user, HttpSession session) {
		String forward;
		logger.info(user);
		if (valideUser(user)) {
			forward = "redirect:/";
			session.setAttribute("user", user.getName());
		} else {
			forward = "login";
		}
		return forward;
	}

	private boolean valideUser(User user) {
		logger.info("login user is " + user);
		return userService.valideUser(user);
	}

	@RequestMapping(value = "/login/valid.do")
	@ResponseBody
	public boolean valid(String name, String md5pwd) {
		try {
			User user = new User();
			user.setName(name);
			user.setMd5pwd(md5pwd);
			return valideUser(user);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}
