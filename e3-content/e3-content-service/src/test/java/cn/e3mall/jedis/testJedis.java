package cn.e3mall.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * Jedis测试方法
 * <p>
 * Title: testJedis
 * @version 1.0
 */
public class testJedis {
	@Test
	public void testJedis() throws Exception {
		// 创建一个连接Jedis对象，参数，host，port
		Jedis jedis = new Jedis("192.168.0.125", 6379);
		// 直接使用Jedis操作redis。所有jedis的命令对应一个方法
		jedis.set("test122", "my first jedi22 test");
		String string = jedis.get("test123");
		System.out.println(string);
		// 关闭连接
		jedis.close();
	}

	@Test
	public void testJedisPool() throws Exception {
		// 创建一个连接池对象，俩个参数，host，port
		JedisPool jedisPool = new JedisPool("192.168.0.125", 6379);
		// 从连接池获得一个连接，就是一个Jedis对象
		Jedis jedis = jedisPool.getResource();
		// 使用jedis操作redis
		String string = jedis.get("test123");
		System.out.println(string);
		// 关闭连接，每次使用完后关闭连接，连接池资源回收
		jedis.close();
		// 关闭连接
		jedisPool.close();
	}
	@Test
	public void testJedisCluster() throws Exception {
		//创建一个JedisCluster对象。有一个参数nodes是一个set类型。set中包含若干个HostAndPort对象。
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.0.125", 7001));
		nodes.add(new HostAndPort("192.168.0.125", 7002));
		nodes.add(new HostAndPort("192.168.0.125", 7003));
		nodes.add(new HostAndPort("192.168.0.125", 7004));
		nodes.add(new HostAndPort("192.168.0.125", 7005));
		nodes.add(new HostAndPort("192.168.0.125", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		//直接使用JedisCluster对象操作redis。
		jedisCluster.set("test", "123");
		String string = jedisCluster.get("test");
		System.out.println(string);
		//关闭JedisCluster对象
		jedisCluster.close();
	}
	
}
