package com.jt.web.service;

import com.jt.web.pojo.Order;

public interface OrderService {
	
	/** 订单入库操作 */
	String saveOrder(Order order);

	/** 根据orderId查询后台数据库：订单的全部消息   */
	Order findOrderById(String id);
	
}
