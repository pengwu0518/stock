package com.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.stock.mapper.StockMapper;
import com.stock.pojo.KDJ;
import com.stock.pojo.StockMap;

@Service
public class StockSelectService {

	@Autowired
	RedisTemplate<String,Object> redisTemplate;
	
	@Autowired
	StockMapper stockMapper;

	List<StockMap> stockList;
	List<String> dateList;
	
	public final static String dateListRedisKey = "dateListKey";
	public final static String avg5DKey = "avg5DKey";
	
	StockMap stock;
	StockMap prevStock;
	StockMap pprevStock;
	StockMap ppprevStock;
	StockMap nextStock;
	StockMap nnextStock;
	StockMap nnnextStock;
	
	String code;
	Object date;
	
	int position;
	float turnOver;
	float lclosePrice;
	float topenPrice;
	float tclosePrice;
	float avg5D;
	
	float prevPChg;
	float prevTurnOver;
	float prevVaturnover;
	
	float pprevPChg;
	float pprevTurnOver;
	float pprevVaturnover;
	
	float ppprevPChg;
	
	float nextPChg;
	float nnextPChg;
	float nnnextPChg;
	float nLClosePrice;
	float nTClosePrice;
	float nTOpenPrice;
	float nHighPrice;
	
	float nextTurnOver;
	
	int count = 0;
	int jCount1 = 0;
	int jCount2 = 0;
	
	String context = "%s-%s[%s]";
	
	@SuppressWarnings({ "unchecked"})
	public void selectByStartAndEnd(String startDate,String endDate) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		String key = startDate + "-" + endDate;
		stockList  = (List<StockMap>)redisTemplate.opsForValue().get(key);
		if (stockList == null) {
			stockList = stockMapper.selectByStartAndEnd(map);
			redisTemplate.opsForValue().set(key, stockList);
		}
		dateList = (List<String>)redisTemplate.opsForValue().get(dateListRedisKey);
		if (dateList == null) {
			dateList = stockMapper.selectAllDate();
			redisTemplate.opsForValue().set(dateListRedisKey, dateList);
		}
		List<StockMap> filterList = new ArrayList<StockMap>();

		System.out.println("size: " + stockList.size());
		
