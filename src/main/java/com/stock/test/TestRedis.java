package com.stock.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.stock.pojo.StockMap;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

	@Autowired
	RedisTemplate<String,Object> srt;
	
	@Test
	public void test() {
		StockMap stockMap = new StockMap();
		stockMap.put("date", "2010-04-22");
		stockMap.put("code", "300148");
		srt.opsForValue().set("test", stockMap);
	}
	
	public static void main(String[] args) {
		
	}
}
