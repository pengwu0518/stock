package com.stock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockCodeMapper {

	
	List<String> getAllStockCode();
	
	int addBatch(List<String> list);
}
