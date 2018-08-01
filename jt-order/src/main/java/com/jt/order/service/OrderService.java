package com.jt.order.service;

import com.jt.order.pojo.Order;

public interface OrderService {
	/** 订单入库操作 */
	String saveOrder(Order order);
	
	/** 根据orderId查询全部订单消息,并封装成Order返回 */
	Order findOrderById(String orderId);

}
