package com.jt.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/** 用户注册 */
	@Override
	public void saveUser(User user) {
		String url = "http://sso.jt.com/user/register";
		Map<String,String> params = new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		String jsonData = httpClient.doPost(url,params);
		try {
			//判断返回的数据是否正确,如果不正确,则抛出异常
			SysResult sysResult = objectMapper.readValue(jsonData, SysResult.class);
			if(sysResult.getStatus()!=200){
				throw new RuntimeException();
			}
		} catch (Exception e) {
			//数据添加失败,把异常抛给Controller打印出错误信息
			e.getMessage();
			throw new RuntimeException();
		}
	}

	/** 用户登录操作 */
	@Override
	public String findUserByUP(String username, String password) {
		//1.定义url
		String url = "http://sso.jt.com/user/login";
		//2.封装数据
		Map<String,String> params = new HashMap<>();
		params.put("username", username);
		params.put("password", password);
		//3.发起请求
		String jsonData = httpClient.doPost(url, params);
		try {
			//4.将JSON转成对象
			SysResult sysResult = objectMapper.readValue(jsonData, SysResult.class);
			//5.登录成功,返回token
			if(sysResult.getStatus() == 200){
				String token = (String) sysResult.getData();
				return token;
			}
		} catch (Exception e) {
			e.getMessage();
			throw new RuntimeException();
		}
		return null;
	}
	
	
	
	
}
