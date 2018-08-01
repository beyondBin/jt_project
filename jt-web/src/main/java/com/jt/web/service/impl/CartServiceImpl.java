package com.jt.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/** 根据用户id查询其购物车信息  */
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		String url = "http://cart.jt.com/cart/query/"+userId;
		String jsonData = httpClient.doGet(url);
		List<Cart> cartList = new ArrayList<>();
		try {
			SysResult sysResult = objectMapper.readValue(jsonData, SysResult.class);
			cartList = (List<Cart>) sysResult.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartList;
	}
	/** 添加商品到购物车功能实现  */
	@Override
	public void saveCart(Cart cart) {
		//1.请求url
		String url = "http://cart.jt.com/cart/save";
		//2.封装数据
		Map<String,String> params = new HashMap<>();
		params.put("userId", cart.getUserId()+"");
		params.put("itemId", cart.getItemId()+"");
		params.put("itemTitle", cart.getItemTitle());
		params.put("itemImage", cart.getItemImage());
		params.put("itemPrice", cart.getItemPrice()+"");
		params.put("num", cart.getNum()+"");
		//3.发起请求
		httpClient.doPost(url, params);
	}
	
	
	/** 根据用户id和商品id删除购物车商品 */
	@Override
	public void deleteCart(Long userId, Long itemId) {
		//1.请求url
		String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		//2.发起请求
		httpClient.doGet(url);
	}
	
	
	/** 根据用户id和商品id以及数量更新购物车指定商品的数量 */
	@Override
	public void updateNum(Long userId,Long itemId, Integer num) {
		//1.请求url
		String url = "http://cart.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
		//2.发送请求
		httpClient.doGet(url);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
