package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.jt.manage.pojo.User;

public interface UserMapper {
	
	/** 查询所有用户 */
	@Select("select * from user")
	List<User> userList();
	
	
}
