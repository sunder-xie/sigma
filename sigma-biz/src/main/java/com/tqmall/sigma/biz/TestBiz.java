package com.tqmall.sigma.biz;

import com.tqmall.sigma.biz.beans.tonglian.result.order.QueryBalanceResult;
import com.tqmall.sigma.biz.redis.RedisKeys;
import com.tqmall.sigma.biz.redis.annotation.RedisCache;
import com.tqmall.sigma.biz.redis.annotation.RedisKeyParam;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by huangzhangting on 17/3/30.
 */

@Component
public class TestBiz {

    @RedisCache(key = RedisKeys.TEST_KEY, expire = RedisKeys.EXPIRE_TEN_MINUTES)
    public Set<QueryBalanceResult> test_RedisCache(@RedisKeyParam String userId, String agr1, Integer i2){
        System.out.println("test annotation");

        QueryBalanceResult result = new QueryBalanceResult();
        result.setAllAmount(123l);
        Set<QueryBalanceResult> list = new HashSet<>();
        list.add(result);
        return list;
    }

//    @RedisCache(key = RedisKeys.TEST_KEY+"_2")
    public QueryBalanceResult test_RedisCache_2(String userId, String agr1, Integer i2){
        System.out.println("test annotation");

        //return test_private(); //方法内部访问本类方法，Redis缓存拦截器是无效的
        return test_public();
    }

    @RedisCache(key = RedisKeys.TEST_KEY)
    private QueryBalanceResult test_private(){
        System.out.println("test private");
        QueryBalanceResult result = new QueryBalanceResult();
        result.setAllAmount(123l);
        return result;
    }

    @RedisCache(key = RedisKeys.TEST_KEY)
    public QueryBalanceResult test_public(){
        System.out.println("test private");
        QueryBalanceResult result = new QueryBalanceResult();
        result.setAllAmount(123l);
        return result;
    }

}
