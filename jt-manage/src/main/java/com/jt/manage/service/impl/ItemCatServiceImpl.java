package com.jt.manage.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.ItemCatTree;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private ItemCatMapper itemCatMapper;
	
	/** 通过spring依赖注入jedis对象 */
	/*@Autowired
	private Jedis jedis;*/
	
	/*@Autowired
	private RedisService redisService;*/
	@Autowired
	private JedisCluster jedisCluster;
	
	
	
	/** json转换对象 */
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 1.根据父级id查询商品分类列表
	 * 2.将分类列表数据封装为ItemCatTree
	 */
	@Override
	public List<ItemCatTree> findItemCatById(Long parentId) {
		/*ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		//1.查询数据：根据其中不为null的属性充当where条件
		List<ItemCat> itemCats = itemCatMapper.select(itemCat);*/
		
		/** 通过缓存查询数据信息 */
		List<ItemCat> itemCats = findItemCatCache(parentId);
		
		
		//2.进行数据封装
		/*List<ItemCatTree> itemCatTreeList = new ArrayList<>();
		for (ItemCat itemCatTemp : itemCats) {
			ItemCatTree itemCatTree = new ItemCatTree();
			itemCatTree.setId(itemCatTemp.getId());
			itemCatTree.setText(itemCatTemp.getName());
			//是父级,则closed;如果不是则open
			String state = itemCatTemp.getIsParent() == true ?"closed":"open";
			itemCatTree.setState(state);
			itemCatTreeList.add(itemCatTree);
		}*/
		List<ItemCatTree> itemCatTreeList = findItemCatTreeCache(itemCats,parentId);
		
		
		return itemCatTreeList;
	}

	
	/**
	 *  通过parentId查询itemCats数据
	 *  1.先查询缓存
	 *  2.判断是否有缓存
	 *  3.若无,则先查询并存入缓存
	 *  4.若有,直接转成JSON后返回
	 */
	public List<ItemCat> findItemCatCache(Long parentId){
		List<ItemCat> itemCats = new ArrayList<>();
		String key ="ITEM_CAT"+parentId;
		String jsonData = jedisCluster.get(key);
		try {
			if(StringUtils.isEmpty(jsonData)){
				//缓存中的数据为空,则需要查询数据库
				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId);
				itemCats = itemCatMapper.select(itemCat);
				//将查询的结果先转化为JSON后保存到redis中
				String itemCatJSON = objectMapper.writeValueAsString(itemCats);
				jedisCluster.setex(key,86400,itemCatJSON);
			}else{
				//缓存中有数据,将ItemCatListJSON转化为List<ItemCat>对象
				//因为没有直接将集合转为JSON的API,所以先转成数组后再转成List
				ItemCat[] arrayItemCats = objectMapper.readValue(jsonData, ItemCat[].class);
				itemCats = Arrays.asList(arrayItemCats);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return itemCats;
	}
	
	/**
	 * 将ItemCatTree存入redis缓存中
	 * @param itemCats
	 * @param parentId
	 * @return
	 */
	public List<ItemCatTree> findItemCatTreeCache(List<ItemCat> itemCats,Long parentId){
		List<ItemCatTree> itemCatTreeList = new ArrayList<>();
		String key = "ItemCatsTree:"+parentId;
		String jsonData = jedisCluster.get(key);
		try {
			if(StringUtils.isEmpty(jsonData)){
				//缓存中的数据为空,则将数据写入缓存中
				for (ItemCat itemCatTemp : itemCats) {
					ItemCatTree itemCatTree = new ItemCatTree();
					itemCatTree.setId(itemCatTemp.getId());
					itemCatTree.setText(itemCatTemp.getName());
					//是父级,则closed;如果不是则open
					String state = itemCatTemp.getIsParent() == true ?"closed":"open";
					itemCatTree.setState(state);
					itemCatTreeList.add(itemCatTree);
				}
				String itemCatTreeListJSON = objectMapper.writeValueAsString(itemCatTreeList);
				jedisCluster.setex(key, 86400, itemCatTreeListJSON);
			}else{
				//缓存中有数据,直接将缓存取出并转换成List
				ItemCatTree[] arrayItemCatTree = objectMapper.readValue(jsonData, ItemCatTree[].class);
				itemCatTreeList = Arrays.asList(arrayItemCatTree);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatTreeList;
	}

	
	
	
	/**
	 * 查询一级目录所有信息,用ItemCatResult封装
	 * 1.创建需要返回的对象ItemCatResult
	 * 2.准备一个合理的数据结构,封装子父级关系：Map<parentId,List<ItemCat>>
	 * 3.准备一级商品分类菜单
	 * 
	 */
	@Override
	public ItemCatResult findItemCatAll() {
		//1.定义封装对象itemCatResult
		ItemCatResult itemCatResult = new ItemCatResult();
		//2.查询全部商品分类信息(只要status=1的商品)
		ItemCat itemCatTemp = new ItemCat();
		itemCatTemp.setStatus(1);	//表示状态启用
		List<ItemCat> itemCatDBList = itemCatMapper.select(itemCatTemp);
		//3.封装子父级关系
		Map<Long,List<ItemCat>> map = new HashMap<>();
		
		for (ItemCat itemCat : itemCatDBList) {
			//判断map中是否有父级id这个key(有则不是第一次查询)
			if(map.containsKey(itemCat.getParentId())){
				//将该数据追加到List集合中(获取当前key：parendId,然后将对象追加进去)
				map.get(itemCat.getParentId()).add(itemCat);
			}else{
				//代表第一次添加这个父级id(new List<ItemCat>)
				List<ItemCat> tempCatList = new ArrayList<>();
				tempCatList.add(itemCat);
				map.put(itemCat.getParentId(), tempCatList);
			}
		}
		//4.准备一级商品分类菜单
		List<ItemCatData> itemCatList1 = new ArrayList<>();
		//4.1遍历一级商品分类菜单封装一级商品分类
		for (ItemCat itemCat1 : map.get(0L)) {
			ItemCatData itemCatData1 = new ItemCatData();
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			itemCatData1.setName("<a href='"+itemCatData1.getUrl()+"'>"+itemCat1.getName()+"</a>");
			
			//封装二级商品分类菜单
			List<ItemCatData> ItemCatList2 = new ArrayList<>();
			//通过一级商品分类id查询二级商品分类信息
			for (ItemCat itemCat2 : map.get(itemCat1.getId())) {
				ItemCatData itemCatData2 = new ItemCatData();
				itemCatData2.setUrl("/products/"+itemCat2.getId());
				itemCatData2.setName(itemCat2.getName());
				
				//封装三级商品分类菜单
				List<String> ItemCatList3 = new ArrayList<>();
				//通过二级商品分类id查询三级商品分类信息
				for (ItemCat itemCat3 : map.get(itemCat2.getId())) {
					ItemCatList3.add("/products/"+itemCat3.getId()+"|"+itemCat3.getName());
				}
				itemCatData2.setItems(ItemCatList3);
				ItemCatList2.add(itemCatData2);
			}
			itemCatData1.setItems(ItemCatList2);
			//控制集合的长度
			if(itemCatList1.size()>13){
				break;
			}
			itemCatList1.add(itemCatData1);
		}
		itemCatResult.setItemCats(itemCatList1);
		return itemCatResult;
	}

	/**
	 * 1.用户查询时先查询缓存
	 * 2.如果缓存中有数据,则将缓存数据转成需要的对象
	 * 3.如果缓存中没有数据,则查询数据库,之后将数据转化为JSON后保存到redis缓存中,方便下次使用
	 */
	@Override
	public ItemCatResult findItemCatCache() {
		String key = "ITEM_CAT_ALL";
		String jsonData = jedisCluster.get(key);
		ItemCatResult itemCatResult = null;
		try{
			if(StringUtils.isEmpty(jsonData)){
				//表示缓存数据为空,需要查询数据库
				itemCatResult = findItemCatAll();
				String jsonResult = objectMapper.writeValueAsString(itemCatResult);
				jedisCluster.setex(key,86400,jsonResult);
			}else{
				//表示缓存中有需要的数据
				itemCatResult = objectMapper.readValue(jsonData, ItemCatResult.class);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemCatResult;
	}
	
	

	
	
	
	
}
