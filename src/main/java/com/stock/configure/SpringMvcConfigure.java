package com.stock.configure;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stock.interceptor.LoginInterceptor;

//import com.stock.interceptor.LoginInterceptor;

@SpringBootConfiguration
public class SpringMvcConfigure implements WebMvcConfigurer  {
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		System.out.println("......................");
//		LoginInterceptor loginINterceptor = new LoginInterceptor();
//		InterceptorRegistration loginRegistry = registry.addInterceptor(loginINterceptor);
//		loginRegistry.addPathPatterns("/**");
//		loginRegistry.excludePathPatterns("/");
//		loginRegistry.excludePathPatterns("/login");
//		loginRegistry.excludePathPatterns("/login.html");
	}
}
