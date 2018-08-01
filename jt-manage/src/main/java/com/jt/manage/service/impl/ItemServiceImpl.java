package com.jt.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public List<Item> findAll() {
		return itemMapper.findAll();
	}
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 1.total  记录总数
	 * 2.rows   分页后的结果
	 */
	@Override
	public EasyUITree findItemByPage(int page, int rows) {
		//获取记录总数
		//int total = itemMapper.findCount();
		int total = itemMapper.selectCount(null);
		/**
		 * 例子：
		 * select * from tb_item limit 起始位置,查询行数 
		 * select * from tb_item limit (page-1)*rows,rows
		 */
		int start = (page - 1)*rows;
		List<Item> rowList = itemMapper.findItemByPage(start,rows);
		EasyUITree easyUITree = new EasyUITree();
		easyUITree.setTotal(total);
		easyUITree.setRows(rowList);
		return easyUITree;
	}
	
/*	*//**
	 * 查询时使用redis缓存
	 *//*
	@Override
	public EasyUITree findItemCacheByPage(int page,int rows){
		String key ="ITEM_ALL_"+page+rows;
		String jsonData = jedisCluster.get(key);
		EasyUITree easyUITree = null;
		try {
			if(StringUtils.isEmpty(jsonData)){
				//如果缓存中没有数据,则查询数据库并转成JSON放入缓存中
				easyUITree = findItemByPage(page, rows);
				String result = objectMapper.writeValueAsString(easyUITree);
				jedisCluster.set(key, result);
				
			}else{
				//如果缓存中有需要的数据,则直接转成EasyUITree取出
				easyUITree = objectMapper.readValue(jsonData, EasyUITree.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return easyUITree;
	}
	
*/	
	
	

	@Override
	public String findItemCatName(Long itemId) {
		//暂时借用itemMapper查询,后期维护时再改进
		return itemMapper.findItemCatName(itemId);
	}
	
	@Override
	public void saveItem(Item item,String desc) {
		//封装数据
		item.setStatus(1);	//1.正常 2.下架 3.删除
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		//实现商品详情的入库操作
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void deleteItem(Long[] ids) {
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);
	}
	
	@Override
	public void updateItem(Item item,String desc) {
		item.setStatus(1);
		item.setUpdated(new Date());
		item.setPrice(item.getPrice());
		item.setNum(item.getNum());
		//采用动态更新的操作:只更新不为null的数据
		itemMapper.updateByPrimaryKeySelective(item);
		//修改商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Override
	public void updateStatus(Long[] ids, int status) {
		itemMapper.updateStatus(ids, status);
	}

	@Override
	public ItemDesc findItemDesc(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

	
	
	
	

}
