package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends SysMapper<Item>{
	
	
	List<Item> findAll();
	
	@Select("select count(*) from tb_item")
	int findCount();
	
	/**
	 * mybatis中不支持多值传参,参数必须进行封装,将多值封装为单值
	 * 1.将数据封装为对象
	 * 2.将数据封装为集合
	 * 3.注解方式
	 * @Param("start")int start
	 * 使用注解其实就是将数据封装为Map,节省代码
	 * @param start
	 * @param rows
	 * @return
	 */
	List<Item> findItemByPage(
			@Param("start")int start,
			@Param("rows")int rows);
	 
	
	
	
	@Select("select name from tb_item_cat where id =#{itemId}")
	String findItemCatName(Long itemId);
	
	
	
	
	void updateStatus(
			@Param("ids")Long[] ids,
			@Param("status")int	status);
	
	
	
	
	
	
	
	
	
	
	
	
}
