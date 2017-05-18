package com.tqmall.sigma.test.biz;

import com.google.common.collect.Lists;
import com.tqmall.sigma.biz.TestBiz;
import com.tqmall.sigma.biz.beans.tonglian.result.order.QueryBalanceResult;
import com.tqmall.sigma.biz.redis.RedisClient;
import com.tqmall.sigma.biz.redis.lock.RedisLock;
import com.tqmall.sigma.biz.redis.lock.RedisLockCallback;
import com.tqmall.sigma.component.utils.JsonUtils;
import com.tqmall.sigma.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by huangzhangting on 17/3/31.
 */
public class RedisTest extends BaseTest {
    @Autowired
    private RedisClient redisClient;

    @Test
    public void test_set_get(){
        String key = "hzt_test";
        String str = redisClient.set(key, "hanihao", 3600);
        System.out.println(str);
        System.out.println(redisClient.get(key));

        key = "hzt_test_lazy";
        QueryBalanceResult result = new QueryBalanceResult();
        result.setAllAmount(123l);
        result.setFreezenAmount(12l);
        str = redisClient.lazySet(key, result, 3600);
        System.out.println(str);
        System.out.println(redisClient.lazyGet(key, QueryBalanceResult.class));

        key = "hzt_test_lazy_list";
        List<QueryBalanceResult> list = new ArrayList<>();
        list.add(result);
        str = redisClient.lazySet(key, list, 3600);
        System.out.println(str);
        System.out.println(redisClient.lazyGetList(key, QueryBalanceResult.class));

        key = "hzt_test_incr";
        redisClient.set(key, "1", 3600*24);
    }

    @Test
    public void test_incr(){
        String key = "hzt_test_incr";
        Long l = redisClient.incr(key);
        System.out.println(l+"");
    }

    @Test
    public void test_keys(){
        String pattern = RedisLock.LOCK_PREFIX+"*";
        Set<String> keys = redisClient.getKeys(pattern);
        System.out.println(keys);
    }

    @Test
    public void test_del(){
        String key = "hzt_test_incr-";
        System.out.println(redisClient.delKey(key));

        Set<String> keys = new HashSet<>();
        keys.add(key);
        keys.add("hzt_test");
        System.out.println("keys="+keys);
        System.out.println(redisClient.delKeys(keys));

    }

    @Autowired
    private TestBiz testBiz;

    @Test
    public void test_cache_interceptor(){
        Set<QueryBalanceResult> result = testBiz.test_RedisCache("123", "hzt", 123);
        System.out.println(JsonUtils.toJson(result));

//        QueryBalanceResult result = testBiz.test_RedisCache_2("123", "hzt", 123);
//        System.out.println(JsonUtil.objectToStr(result));

    }


    @Autowired
    private RedisLock redisLock;

    @Test
    public void test_redis(){
        //为了测试，真正使用时无需加这个前缀，程序自动会加上
        String lockKey = RedisLock.LOCK_PREFIX+"test_redis_lock_key";
        String times = (System.currentTimeMillis() + 20*1000)+"";
        redisClient.set(lockKey, times, 0);
        System.out.println(redisClient.get(lockKey));
    }

    @Test
    public void test_redis_lock(){
        String lockKey = "test_redis_lock_key";
        Map result = redisLock.execute(lockKey, new RedisLockCallback<Map>() {
            @Override
            public Map callback() {
                Map<String, Object> map = new HashMap<>();
                map.put("12", 123);
                map.put("hz", 12341);
                return map;
            }
        });
        System.out.println(JsonUtils.toJson(result));

        List<Integer> list = redisLock.execute(lockKey, new RedisLockCallback<List<Integer>>() {
            @Override
            public List<Integer> callback() {
                return Lists.newArrayList(1,2,45);
            }
        });
        System.out.println(list);

    }

    @Test
    public void test_18(){
        long time1 = System.nanoTime();
        getExpireTimeMillis();
        System.out.println(System.nanoTime()-time1);
    }

    private static final int EXPIRE_TIME = 600*1000; //锁失效时间，单位：毫秒

    private String getExpireTimeMillis(){
        return Long.toString(System.currentTimeMillis() + EXPIRE_TIME);
    }
    private String getExpireTimeMillis2(){
        return (System.currentTimeMillis() + EXPIRE_TIME)+"";
    }

}
