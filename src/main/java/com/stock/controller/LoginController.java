package com.stock.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	
	@RequestMapping("/")
	public String loginPage() {
		return "/login";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam(required = true,name = "username")String username,@RequestParam(required = true,name="password")String password,HttpServletRequest req) {
		System.out.println("username: " + username + ",password: " + password);
		if(username.equals("admin") && password.equals("123456")) {
			req.getSession().setAttribute("loginUser", "admin");
			return "/html/index";
		}
		return "error";
	}
}
