package com.stock.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stock.service.DoDownService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStockDownSevice {
	
	Logger logger = LoggerFactory.getLogger(TestStockDownSevice.class);
	
	@Autowired 
	DoDownService doDownService;
	
	@Test
	public void testDown() {
		//doDownService.setDate("20200808", "20200819");
		//doDownService.downAll();
//		doDownService.reDownFail(null);
	}
	
}
