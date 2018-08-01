package com.jt.cart.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.cart.pojo.Cart;
import com.jt.common.mapper.SysMapper;

public interface CartMapper extends SysMapper<Cart>{
	
	/** 根据userId和itemId查询购物车数据  */
	Cart findCartByUI(Cart cart);
	
	/** 根据userId和itemId删除购物车中的商品 */
	void deleteCart(@Param("userId")Long userId, @Param("itemId")Long itemId);
	
	/** 根据userId和itemId以及num更新购物车商品数量 */
	void updateNum(@Param("userId")Long userId,@Param("itemId") Long itemId,@Param("num")Integer num);
	
	
	
	
	
	
	
	
	
	
	
}
