package com.jt.manage.controller.web;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.User;

@Controller
public class JSONPController {

	
	//@RequestMapping(value="/web/testJSONP",produces="text/html;charset=utf-8")
	//@ResponseBody
	public String getJSONP(String callback){
		//编辑JSON数据
		String json = "{\"id\":\"1\",\"name\":\"张三\"}";
		
		return callback+"("+ json +")";
	}
	
	
	/**
	 * JSONP的API调用
	 * 1.返回特定的JSONP的接收对象
	 * 2.接收callback参数
	 * 3.为对象赋值
	 * 4.直接返回特定的对象
	 */
	@RequestMapping(value="/web/testJSONP")
	@ResponseBody
	public MappingJacksonValue jsonp(String callback){
		User user = new User();
		user.setId(1000);
		user.setName("杯子");
		MappingJacksonValue jacksonValue = new MappingJacksonValue(user);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	
	
	
	
	
	
	
	
}