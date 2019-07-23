package com.nowcoder.answer.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.answer.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
@Service
public class JedisAdapter implements InitializingBean {
    private static final Logger logger= LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;
    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("redis://localhost:6379/10");
    }

    public long sadd(String key,String value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.sadd(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally{
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }

    public long srem(String key,String value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.srem(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally{
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }

    public long scard(String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally{
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }

    public boolean sismember(String key,String value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.sismember(key,value);
        }catch (Exception e){
            logger.error("发生异常"+e.getMessage());
        }finally{
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }




    //演示功能测试代码
    public static void print(int index,Object obj){
        System.out.println(String.format("%d, %s",index,obj.toString()));
    }
    public static void main(String[] args) {
        Jedis jedis=new Jedis("redis://localhost:6379/9");
        jedis.flushDB();//remove the database 9

        //get set operations
        jedis.set("hello","world");
        print(1,jedis.get("hello"));
        jedis.rename("hello","newhello");
        print(2,jedis.get("newhello"));
        jedis.setex("hello2",15,"world");//set expired time

        //incr and decrease
        jedis.set("pv","100");
        jedis.incr("pv");
        jedis.incrBy("pv",5);
        print(3,jedis.get("pv"));
        jedis.decrBy("pv",4);
        print(4,jedis.get("pv"));
        print(5,jedis.keys("*"));

        //list
        String listName="list";
        jedis.del(listName);
        for(int i=0;i<10;i++){
            jedis.lpush(listName,"a"+String.valueOf(i));
        }
        print(6,jedis.lrange(listName,0,-1));
        print(7,jedis.llen(listName));
        print(8,jedis.rpop(listName));
        print(9,jedis.llen(listName));
        print(10,jedis.rpush(listName,"a0"));
        print(11,jedis.lrange(listName,0,-1));
        print(12,jedis.lindex(listName,4));
        print(13,jedis.linsert(listName, ListPosition.AFTER,"a4","aftera4"));
        print(14,jedis.linsert(listName,ListPosition.BEFORE,"a4","beforea4"));
        print(15,jedis.lrange(listName,0,-1));
        print(16,jedis.linsert(listName,ListPosition.BEFORE,"aa","beforeaa"));
        print(15,jedis.lrange(listName,0,-1));

        //hash
        String userKey="userxx";
        jedis.hset(userKey,"name","jim");
        jedis.hset(userKey,"name","newjim");
        jedis.hset(userKey,"age","14");
        jedis.hset(userKey,"phone","11717177");
        print(17,jedis.hget(userKey,"name"));
        print(18,jedis.hgetAll(userKey));
        print(19,jedis.hgetAll(userKey));
        print(20,jedis.hexists(userKey,"phone"));
        print(21,jedis.hkeys(userKey));
        print(22,jedis.hvals(userKey));
        print(23,jedis.hstrlen(userKey,"phone"));
        print(24,jedis.hmget(userKey,"phone","name"));
        //set
        String likeKey1="commentLike1";
        String likeKey2="commentLike2";
        for(int i=0;i<10;i++){
            jedis.sadd(likeKey1,String.valueOf(i));
            jedis.sadd(likeKey2,String.valueOf(i*i));
        }
        print(23,jedis.smembers(likeKey1));
        print(24,jedis.smembers(likeKey2));
        print(25,jedis.sunion(likeKey1,likeKey2));
        print(26,jedis.sdiff(likeKey1,likeKey2));
        print(27,jedis.sinter(likeKey1,likeKey2));
        print(28,jedis.sismember(likeKey1,"12"));
        print(29,jedis.sismember(likeKey2,"16"));
        jedis.srem(likeKey1,"5");
        jedis.smove(likeKey2,likeKey1,"25");
        print(30,jedis.smembers(likeKey1));
        print(31,jedis.scard(likeKey1));

        //sorted set
        String rankKey="rankKey";
        jedis.zadd(rankKey,99,"jim");
        jedis.zadd(rankKey,97,"ji");
        jedis.zadd(rankKey,78,"mikey");
        jedis.zadd(rankKey,88,"Mad");
        jedis.zadd(rankKey,67,"Update");
        jedis.zadd(rankKey,65,"Upset");
        print(32,jedis.zcard(rankKey));
        print(33,jedis.zcount(rankKey,90,100));
        print(34,jedis.zscore(rankKey,"Mad"));
        print(34,jedis.zrevrange(rankKey,0,5));//默认从小到大
        for(Tuple tuple:jedis.zrangeByScoreWithScores(rankKey,"60","100")){
            print(37,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }
        print(38,jedis.zrank(rankKey,"ji"));
        print(39,jedis.zrevrank(rankKey,"ji"));

        String setKey="zset";
        jedis.zadd(setKey,1,"a");
        jedis.zadd(setKey,1,"b");
        jedis.zadd(setKey,1,"c");
        jedis.zadd(setKey,1,"d");
        jedis.zadd(setKey,1,"e");
        print(40,jedis.zlexcount(setKey,"-","+"));
        print(40,jedis.zlexcount(setKey,"(a","(e"));
        print(40,jedis.zlexcount(setKey,"[a","[e"));
        jedis.zremrangeByLex(setKey,"(c","+");
        print(41,jedis.zrange(setKey,0,-1));

//        JedisPool pool=new JedisPool();
//        for(int i=0;i<100;++i){
//            Jedis j=pool.getResource();
//            print(45,j.get("pv"));
//            j.close();
//        }
        User u=new User();
        u.setName("xx");
        u.setPassword("ppp");
        u.setHeadUrl("a.png");
        u.setSalt("salt");
        u.setId(1);
        jedis.set("user1", JSONObject.toJSONString(u));
        String value=jedis.get("user1");
        User user2= JSON.parseObject(value,User.class);
        print(47,user2);

    }
}
