package com.stock.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.mapper.StockCodeMapper;
import com.stock.mapper.StockMapper;
import com.stock.pojo.StockMap;
import com.stock.utils.Constants;
import com.stock.utils.Convert;
import com.stock.utils.StockMapPoolFactorry;

@Service
public class StockService implements IStockService{
	
	Logger logger = LoggerFactory.getLogger(StockService.class);
	
	@Autowired 
	IStockDownService stockDownService;
	@Autowired
	StockMapper stockMapper;
	@Autowired
	StockCodeMapper stockCodeMapper;
	
	@Override
	public int addBatch(String startDate, String endDate) {
		// TODO Auto-generated method stub
		List<StockMap> stockList = new ArrayList<StockMap>();
		List<String> codeList = stockCodeMapper.getAllStockCode();
		String foldeName = startDate + "-" + endDate;
		int codeSize = codeList.size();
		int total = 0;
		int max = 1000 * 100 * 1;
		for(int i = 0;i < codeSize;i++) {
			String codeName = codeList.get(i);
			logger.info("代码名: " + codeName);
			stockList.addAll(readFile(codeName,foldeName));
			if ((i == codeSize - 1) || (stockList.size() >= max)) {
				//total += stockList.size();
				logger.info("可添加条数: " + stockList.size() + ",start insert...");
				total += stockMapper.addBatch(stockList);
				StockMapPoolFactorry.getStockMapPool().recover(stockList);
				stockList.clear();
				logger.info("结束添加: 清空集合");
			}
		}
		return total;
	}
	
	private List<StockMap> readFile(String codeName,String folderName) {
		String filePath = Constants.downPath + folderName + "/" + codeName + ".csv";
//		logger.info("开始读取文件: " + filePath);
		File file = new File(filePath);
		List<StockMap> list = new ArrayList<StockMap>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
//				System.out.println(line);
				list.add(Convert.convertByExcel(Constants.stockFields, line));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
