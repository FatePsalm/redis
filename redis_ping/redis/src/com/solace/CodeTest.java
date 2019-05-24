package com.solace;







import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author 作者 Name:CaoGang
 * @version 创建时间：2017年11月7日 上午10:22:50 类说明
 * 测试使用
 */
public class CodeTest {

    public static void main(String[] args) {
        // 2017年11月7日TODO
        CodeTest codeTest = new CodeTest();
        // Jedis jedis = new Jedis("120.78.164.66", 6379);
        Jedis jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
        try {
            String str=jedis.get("11");
            System.out.printf(str);
            // 获取key的方法
            // codeTest.getKey(jedis);
            // 获取String方法
            // codeTest.getString(jedis);
            // 获取List方法
            // codeTest.getList(jedis);
            // 获取Set方法
            // codeTest.getSet(jedis);
            // 获取Hash方法
            // codeTest.getHash(jedis);
            // 获取Hash方法
            // codeTest.getHash(jedis);
            // 获取getZset方法
            //codeTest.getSetTime(jedis);
            jedis.set("你好", "我是小白!");
            jedis.set("11", "我是小白!");
        } finally {
            // TODO: handle finally clause
            JedisPoolUtil.release(JedisPoolUtil.getJedisPoolInstance(), jedis);
        }

    }

    public void getSetTime(Jedis jedis) {
        // 有序时间集合
        for (int i = 0; i < 100; i++) {
            jedis.zadd("TimeSet", new Date().getTime(), "" + i);
        }
        System.out.println(jedis.zrange("TimeSet", 0, -1));
    }

    public void getKey(Jedis jedis) {
        // key..
        Set<String> keys = jedis.keys("*");
        for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            System.out.println(key);
        }
        System.out.println("jedis.exists====>" + jedis.exists("k2"));
        System.out.println(jedis.ttl("k1"));
    }

    public void getString(Jedis jedis) {
        // String
        // jedis.append("k1","myreids");
        System.out.println(jedis.get("k1"));
        jedis.set("k4", "k4_redis");
        System.out.println("----------------------------------------");
        jedis.mset("str1", "v1", "str2", "v2", "str3", "v3");
        System.out.println(jedis.mget("str1", "str2", "str3"));
    }

    public void getList(Jedis jedis) {
        // list


        System.out.println("--------------------------------");
        jedis.lpush("mylist", "v1", "v2", "v3", "v4", "v5");
        List<String> list = jedis.lrange("mylist", 0, -1);
        System.out.println(list);
        for (String element : list) {
            System.out.println(element);
        }
    }

    public void getSet(Jedis jedis) {
        // set
        jedis.sadd("orders", "jd001");
        jedis.sadd("orders", "jd002");
        jedis.sadd("orders", "jd003");
        Set<String> set1 = jedis.smembers("orders");
        for (Iterator iterator = set1.iterator(); iterator.hasNext(); ) {
            String string = (String) iterator.next();
            System.out.println(string);
        }
        jedis.srem("orders", "jd002");// 取出值后抹去.
        System.out.println(jedis.smembers("orders").size());
    }

    public void getHash(Jedis jedis) {
        // hash
        jedis.hset("hash1", "userName", "lisi");
        System.out.println(jedis.hget("hash1", "userName"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("telphone", "13811814763");
        map.put("address", "atguigu");
        map.put("email", "abc@163.com");
        jedis.hmset("hash2", map);
        List<String> result = jedis.hmget("hash2", "telphone", "address", "email");
        Map<String, String> map2 = jedis.hgetAll("hash2");
        for (String element : result) {
            System.out.println(element);
        }
        System.out.println("-------------------------");
        System.out.println(map2);
    }

    public void getZset(Jedis jedis) {
        // zset
        jedis.zadd("zset01", 60d, "v1");
        jedis.zadd("zset01", 70d, "v2");
        jedis.zadd("zset01", 80d, "v3");
        jedis.zadd("zset01", 90d, "v4");
        // 有序
        Set<String> s1 = jedis.zrange("zset01", 0, -1);
        for (Iterator iterator = s1.iterator(); iterator.hasNext(); ) {
            String string = (String) iterator.next();
            System.out.println(string);
        }
    }

}
