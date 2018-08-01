package com.jt.common.factory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster>{
	
	private JedisPoolConfig poolConfig;
	
	private String redisNodePrefix;//配置文件的前缀
	
	private Resource propertySource;//导入外部配置文件
	
	@Override
	public JedisCluster getObject() throws Exception {
		
		Set<HostAndPort> nodes = getNodes();
		
		JedisCluster jedisCluster = new JedisCluster(nodes, poolConfig);
		return jedisCluster;
	}

	@Override
	public Class<?> getObjectType() {
		return JedisCluster.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getRedisNodePrefix() {
		return redisNodePrefix;
	}

	public void setRedisNodePrefix(String redisNodePrefix) {
		this.redisNodePrefix = redisNodePrefix;
	}

	public Resource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(Resource propertySource) {
		this.propertySource = propertySource;
	}
	
	
	
	
	/** 从redis.properties中获取redis节点数据 */
	public Set<HostAndPort> getNodes(){
		Set<HostAndPort> nodes = new HashSet<>();
		try {
			//1.创建properties对象
			Properties properties = new Properties();
			properties.load(propertySource.getInputStream());
			for (Object key : properties.keySet()) {
				String redisKey = (String) key;
				//如果前缀不为redis.cluster
				if(!redisKey.startsWith(redisNodePrefix)){
					//跳过这次循环,进入下次
					continue;
				}
				//值-->  IP:端口
				String redisNode = properties.getProperty(redisKey);
				String[] node = redisNode.split(":");
				HostAndPort hostAndPort = new HostAndPort(node[0], Integer.parseInt(node[1]));
				nodes.add(hostAndPort);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
	
	
	
	
	
}
