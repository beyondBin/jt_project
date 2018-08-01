package com.jt.web.thread;

import com.jt.web.pojo.User;

/** 用户本地线程工具类 */
public class UserThreadLocal {
	
	/**
	 * 1.定义ThreadLocal
	 *   T:ThreadLocal中保存数据类型
	 *   使用规则:
	 *   	1.如果有单个数据需要使用ThreadLocal进行数据传输
	 *   	则泛型就是单个对象的类型
	 *  	2.如果有多个数据需要使用ThreadLocal进行数据传输
	 *  	则可以创建不同的ThreadLocal;
	 *  	或者使用Map类型进行数据封装,则泛型类型写Map;
	 */
	private static ThreadLocal<User> threadLocal = new ThreadLocal<>();
	
	/** 获取变量  */
	public static User getUser(){
		return threadLocal.get();
	}
	/** 设置变量  */
	public static void setUser(User user){
		threadLocal.set(user);
	}
	/** 移除变量  */
	public static void remove(){
		threadLocal.remove();
	}
	
}
