package com.jt.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService{
	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 1.通过HttpClient方式请求服务端数据
	 * 
	 */
	@Override
	public Item findItemById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemById";
		Map<String,String> params = new HashMap<>();
		params.put("itemId", itemId+"");
		//发送请求,获取服务端数据
		String jsonResult = httpClient.doGet(url,params);
		Item item = null;
		try {
			item = objectMapper.readValue(jsonResult, Item.class);
		} catch (Exception e) {
		e.printStackTrace();
		}
		return item;
	}
	
	/**
	 * 获取ItemDesc商品详细信息
	 */
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById";
		Map<String,String> params = new HashMap<>();
		params.put("itemId", itemId+"");
		//发送请求,获取服务端数据
		String jsonResult = httpClient.doGet(url,params);
		ItemDesc itemDesc = null;
		try {
			itemDesc = objectMapper.readValue(jsonResult, ItemDesc.class);
		} catch (Exception e) {
		e.printStackTrace();
		}
		return itemDesc;
		
	}

	
	
	
	
	
	
}
