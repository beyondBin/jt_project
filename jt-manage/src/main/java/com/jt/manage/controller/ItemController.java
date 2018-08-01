package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import com.jt.manage.vo.EasyUITree;

@Controller
@RequestMapping("/item/")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@RequestMapping("findAll")
	@ResponseBody
	public List<Item> findAll(){
		List<Item> list = itemService.findAll();
		return list;
	}
	
	
	
	@RequestMapping("query")
	@ResponseBody
	public EasyUITree findItemByPage(int page,int rows){
		return itemService.findItemByPage(page, rows);
	}
	
	
	/** 
	 * 实现商品分类目录的回显 
	 * 1.如果返回对象  使用@ResponseBody时采用utf-8编码进行数据返回
	 * 2.如果返回字符串  则使用默认的ISO-8859-1编码
	 * 3.
	 */
	@RequestMapping(value="cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatName(Long itemId){
		return itemService.findItemCatName(itemId);
	}
	
	
	/**
	 * 实现商品的新增
	 * @return 封装的消息
	 */
	@RequestMapping("save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			//实现商品详情入库
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品入库失败");
	}
	
	/**
	 * 实现商品的修改
	 * @return 封装的消息
	 */
	@RequestMapping("update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品修改失败");
	}
	
	
	/**
	 * 实现商品的删除
	 * @return 封装的消息
	 */
	@RequestMapping("delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids){
		try {
			itemService.deleteItem(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品删除失败");
	}
	
	
	
	/**
	 * 实现商品的上架
	 * @return 封装的消息
	 */
	@RequestMapping("instock")
	@ResponseBody
	public SysResult instock(Long[] ids){
		try {
			int status = 2;	//下架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201, "商品下架失败");
	}
	
	
	/**
	 * 实现商品的下架
	 * @return 封装的消息
	 */
	@RequestMapping("reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids){
		try {
			int status = 1;	//上架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201, "商品上架失败");
	}
	
	
	/**
	 * 实现商品描述信息查询
	 */
	@RequestMapping("query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDesc(
			@PathVariable Long itemId){
		try {
			//页面中需要展现商品描述信息
			ItemDesc itemDesc = itemService.findItemDesc(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品描述查询失败");
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
