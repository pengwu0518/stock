package com.stock.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("enter loginInterceptor..." + request.getRequestURL());
		
		Object loginUser = request.getSession().getAttribute("loginUser");
		if(loginUser == null) {
			response.sendRedirect("/stock/");
			return false;
		}
		return true;
	}
}
