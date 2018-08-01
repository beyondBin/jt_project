package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.ItemCatTree;

@Controller
@RequestMapping("/item/")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 实现商品分类列表
	 * @RequestParam(value="",defaultValue="",required=true)
	 * value:接收参数的名称
	 * defaultValue:默认值  如果参数为null 则添加默认值
	 * required:标识是否是必填项(true:参数必须传递,否则违反SpringMVC校验规则)
	 */
	@RequestMapping("cat/list")
	@ResponseBody
	public List<ItemCatTree> findItemCatById(
			@RequestParam(value="id",defaultValue="0",required=true) Long parentId){
		//1.实现商品一级分类查询
		return itemCatService.findItemCatById(parentId);
	}
	
	
}
