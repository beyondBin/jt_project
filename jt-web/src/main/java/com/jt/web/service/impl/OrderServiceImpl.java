package com.jt.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Order;
import com.jt.web.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 订单入库操作
	 * @return orderId 订单ID
	 */
	@Override
	public String saveOrder(Order order) {
		//1.url
		String url = "http://order.jt.com/order/create";
		//2.将参数转成JSON串后封装到Map中
		String orderJSON = null;
		String orderId = null;
		try {
			orderJSON = objectMapper.writeValueAsString(order);
			Map<String,String> params = new HashMap<>();
			params.put("orderJSON", orderJSON);
			//3.发起请求
			String jsonData = httpClient.doPost(url, params);
			//判断返回的数据是否为空
			if(StringUtils.isEmpty(jsonData)){
				throw new RuntimeException();
			}
			//将数据转成SysResult
			SysResult sysResult = objectMapper.readValue(jsonData, SysResult.class);
			//如果成功,返回orderId
			if(sysResult.getStatus() == 200){
				orderId = (String) sysResult.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return orderId;
	}
	
	/**
	 *  根据orderId查询后台数据库：订单的全部消息  
	 *  @return Order
	 *  
	 */

	@Override
	public Order findOrderById(String id) {
		//1.url
		String url = "http://order.jt.com/order/query/"+id;
		//2.发起请求
		String jsonData = httpClient.doGet(url);
		if(StringUtils.isEmpty(jsonData)){
			System.out.println("jsonData数据为空");
		}
		//3.将返回的数据转成Order
		Order order = null;
		try {
			order = objectMapper.readValue(jsonData, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return order;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
