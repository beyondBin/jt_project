package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/** 主页页面 */
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	/**
	 * url:/page/item-add
	 * restFul语法规则:
	 * 	1.传递的参数需要使用“/”分割
	 * 	2.传递的参数需要使用{}包裹
	 * 接收参数要求:
	 * 	1.接收的参数必须时经过{}包裹的参数
	 * 	2.需要使用@PathVariable注解实现数据的转化
	 * 
	 * get请求：
	 * 	localhost:8091/page?moduleName='item-add'
	 * 
	 * restFul请求
	 * 	localhost:8091/page/item-add
	 * 
	 * 注意：
	 * @PathVariable(value="module")
	 * 如果参数名称接收时一致的可以省略不屑value属性.
	 * 如果不一致必须写
	 */
	
	@RequestMapping("/page/{module}")
	public String Module(@PathVariable String module){
		return module;
	}
	
	
	
	
}
