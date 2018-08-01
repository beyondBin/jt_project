package com.jt.manage.service;

import java.util.List;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.vo.EasyUITree;

public interface ItemService {
	
	List<Item> findAll();
	
	
	EasyUITree findItemByPage(int page,int rows);
	
	
	String findItemCatName(Long itemId);
	
	
	void saveItem(Item item,String desc);


	void updateItem(Item item,String desc);


	void updateStatus(Long[] ids, int status);


	void deleteItem(Long[] ids);

	//根据商品ID查询商品描述信息
	ItemDesc findItemDesc(Long itemId);


	//EasyUITree findItemCacheByPage(int page, int rows);


	Item findItemById(Long itemId);




}
