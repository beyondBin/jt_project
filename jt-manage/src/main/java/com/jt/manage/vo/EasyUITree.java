package com.jt.manage.vo;

import java.util.List;

import com.jt.manage.pojo.Item;
/**
 * 该类用来封装Item查询后返回给页面的JSON数据 
 */
public class EasyUITree {
	
	private Integer total;		//记录总数
	private List<Item> rows;	//查询的记录数据
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Item> getRows() {
		return rows;
	}
	public void setRows(List<Item> rows) {
		this.rows = rows;
	}
	
}
