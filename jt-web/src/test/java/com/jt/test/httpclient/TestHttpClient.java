package com.jt.test.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	
	/**
	 * 1.定义请求的路径
	 * 2.创建httpClient对象
	 * 3.创建请求的方式类型get/port
	 * 4.发起请求,获取响应的信息
	 * 5.检查状态码是否为200
	 * 6.获取返回值有效数据
	 */
	@Test
	public void test01() throws Exception{
		String url = "https://item.jd.com/8133837.html";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse httpResponse = client.execute(httpGet);
		if(httpResponse.getStatusLine().getStatusCode()==200){
			//获取全部的响应信息
			String result = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(result);
		}
	}
	
	
}
