package com.stock.utils;

public interface Constants {
	
	public final static String wyUrl =  "http://quotes.money.163.com/service/chddata.html?code=%s&start=%s&end=%s&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
	public final static String[] stockFields = {"DATE","CODE","NAME","TCLOSE","HIGH","LOW","TOPEN","LCLOSE","CHG","PCHG","TURNOVER","VOTURNOVER","VATURNOVER","TCAP","MCAP"};
	
	public final static String downPath = "E:/股票数据/";
	public final static String downFailFile = "downFail.txt";
	
	public final static int allowPacket= 4194304;
}
