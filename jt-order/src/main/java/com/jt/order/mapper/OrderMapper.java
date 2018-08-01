package com.jt.order.mapper;

import java.util.Date;

import com.jt.common.mapper.SysMapper;
import com.jt.order.pojo.Order;

public interface OrderMapper extends SysMapper<Order>{
    
	
	Order findOrderById(String orderId);

	/** 根据超时时间修改状态码  */
	void updateStatus(Date timeOut);
	
	
}