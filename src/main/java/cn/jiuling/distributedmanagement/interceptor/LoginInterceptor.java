package cn.jiuling.distributedmanagement.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		logger.info("enter login interceptor,user is " + user+",uri is "+uri);
		if (null == user || "".equals(user)) {
			request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
			return false;
		}
		return true;
	}

}
