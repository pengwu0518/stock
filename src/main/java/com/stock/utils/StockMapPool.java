package com.stock.utils;

import java.util.ArrayList;
import java.util.List;

import com.stock.pojo.StockMap;

public class StockMapPool {
	
	List<StockMap> pool = new ArrayList<StockMap>();
	
	public StockMap getStockMap() {
		if (pool.size() != 0) {
			StockMap stockMap = pool.get(0);
			pool.remove(0);
			return stockMap;
		}
		return new StockMap();
	}
	
	public void recover(List<StockMap> list) {
		pool.addAll(list);
	}
}
