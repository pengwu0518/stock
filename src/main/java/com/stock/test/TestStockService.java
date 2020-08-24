package com.stock.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stock.service.IStockService;
import com.stock.service.StockSelectService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStockService {

	@Autowired
	IStockService stockService;
	@Autowired
	StockSelectService stockSelectService;
	
	// 2010301 20160229
	// 20160301 20200301
	//数据库最大传输限制为200M
	@Test
	public void testAddStock() {
//		stockService.addBatch("20200808", "20200819");
		stockSelectService.selectByStartAndEnd("20200701", "20200801");
	}
}
