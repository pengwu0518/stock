package com.stock.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.mapper.StockCodeMapper;
import com.stock.utils.Constants;

@Service
public class DoDownService {
	
Logger logger = LoggerFactory.getLogger(DoDownService.class);
	
	@Autowired 
	StockCodeMapper stockCodeMapper;
	@Autowired
	IStockDownService stockDownService;
	
	private String startDate;
	private String endDate;
	private String downFolder;
	
//	String startDate = "20100301";
//	String endDate = "20200807";
	
	public void setDate(String startDate,String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		if (this.downFolder == null) {
			this.downFolder = startDate + "-" + endDate;
		}
	}
	
	public void setDownFolder(String downFolder) {
		this.downFolder = downFolder;
	}
	
	public void downAll() {
		if(startDate == null || endDate == null) {
			logger.error("请设置下载时间段!");
			return;
		}
		List<String> codeList = stockCodeMapper.getAllStockCode();
		if (codeList == null || codeList.size() == 0) return;
		stockDownService.setDate(startDate,endDate);
		stockDownService.setDownFolder(downFolder);
		int codeSize = codeList.size();
		int failCount = 0;
		for(int i = 0;i < codeSize;i++) {
			//logger.info("代码："+ codeNames.get(i) + ",count: " + count++);
			boolean b = stockDownService.down(codeList.get(i), startDate, endDate);
			if(!b) failCount++;
			//logger.info("下载总数："+ list.size());
		}
		logger.info("下载成功: " + (codeSize - failCount));
		logger.info("下载失败: " + failCount);
		if (failCount != 0) {
			reDownFail(codeList);
		}
		else {
			stockDownService.close();
		}
	}

	/**
	 * 重新下载失败的股票数据,方法公开的目的是可以手动调用下载失败的
	 * @param codeList 所有股票代码列表
	 * @param downFolder 股票文件下载文件夹
	 */
	public void reDownFail(List<String> codeList){
		if(codeList == null) {
			codeList = stockCodeMapper.getAllStockCode();
		}
		if(startDate == null || endDate == null) {
			logger.error("请设置下载时间段!");
			return;
		}
		List<String> failCodeList = getDownFailCodeList(codeList,downFolder);
		stockDownService.setDate(startDate,endDate);
		stockDownService.setDownFolder(downFolder + "-fail");
		for(int i = 0;i < failCodeList.size();i++) {
			stockDownService.down(failCodeList.get(i));
		}
		stockDownService.close();
	}
	
	//通过全部代码列表和下载文件夹下文件名称获得下载失败的股票代码
	private List<String> getDownFailCodeList(List<String> codeList,String downFolder){
		List<String> downFailCodeList = new ArrayList<String>();
		File file = new File(Constants.downPath + downFolder);
		String[] fileNames = file.list();
		List<String> fileNameList = Arrays.asList(fileNames);
		StringBuffer sb = new StringBuffer();
		for(String codeName : codeList) {
			sb.append(codeName).append(".csv");
			if (!fileNameList.contains(sb.toString())) {
				downFailCodeList.add(codeName);
			}
			sb.delete(0, sb.length());
		}
		return downFailCodeList;
	}
}
