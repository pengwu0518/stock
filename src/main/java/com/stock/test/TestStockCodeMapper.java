package com.stock.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stock.mapper.StockCodeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStockCodeMapper {
	
	@Autowired
	StockCodeMapper stockCodeMapper;
	
	@Test
	public void testGetAllStockCode() {
		List<String> list = stockCodeMapper.getAllStockCode();
		System.out.println("add count: " + list);
	}
	
	public static List<String> getAllCode(){
		List<String> list = new ArrayList<String>();
		File file = new File("E:\\zd_zsone\\T0002\\export");
		String[] codeNames = file.list();
		//System.out.println(codeNames.length);
		for(int i = 0;i < codeNames.length;i++) {
			String code = codeNames[i];
			code = code.substring(3,9);
			list.add(code);
		}
		return list;
	}
	
}	
