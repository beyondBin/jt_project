package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	/** 创建订单 */
	@RequestMapping("/create")
	public String create(Model model){
		Long userId = UserThreadLocal.getUser().getId();
		//查询当前登录用户的购物信息
		List<Cart> carts = cartService.findCartListByUserId(userId);
		model.addAttribute("carts", carts);
		return "order-cart";
	}
	
	/** 订单入库  */
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order){
		try {
			//获取用户Id
			Long userId = UserThreadLocal.getUser().getId();
			order.setUserId(userId);
			String orderId = orderService.saveOrder(order);
			if(!StringUtils.isEmpty(orderId)){
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "订单入库失败！");
	}
	
	/**
	 * 根据orderId查询后台数据库：订单的全部消息
	 * 要求查询3张表
	 * 1.利用通用Mapper实现数据的查询，封装成Order对象返回(中)
	 * 2.自己编辑sql语句实现数据封装(难)
	 * http://www.jt.com/order/success.html?id=131531558011549
	 */
	@RequestMapping("/success")
	public String success(String id,Model model){
		Order order = orderService.findOrderById(id);
		model.addAttribute("order", order);
		return "success";
	}
	
	
	
}
