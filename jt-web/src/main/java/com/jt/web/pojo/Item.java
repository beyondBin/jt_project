package com.jt.web.pojo;

import com.jt.common.po.BasePojo;
public class Item extends BasePojo{
	private Long id;	//商品Id
	private String title;	//商品标题
	/** 如果表中的字段与对象的属性名不一致,需要使用该注解进行标识
	 *  由于开启了驼峰映射规则,则应省略 @Column注解
	 */
	//@Column(name="sell_point")
	private String sellPoint;	//卖点信息
	private Long price;	//价格
	private Integer num;	//商品数量
	private String barcode;	//二维码
	private String image;	//商品图片信息
	private Long cid;	//商品分类信息
	private Integer status;	//商品状态码信息	
	
	
	
	/** 为了实现图片信息的回显正常添加方法 */
	public String[] getImages(){
		return image.split(",");
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
}
