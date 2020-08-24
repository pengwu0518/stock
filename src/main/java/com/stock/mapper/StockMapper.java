package com.stock.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.stock.pojo.StockMap;


@Mapper
public interface StockMapper {

	List<StockMap> getAllStock();
	
	int addBatch(List<StockMap> list);
	
	List<StockMap> selectByStartAndEnd(Map<String,String> map);
	
	List<String> selectAllDate();
	
	StockMap selectByCodeAndDate(Map<String,String> map);
	
	float selectSumByCodeAndDateSec(Map<String,String> map);
	
	List<StockMap> selectByCodeAndDateSec(Map<String,String> map);
	
	float select5DAvg(Map<String,String> map);
}
