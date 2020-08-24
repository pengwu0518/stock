package com.stock.service;


public interface IStockDownService {

	void setDate(String startDate,String endDate);
	
	boolean down(String code,String startDate,String endDate);
	
	void down(String code);
	
	void close();
	
	void setDownFolder(String folder);
}
