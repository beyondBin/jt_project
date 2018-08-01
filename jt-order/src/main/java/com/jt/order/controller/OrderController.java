package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 订单入库操作
	 * @param orderJSON
	 * @return orderId
	 */
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON){
		try {
			Order order =objectMapper.readValue(orderJSON, Order.class);
			String orderId = orderService.saveOrder(order);
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "订单入库失败！");
	}
	
	/**
	 * 根据orderId查询全部订单消息,并封装成Order返回
	 * @param id
	 * @return Order
	 * http://order.jt.com/order/query/131531560414056
	 * order.jt.com/order/success.html?id=131531560414056
	 */
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable String orderId){
		Order order = orderService.findOrderById(orderId);
		return order;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
