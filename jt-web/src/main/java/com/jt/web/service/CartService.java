package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Cart;

public interface CartService {
	
	/** 根据用户id查询其购物车信息  */
	List<Cart> findCartListByUserId(Long userId);
	
	/** 加入购物车 */
	void saveCart(Cart cart);

	/** 根据用户id和商品id删除购物车商品 */
	void deleteCart(Long userId, Long itemId);
	
	/** 根据用户id和商品id以及数量更新购物车指定商品的数量 */
	void updateNum(Long userId,Long itemId, Integer num);

}
