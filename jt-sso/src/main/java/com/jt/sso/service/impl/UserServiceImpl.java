package com.jt.sso.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	/**
	 * 检验用户名/手机号码/邮箱是否存在
	 * 1.根据type类型的值,转化为具体的字段
	 * 	 1.1   可选参数1 username、2 phone、3 email
	 * 2.根据用户提供的信息count求和  如果数据>=1或者==0 判断用户信息是否存在
	 * @return true:存在-->1 /false:不存在-->0
	 */
	@Override
	public boolean findCheckUser(String param, Integer type) {
		//1.判断type类型(即需要验证的类型)
		String cloumn = null;
		switch (type) {
		case 1:
			cloumn = "username";
			break;
		case 2:
			cloumn = "phone";
			break;
		case 3:
			cloumn = "email";
			break;
		}
		//2.获取数据库查询数据(用户存在返回true,不存在返回false)
		int count = userMapper.findCheckUser(param,cloumn);
		return count == 0?false:true;
	}

	/** 对数据库进行操作,实现用户注册功能  */
	@Override
	public void saveUser(User user) {
		//1.准备后台数据
		String md5pwd = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5pwd);//添加密文
		System.out.println("密码："+md5pwd);
		user.setEmail(user.getPhone());//为了防止数据库保存(前台没有email传入)
		user.setCreated(new Date());
		user.setUpdated(user.getUpdated());
		//2.进行添加操作
		userMapper.insert(user);
	}

	
	
	/** 查询用户是否存在  */
	@Override
	public String findUserByUP(String username, String password) {
		User userTemp = new User();
		userTemp.setUsername(username);
		userTemp.setPassword(DigestUtils.md5Hex(password));
		User user = userMapper.findUserByUP(userTemp);
		String token = null;
		if(user == null){
			//查询结果为空,表示用户名和密码不匹配
			return null;
		}
		try {
			//如果不为null,则生成密钥,转成JSON放入缓存
			user.setPassword(null);	//敏感数据不应该保存在第三方
			String userJSON = objectMapper.writeValueAsString(user);
			String ps = "JT_TICKET_"+System.currentTimeMillis()+user.getUsername();
			token = DigestUtils.md5Hex(ps);
			//将数据保存到Redis中
			jedisCluster.set(token, userJSON);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
