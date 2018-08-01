package com.jt.manage.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.service.ItemCatService;
/**
 * 此类用来接收web前端的三级列表请求
 */
@Controller
@RequestMapping("/web")
public class WebItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	
	@RequestMapping("/itemcat/all")
	@ResponseBody
	public MappingJacksonValue findItemCatAll(String callback){
		//封装三级商品分类目录菜单(查询一级目录所有信息)
		ItemCatResult itemCatResult = itemCatService.findItemCatCache();
		MappingJacksonValue jacksonValue = new MappingJacksonValue(itemCatResult);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	
}
