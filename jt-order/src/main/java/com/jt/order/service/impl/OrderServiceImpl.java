package com.jt.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;
import com.jt.order.pojo.OrderShipping;
import com.jt.order.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;

	
	/** 
	 * 订单入库操作:
	 * 需要关联入库,同时操作3张表格
	 * orderId:登录用户id+当前时间戳
	 */
	@Override
	public String saveOrder(Order order) {
		//拼orderId
		String orderId = order.getUserId() +""+System.currentTimeMillis();
		Date date = new Date();
		//1.实现订单表的入库操作
		order.setOrderId(orderId);//保存主键
		order.setCreated(date);
		order.setUpdated(date);
		order.setStatus(1);//代表未支付
		orderMapper.insert(order);
		System.out.println("订单表入库成功！！！");
		
		//2.实现订单物流信息入库
		OrderShipping shipping = order.getOrderShipping();//获取订单物流对象
		shipping.setOrderId(orderId);
		shipping.setCreated(date);
		shipping.setUpdated(date);
		orderShippingMapper.insert(shipping);
		System.out.println("订单物流信息表入库成功！！！");
		
		//3.实现订单商品入库
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品表入库成功！！！");
		return orderId;
	}

	/**
	 * 根据orderId查询全部订单消息,并封装成Order返回
	 * 1.查询tb_order表格
	 * 2.查询tb_order_item
	 * 3.查询tb_order_shipping
	 * 封装成Order对象并返回
	 */
	@Override
	public Order findOrderById(String orderId) {
		/*//1.查询Order表格
		Order order = orderMapper.selectByPrimaryKey(orderId);
		//2.查询OrderItem表格
		List<OrderItem> orderList = new ArrayList<>();
		OrderItem orderItem = orderItemMapper.selectByPrimaryKey(orderId);
		orderList.add(orderItem);
		//3.查询OrderShipping表格
		OrderShipping orderShipping = orderShippingMapper.selectByPrimaryKey(orderId);
		//4.封装Order对象
		order.setOrderItems(orderList);
		order.setOrderShipping(orderShipping);
		*/
		Order order = orderMapper.findOrderById(orderId);
		return order;
	}
	
	
	
	
	
	
}