		for(int i = 0;i < stockList.size();i++) {
			stock = stockList.get(i);
			code = stock.get("code").toString();
			date = stock.get("date");
//			System.out.println(date);
			position = dateList.indexOf(date.toString());
			
			String prevDate = dateList.get(position - 1);
			String pprevDate = dateList.get(position - 2);
			String ppprevDate = dateList.get(position - 3);
			
			String nextDate = dateList.get(position + 1);
			String nnextDate = dateList.get(position + 2);
			String nnnextDate = dateList.get(position + 3);

			prevStock = getStock(code,prevDate);
			if (prevStock == null) continue;
			pprevStock = getStock(code,pprevDate);
			if (pprevStock == null) continue;
			ppprevStock = getStock(code,ppprevDate);
			if (ppprevStock == null) continue;
			nextStock = getStock(code,nextDate);
			nnextStock = getStock(code,nnextDate);
			nnnextStock = getStock(code,nnnextDate);
			
			turnOver = getFloat(stock.get("TURNOVER"));
			lclosePrice = getFloat(stock.get("LCLOSE"));
			topenPrice = getFloat(stock.get("TOPEN"));
			tclosePrice = getFloat(stock.get("TCLOSE"));
			
			
			prevPChg = getFloat(prevStock.get("PCHG"));
			prevTurnOver = getFloat(prevStock.get("TURNOVER"));
			prevVaturnover = getFloat(prevStock.get("VATURNOVER"));
			
			pprevPChg = getFloat(pprevStock.get("PCHG"));
			pprevTurnOver = getFloat(pprevStock.get("TURNOVER"));
			pprevVaturnover = getFloat(pprevStock.get("VATURNOVER"));
			ppprevPChg = getFloat(ppprevStock.get("PCHG"));
			
			nextPChg = getFloat(nextStock.get("PCHG"));
			nextTurnOver = getFloat(nextStock.get("TURNOVER"));
			nnextPChg = getFloat(nnextStock.get("PCHG"));
			nnnextPChg = getFloat(nnnextStock.get("PCHG"));
			nLClosePrice = getFloat(nextStock.get("LCLOSE"));
			nTClosePrice = getFloat(nextStock.get("TCLOSE"));
			nTOpenPrice = getFloat(nextStock.get("TOPEN"));
			nHighPrice = getFloat(nextStock.get("HIGH"));
			
			if (!check1()) {
				continue;
			}
			
//			String avg5DStartDate = dateList.get(position - 4);
//			String avg5DEndDate = date.toString();
//			String avg5DNewKey =  avg5DKey + avg5DStartDate + "-" + avg5DEndDate;
//			String avg5DStr = (String)redisTemplate.opsForValue().get(avg5DNewKey);
//			if (avg5DStr == null) {
//				map.put("code", code);
//				map.put("startDate", avg5DStartDate);
//				map.put("endDate", avg5DEndDate);
//				avg5D = stockMapper.select5DAvg(map);
//				redisTemplate.opsForValue().set(avg5DNewKey, avg5D + "");
//			}else {
//				avg5D = getFloat(avg5DStr);
//			}
			
			
			
			//条件：下一日下跌
//			if ((nextPChg < -3) && (nTOpenPrice >  nTClosePrice)) {
//			}
//			else {
//				continue;
//			}
//			if (nHighPrice > nLClosePrice) {
//				continue;
//			}
//			if (nTClosePrice > nTOpenPrice) {
//				continue;
//			}
//			
			KDJ kdj = getKDJ(stock);
			if (kdj == null) continue;
			KDJ nextKdj = getKDJ(nextStock);
			if (nextKdj == null) continue;
			float jk1 = kdj.j - kdj.k;
			float jk2 = nextKdj.j - kdj.k;
			
			if (kdj.d > 70) {
				continue;
			}
			
			count++;
			
			if (nnextPChg > 9 || nnnextPChg > 9) {
				jCount1++;
			}else {
				jCount2++;
//				continue;
			}
			
			System.out.print(String.format(context, count,code,String.format("%tF",date)));
			System.out.print("kdj: " + f2(kdj.j) + f2(kdj.k) + f2(jk1) + ",nextKdj: " + f2(nextKdj.j) + "," + f2(nextKdj.k) + f2(jk2));
			System.out.println();
			//System.out.print("pchg: " + nextPChg + "," + nnextPChg + "," + nnnextPChg);
			filterList.add(stock);
		}
		System.out.println(String.format("[count: %s,jCount1: %s,jCount2: %s]", count,jCount1,jCount2));
	}

	
	public boolean check1() {
		if (code.startsWith("688")) {
			return false;
		}
		//当日换手率限制
		if (turnOver > 2 * prevTurnOver) {
			return false;
		}
		//前一日换手率限制
		if (prevTurnOver < 5) {
			return false;
		}
		if (prevTurnOver > pprevTurnOver * 2) {
			return false;
		}
		//前一日成交额限制
		if (prevVaturnover < 2 * 10000 * 10000) {
			return false;
		}
		if (prevVaturnover > 20 * 10000 * 10000) {
			return false;
		}
		//下一日换手率限制
//		if (nextTurnOver > turnOver * 2) {
//			return false;
//		}
//		if (nextTurnOver > turnOver) {
//			return false;
//		}

		//float avg5DPer = (tclosePrice - avg5D) * 100/ avg5D;
		//System.out.print("avg5D: " + f2(avg5D) + f2(tclosePrice) + f2(avg5DPer));
		return true;
	}
	
	public StockMap getStock(String code,String date) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("code", code);
		map.put("date", date);
		String key = "stock-" + code + "-" + date;
		StockMap stock = (StockMap) redisTemplate.opsForValue().get(key);
		if (stock == null) {
			stock = stockMapper.selectByCodeAndDate(map);
		}
		return stock;
	}

	
	@SuppressWarnings("unchecked")
	public List<StockMap> getList(String code,int startPos,int endPos){
		Map<String,String> map = new HashMap<String,String>();
		String startDate = dateList.get(startPos);
		String endDate = dateList.get(endPos);
		map.put("code",code);
		map.put("startDate",startDate);
		map.put("endDate", endDate);
		String key = code + "-" +  startDate + "-" + endDate;
		List<StockMap> list = (List<StockMap>) redisTemplate.opsForValue().get(key);
		if (list == null) {
			list = stockMapper.selectByCodeAndDateSec(map);
			redisTemplate.opsForValue().set(key, list);
		}
		return list;
	}

	public float getFloat(Object obj) {
		String str = (String) obj;
		if (str == null || str.equals("None")) 
			return 0;
		return Float.parseFloat(str);
	}
	
	public float get9DMaxPrice(List<StockMap> list) {
		float maxPrice = 0;
			for(StockMap stock : list) {
				float temp = getFloat(stock.get("HIGH"));
				if (temp >= maxPrice) {
					maxPrice = temp;
				}
			}
		return maxPrice;
	}
	public float get9DMinPrice(List<StockMap> list) {
		float minPrice = 100;
			for(StockMap stock : list) {
				float temp = getFloat(stock.get("LOW"));
				if (temp <= minPrice) {
					minPrice = temp;
				}
			}
		return minPrice;
	}
	
	public KDJ getMaxKDJ(List<KDJ> kdjList) {
		float max = -999;
		KDJ maxKDJ = null;
		for(KDJ kdj : kdjList) {
			if (kdj.j > max) {
				max = kdj.j;
				maxKDJ = kdj;
			}
		}
		return maxKDJ;
	}
	public KDJ getKDJ(StockMap stock) {
		String code = stock.get("code").toString();
		String date = stock.get("date").toString();
		String key = "kdj-" + code + "-" + date;
		KDJ redisKdj = (KDJ) redisTemplate.opsForValue().get(key);
		if (redisKdj != null) {
			return redisKdj;
		}
		List<KDJ> kdjList = getKDJList(stock);
		for(KDJ kdj : kdjList) {
			if (date.equals(kdj.date)) {
				redisTemplate.opsForValue().set(key, kdj);
				return kdj;
			}
		}
		return null;
	}
	public List<KDJ> getKDJList(StockMap stock) {
		String code = stock.get("code").toString();
		String date = stock.get("date").toString();
		int position = dateList.indexOf(date);
	
		int p = 5;
		int startPos = position - (2 * p - 1);
		int endPos = position;
		List<StockMap> kdjStockList = getList(code,startPos,endPos);
		//System.out.println("kdjStockList: " + kdjStockList.size());
		
		List<KDJ> kdjList = new ArrayList<KDJ>();
		if(kdjStockList.size() != 10) {
			return kdjList;
		}
		float k = 50,d = 50;
		StringBuffer kdjSB = new StringBuffer();
		for(int i = 1;i <= p;i++) {
			List<StockMap> tmpStockList = kdjStockList.subList(i, i + p);
//			System.out.println("size: " + tmpStockList.size());
			float maxPrice = get9DMaxPrice(tmpStockList);
			float minPrice = get9DMinPrice(tmpStockList);
			StockMap tempStock = tmpStockList.get(p - 1);
			float tclosePrice = getFloat(tempStock.get("TCLOSE"));
			String tempCode = tempStock.get("code").toString();
			String tempDate = tempStock.get("date").toString();
			KDJ kdj = calcKDJ(maxPrice,minPrice,tclosePrice,k,d);
			kdj.code = tempCode;
			kdj.date = tempDate;
			kdjList.add(kdj);
			k = kdj.k;
			d = kdj.d;
		}
		return kdjList;
	}
	
	public static KDJ calcKDJ(float maxPrice,float minPrice,float tclosePrice,float prevK,float prevD) {
		float rsv = (tclosePrice - minPrice) / (maxPrice - minPrice) * 100;
		float k = (2*prevK/3)+(rsv / 3);
		float d = (2*prevD/3)+(k / 3);
		float j = 3*k-2*d;
		KDJ kdj = new KDJ(k,d,j);
		return kdj;
	}
	
	public String f2(float f) {
		return String.format("%.2f",f) + " ";
	}
	public static void main(String[] args) {
		float max = 33.29f,min = 27.19f,close = 32.62f;
		System.out.println(calcKDJ(max,min,close,100f,100f));
	}
}
