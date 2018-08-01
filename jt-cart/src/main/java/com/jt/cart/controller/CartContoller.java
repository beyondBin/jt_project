package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartContoller {
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable Long userId){
		try {
			List<Cart> cartList = cartService.findCartByUserId(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "查询购物车失败！");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		try {
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "加入购物车失败！");
	}
	
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(
			@PathVariable Long userId,
			@PathVariable Long itemId){
		try {
			cartService.deleteCart(userId,itemId);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "删除购物车商品失败！");
	}
	
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	public SysResult updateNum(
			@PathVariable Long userId,
			@PathVariable Long itemId,
			@PathVariable Integer num){
		try {
			cartService.updateNum(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车商品数量修改失败！");
	}
	
	
	
	
	
	
	
}
