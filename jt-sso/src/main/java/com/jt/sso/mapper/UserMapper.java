package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;

public interface UserMapper extends SysMapper<User>{
	
	/**
	 * 查询数据库的校验数据
	 * @param param
	 * @param cloumn
	 * @return
	 */
	int findCheckUser(@Param("param")String param, 
			@Param("cloumn")String cloumn);

	/**
	 * 查询用户是否存在
	 * @param userTemp
	 * @return
	 */
	User findUserByUP(User userTemp);
	
}
