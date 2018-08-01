package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	/**  查询当前用户的全部购物信息  */
	@RequestMapping("/show")
	public String show(Model model){
		Long userId = UserThreadLocal.getUser().getId();
		List<Cart> cartList = cartService.findCartListByUserId(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	
	/** 添加商品到购物车功能实现  */
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart){
		Long userId = UserThreadLocal.getUser().getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	/** 根据用户id和商品id删除购物车中的商品 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId){
		Long userId = UserThreadLocal.getUser().getId();
		cartService.deleteCart(userId,itemId);
		return "redirect:/cart/show.html";
	}
	
	
	@RequestMapping("/update/num/{itemId}/{num}")
	public String updateNum(
			@PathVariable Long itemId,
			@PathVariable Integer num){
		Long userId = UserThreadLocal.getUser().getId();
		cartService.updateNum(userId,itemId,num);
		return "redirect:/cart/show.html";
	}
	
	
	
	
}
