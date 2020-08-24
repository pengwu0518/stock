package com.stock.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class TestTomcatThread {

	
	public static void main(String[] args) {
		for(int i= 0;i < 400;i++) {
			new TomcatThread().start();
		}
	}
	public static class TomcatThread extends Thread{
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet("http://localhost:8080/stock/hello");
		public void run() {
			// TODO Auto-generated method stub
			CloseableHttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpGet);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				System.out.println("connect status: " + statusCode);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if (httpResponse != null) {
					try {
						httpResponse.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
