package cn.jiuling.distributedmanagement.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final String LOGIN_PATH = "/WEB-INF/jsp/login.jsp";
	private final Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// login开头的就放行
		String uri=request.getRequestURI();
		if(uri.startsWith("/login")){
			return true;
		}
		Object user = WebUtils.getSessionAttribute(request, "user");
		logger.info("enter login interceptor,user is " + user+",uri is "+uri);
		if (StringUtils.isEmpty(user)) {
			request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
			return false;
		}
		return true;
	}

}
