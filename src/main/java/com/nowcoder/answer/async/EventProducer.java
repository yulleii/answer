package com.nowcoder.answer.async;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.answer.util.JedisAdapter;
import com.nowcoder.answer.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel){
        try{
            String json= JSONObject.toJSONString(eventModel);
            String key= RedisKeyUtil.getEventKey();
            jedisAdapter.lpush(key,json);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
