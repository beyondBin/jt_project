package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;

/** 购物车服务接口 */
public interface CartService {
	
	/** 查询购物车信息 */
	List<Cart> findCartByUserId(Long userId);
	
	/** 加入购物车功能 */
	void saveCart(Cart cart);
	
	/** 根据userId和itemId删除购物车中的商品 */
	void deleteCart(Long userId, Long itemId);
	
	/** 根据userId和itemId以及num更新购物车商品数量 */
	void updateNum(Long userId, Long itemId, Integer num);

	
	
	
	
}
