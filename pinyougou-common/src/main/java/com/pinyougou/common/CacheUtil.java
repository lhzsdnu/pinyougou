package com.pinyougou.common;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * 缓存工具类
 *
 * @author 栾宏志
 * @since 2018-07-22
 */
public class CacheUtil {

    //Spring封装了RedisTemplate对象来进行对Redis的各种操作，它支持所有的Redis原生的api
    //RedisTemplate位于spring-data-redis包下
    //如果没特殊情况，切勿定义成RedisTemplate<Object, Object>，否则根据里氏替换原则，使用的时候会造成类型错误
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //添加对象
    public void addValue(String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    //获取对象
    public Object getValue(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    //删除对象
    public void removeValue(String key) {
        redisTemplate.delete(key);
    }

    //向Set集合添加值
    public void addSetValue(String key, Object value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    //获取Set集合的值
    public Set getSetValue(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    //删除Set集合中的某个值
    public void removeSetValue(String key, Object value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

    //向List集合添加值
    public void addListValue(String key, Object value) {
        redisTemplate.boundListOps(key).leftPush(value);
    }

    //获取List集合
    public List getListValues(String key) {
        return redisTemplate.boundListOps(key).range(0, -1);//-1代表所有
    }

    //获取List集合
    public Object getListValueByIndex(String key, long index) {
        return redisTemplate.boundListOps(key).index(index);
    }

    //删除List集合中的某个值
    public void removeListValue(String key, long index) {
        redisTemplate.boundListOps(key).remove(index, null);
    }

}
