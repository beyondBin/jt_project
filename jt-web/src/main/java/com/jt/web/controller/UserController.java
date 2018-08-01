package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	/** 实现前台通用页面跳转  */
	@RequestMapping(value="/{module}")
	public String module(@PathVariable String module){
		return module;
	}
	
	/** 接收jt_web浏览器发送过来的注册请求  */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户注册失败！");
	}
	
	/** 实现用户登录操作 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(String username,String password,HttpServletResponse response){
		try {
			//1.查询用户
			String token = userService.findUserByUP(username,password);
			//保证token不能为null
			if(StringUtils.isEmpty(token)){
				return SysResult.build(201, "用户登录失败");
			}
			//token不为null,需要将token保存到Cookie中
			Cookie cookie = new Cookie("JT_TICKET",token);
			//2.设定cookie生命周期（0:表示Cookie立即销毁  -1:关闭会话销毁Cookie  >0:指定生命周期）
			cookie.setMaxAge(5*24*3600);//5天,单位(s)
			cookie.setPath("/"); 		//表示Cookie的所属权限,一般都为/
			response.addCookie(cookie);	//保存cookie
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登录失败！");
	}
	
	/**
	 * 1.先获取Cookie信息之后删除Cookie
	 * 2.删除redis缓存数据
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		//1.获取Cookie
		Cookie[] cookies = request.getCookies();
		//因为为数组,所以遍历获取
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				//2.获取token,删除缓存数据
				String token = cookie.getValue();
				jedisCluster.del(token);	
				//利用新的cookie删除原有的cookie(同名即可)
				Cookie cookie2 = new Cookie("JT_TICKET", "");
				cookie2.setMaxAge(0);
				cookie2.setPath("/");
				response.addCookie(cookie2);
				break;
			}
		}
		//重定向到首页
		return "redirect:/index.html";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
