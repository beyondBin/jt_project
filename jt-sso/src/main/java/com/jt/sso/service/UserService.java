package com.jt.sso.service;

import com.jt.sso.pojo.User;

public interface UserService {

	/**
	 * 检验用户名/手机号码/邮箱是否存在
	 * @param type 
	 * @param param 
	 * @return true:存在/false:不存在
	 */
	boolean findCheckUser(String param, Integer type);
	
	/** 对数据库进行操作,实现注册功能  */
	void saveUser(User user);
	
	/** 查询用户 */
	String findUserByUP(String username, String password);
	
}
