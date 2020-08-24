package com.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	Integer count = 1;
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
//		synchronized (count) {
			System.out.println("hello." + (count++));
//		}
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "hello sprintboot";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "/html/index";
	}
}
