package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
 * 该类为Redis工具类
 */
@Service
public class RedisService {
	//实现分片配置
/*	@Autowired(required=false)
	private ShardedJedisPool jedisPool;
	
	public void set(String key,String value){
		ShardedJedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		//将链接还回池中
		jedisPool.returnResource(jedis);
	}
	
	public String get(String key){
		ShardedJedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		jedisPool.returnResource(jedis);
		return result;
	}*/
	
	
	
	/**
	 * 实现哨兵的配置
	 * 1.引入哨兵链接池对象
	 * 2.编辑set/get方法
	 * 3.扩充一个添加超时时间的方法
	 * required=false-->表示服务启动时暂时不去检测注入情况，当程序调用时检测是否注入
	 * 一般工具类都需要加(required=false)
	 */
	
	//实现哨兵
	@Autowired(required=false)
	private JedisSentinelPool sentinelPool;
	
	public void set(String key,String value){
		Jedis jedis = sentinelPool.getResource();
		jedis.set(key, value);
		sentinelPool.returnResource(jedis);//还回池中
	}
	
	public String get(String key){
		Jedis jedis = sentinelPool.getResource();
		String result = jedis.get(key);
		sentinelPool.returnResource(jedis);
		return result;
	}
	
	
	/** 实现set操作添加超时时间 */    //TimeUnit.DAYS
	public void set(String key,String value,int seconds){
		Jedis jedis = sentinelPool.getResource();
		jedis.setex(key, seconds, value);
		sentinelPool.returnResource(jedis);
	}
	
	
	
	
	
	
	
	
}
