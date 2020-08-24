package com.stock.utils;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stock.pojo.StockMap;

public class Convert {
	
	static Logger logger = LoggerFactory.getLogger(Convert.class);
	
	public static StockMap convertByExcel(String[] names,String line){
		
		if (names == null || names.length == 0) {
			logger.error("列名不能为空!");
			return null;
		}
		if (Strings.isEmpty(line)) {
			logger.error("内容不能为空!");
			return null;
		}
		String[] values = line.replaceAll("'", "").split(",");
		if (names.length != values.length) {
			logger.error("列的长度不匹配!");
			return null;
		}
//		StockMap stock = new StockMap();
		
		StockMap stock = StockMapPoolFactorry.getStockMapPool().getStockMap();
		for(int i = 0;i < names.length;i++) {
			//System.out.print(values[i]);
			stock.put(names[i], values[i]);
		}
		return stock;
	}
	
	public static void main(String[] args) {
		String content = "2018-01-15,'600017,日照港,3.89,3.96,3.88,3.95,3.95,-0.06,-1.519,0.3472,10677989,41876424.0,11964293624.3,11964293624.3";
		List<StockMap> list = new ArrayList<StockMap>();
		int count = 0;
		for(int i= 0;i < 20;i++) {
			for(int j = 0;j < 1000 * 100 * 5;j++) {
				logger.info("count： " + count++);
				list.add(convertByExcel(Constants.stockFields,content));
			}
			//StockMapPoolFactorry.getStockMapPool().recover(list);
			list.clear();
		}
	}
}
