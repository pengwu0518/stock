package com.stock.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.stock.utils.Constants;

@Service
public class StockDownService implements IStockDownService{
	
	static Logger logger = LoggerFactory.getLogger(StockDownService.class);

	CloseableHttpClient httpClient;
	HttpGet httpGet;
	RequestConfig requestConfig;
	CloseableHttpResponse httpResponse;
	BufferedReader bufferReader;
	InputStreamReader isr;
	FileWriter fw;
	String downFolder = "stock/";
	
	String startDate;
	String endDate;
	
	public StockDownService() {
		httpClient = HttpClientBuilder.create().build();
		requestConfig = RequestConfig.custom()  
        .setConnectTimeout(5000)//一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间  
        .setSocketTimeout(5000)// 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间  
        .setConnectionRequestTimeout(5000)  
        .build();
	}
	
	@Override
	public boolean down(String code, String startDate, String endDate) {
		// TODO Auto-generated method stub
		if (Strings.isEmpty(code)) {
			logger.info("code is null!");
		}
		String newCode;
		if (code.startsWith("6")) {
			newCode = "0" + code;
		}else {
			newCode = "1" + code;
		}
		String newUrl = String.format(Constants.wyUrl, newCode,startDate,endDate);
		logger.info("url: " + newUrl);
		httpGet = new HttpGet(newUrl);
		httpGet.setConfig(requestConfig);
		try {
			HttpEntity entity = null;
			httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.info("响应状态为:" + statusCode);
			if (statusCode == 200) {
				entity = httpResponse.getEntity();
			}
			else {
				return false;
			}
			if (entity != null) {
				isr = new InputStreamReader(entity.getContent(),"GBK");
				bufferReader = new BufferedReader(isr);
	
				fw = new FileWriter(Constants.downPath + downFolder + code + ".csv");
				String line = null;
				while ((line = bufferReader.readLine()) != null) {
					fw.write(line + "\n");
					fw.flush();
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (fw != null) {
					fw.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (bufferReader != null) {
						bufferReader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public void down(String code) {
		if (startDate == null || endDate == null) {
			logger.debug("开始时间和结束时间不能为空!");
			return;
		}
		down(code,startDate,endDate);
	}
	
	public void setDate(String startDate,String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public void close() {
		try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDownFolder(String folder) {
		this.downFolder = folder + "/";
		File file = new File(Constants.downPath + downFolder);
		System.out.println(file.getAbsolutePath());
		if (!file.exists() || !file.isDirectory()) {
		file.mkdir();
		}
	}
	
}
