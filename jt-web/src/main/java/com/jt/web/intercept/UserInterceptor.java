package com.jt.web.intercept;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
/**
 * 拦截器类：实现HandlerInterceptor接口
 */
public class UserInterceptor implements HandlerInterceptor{
	
	/**
	 * 在真正执行业务逻辑之前进行拦截
	 * 1.return false:表示请求拦截,需要重定向到其他页面
	 * 2.return true :放行用户请求的资源
	 * 3.用户拦截器的业务逻辑：
	 * 	 ·获取Cookie信息
	 * 	 ·获取token值
	 * 	 ·从redis中获取缓存数据(json)
	 * 	 ·将userJSON转化为User对象
	 * 	 ·如果用户没有Cookie或json,则需要用户重定向到登录页面
	 */
	@Autowired
	private JedisCluster jedisCluster;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取Cookie信息
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token = cookie.getValue();
				break;
			}
		}
		
		if(!StringUtils.isEmpty(token)){
			//2.获取userJSON数据
			String userJSON = jedisCluster.get(token);
			//判断用户数据不为空,则转成User对象并传到Controller
			if(!StringUtils.isEmpty(userJSON)){
				User user = objectMapper.readValue(userJSON, User.class);
				//将用户信息放入ThreadLocal
				UserThreadLocal.setUser(user);
				//获取用户信息后需要传递给服务器程序
				return true;//拦截放行
			}
		}
		//3.重定向到登录页面
		response.sendRedirect("/user/login.html");
		return false;//进行拦截
	}
	
	//在请求完成后在返回给用户之前进行拦截
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	
	//给用户展现页面之前进行拦截
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//关闭ThreadLocal,防止内存泄漏
		UserThreadLocal.remove();
	}
	
	
	
}
