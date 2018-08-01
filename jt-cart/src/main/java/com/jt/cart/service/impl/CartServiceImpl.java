package com.jt.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartMapper cartMapper;
	
	
	/** 根据用户ID查询其购物车信息 */
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cartMapper.selectByPrimaryKey(cart);
		return cartMapper.select(cart);
	}
	
	
	/**
	 * 加入购物车功能实现
	 * 1.根据userId和itemId查询购物车数据
	 * 如果购物车中有数据,则应该做购物车的更新商品数量操作
	 * 如果购物车中没有数据,则做购物车入库操作
	 */
	@Override
	public void saveCart(Cart cart) {
		Cart cartDB = cartMapper.findCartByUI(cart);
		Date date = new Date();
		//购物车无数据,执行添加操作
		if(cartDB==null){
			cart.setCreated(date);
			cart.setUpdated(date);
			cartMapper.insert(cart);
		}else{
			//购物车有数据,执行更新数量操作
			int num = cartDB.getNum() + cart.getNum();
			cartDB.setNum(num);
			cartDB.setUpdated(date);
			cartMapper.updateByPrimaryKeySelective(cartDB);
		}
	}

	/** 根据userId和itemId删除购物车中的商品 */
	@Override
	public void deleteCart(Long userId, Long itemId) {
		cartMapper.deleteCart(userId,itemId);
	}

	/** 根据userId和itemId以及num更新购物车商品数量 */
	@Override
	public void updateNum(Long userId, Long itemId, Integer num) {
		cartMapper.updateNum(userId,itemId,num);
	}
	
	
	
	
}
