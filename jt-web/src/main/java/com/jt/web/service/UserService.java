package com.jt.web.service;

import com.jt.web.pojo.User;

public interface UserService {

	/** 用户注册 */
	void saveUser(User user);
	/** 用户登录 */
	String findUserByUP(String username, String password);

}
