package com.stock.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stock.mapper.StockMapper;
import com.stock.pojo.StockMap;
import com.stock.utils.Constants;
import com.stock.utils.Convert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStockMapper {

	@Autowired
	StockMapper stockMapper;
	
	
	@Test
	public void testGetAllStock() {
		//System.out.println(stockMapper.getAllStock());
//		System.out.println("date: " + stockMapper.selectAllDate());
		
		Map<String,String> map = new HashMap<String,String>();
//		map.put("startDate", "20200701");
//		map.put("endDate", "20200801");
//		System.out.println("dataList : " + stockMapper.selectByStartAndEnd(map).size());
		map.put("code", "300148");
		map.put("startDate", "20200707");
		map.put("endDate", "20200720");
		System.out.println("data : " + stockMapper.selectByCodeAndDateSec(map));
	}
	
	//@Test
	public void addBatch() {
		String[] values = {
				"2020-08-07,'300148,天舟文化,5.45,5.57,5.13,5.37,5.44,0.01,0.1838,6.2317,51924336,277858695.14,4601844000.7,4541103750.7",
				"2020-08-06,'300148,天舟文化,5.44,5.75,5.3,5.67,5.67,-0.23,-4.0564,4.9429,41185909,225402624.34,4593400250.24,4532771450.24"
		};
		List<StockMap> list = new ArrayList<StockMap>();
		list.add(Convert.convertByExcel(Constants.stockFields, values[0]));
		list.add(Convert.convertByExcel(Constants.stockFields, values[1]));
		int i = stockMapper.addBatch(list);
		System.out.println("add count: " + i);
	}
}
