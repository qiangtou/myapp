package com.mycompany.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri=request.getRequestURI();
		if(uri.startsWith("/login")){
			return true;
		}
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		logger.info("enter login interceptor,user is " + user+",uri is "+uri);
		if (null == user || "".equals(user)) {
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return false;
		}
		return true;
	}

}
