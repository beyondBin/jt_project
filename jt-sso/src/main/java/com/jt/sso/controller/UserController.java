package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	/** 使用用户的校验  http://sso.jt.com/user/check/{params}/{1/2/3/..}?....  */
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public MappingJacksonValue checkUser(@PathVariable String param,
			@PathVariable Integer type,
			String callback){
		boolean flag = userService.findCheckUser(param,type);
		MappingJacksonValue jacksonValue = new MappingJacksonValue(SysResult.oK(flag));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	
	/** 接收jt_web服务层发送的请求：实现用户注册  */
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户注册失败！");
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByUP(String username,String password){
		try {
			String token = userService.findUserByUP(username,password);
			if(token == null){
				return SysResult.build(201, "用户查询失败");
			}
			return SysResult.oK(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户查询失败");
	}
	
	/**
	 * 用户登录成功信息回显
	 * @param ticket
	 * @return
	 */
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public MappingJacksonValue findUserByToken(@PathVariable String ticket,String callback){
		String userJSON = jedisCluster.get(ticket);
		MappingJacksonValue jacksonValue = null; 
		if(StringUtils.isEmpty(userJSON)){
			jacksonValue = new MappingJacksonValue(SysResult.build(201, "用户查询失败！"));
			jacksonValue.setJsonpFunction(callback);
			return jacksonValue;
		}
		jacksonValue = new MappingJacksonValue(SysResult.oK(userJSON));
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
