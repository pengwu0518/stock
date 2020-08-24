package com.stock.utils;


public class StockMapPoolFactorry {
	
	private static StockMapPool stockMapPool;
	
	
	public static StockMapPool getStockMapPool() {
		if (stockMapPool == null) {
			stockMapPool = new StockMapPool();
		}
		return stockMapPool;
	}
}

