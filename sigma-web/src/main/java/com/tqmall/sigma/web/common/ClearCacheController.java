package com.tqmall.sigma.web.common;

import com.tqmall.core.common.entity.Result;
import com.tqmall.sigma.biz.redis.RedisClient;
import com.tqmall.sigma.biz.redis.RedisKeys;
import com.tqmall.sigma.biz.redis.lock.RedisLock;
import com.tqmall.sigma.component.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 *
 * 缓存清理controller
 *
 * Created by huangzhangting
 */
@Slf4j
@Controller
@RequestMapping("clearCache")
public class ClearCacheController {

    @Autowired
    private RedisClient redisClient;


    /**
     * 根据表达式清除redis缓存
     * 例子：sigma_user_permissions_* ，也可以是具体值
     * @param pattern
     * @return
     */
    @RequestMapping("clearRedisCache")
    @ResponseBody
    public Result clearRedisCache(String pattern){
        if(pattern==null){
            return Result.wrapErrorResult("", "参数不能为空");
        }
        //这个控制实际上没什么意义，sad~~~
        if(!pattern.startsWith(RedisKeys.PREFIX) || pattern.startsWith(RedisKeys.PREFIX+"*")
                || pattern.startsWith(RedisLock.LOCK_PREFIX+"*")){
            return Result.wrapErrorResult("", "参数不合法, pattern: "+pattern);
        }

        //获取需要删除key
        Set<String> keys = redisClient.getKeys(pattern);
        if(keys.isEmpty()){
            return Result.wrapErrorResult("", "没有相关缓存, pattern: "+pattern);
        }

        //验证key
        Set<String> delKeys = new HashSet<>();
        for(String key : keys){
            if(!isUnFreshKey(key)){
                delKeys.add(key);
            }
        }

        return Result.wrapSuccessfulResult(redisClient.delKeys(delKeys));

    }
    //检验是否不可刷缓存的key
    private boolean isUnFreshKey(String checkKey){
//        for(String key : RedisKeyBean.UN_FRESH_KEY_SET) {
//            String keyReg = key.replaceAll("%s", ".+").replaceAll("%d", ".+");
//            Pattern pattern = Pattern.compile(keyReg);
//            Matcher matcher = pattern.matcher(checkKey);
//            if(matcher.matches()){
//                return true;
//            }
//        }
        return false;
    }


    /**
     * 查询redis锁信息
     * @return
     */
    @RequestMapping("getRedisLocksInfo")
    @ResponseBody
    public Result getRedisLocksInfo(){
        List<Map<String, String>> list = new ArrayList<>();
        String pattern = RedisLock.LOCK_PREFIX+"*";
        Set<String> keys = redisClient.getKeys(pattern);
        if(keys.isEmpty()){
            return Result.wrapSuccessfulResult(list);
        }
        final String str = "date";
        for(String k : keys){
            String val = redisClient.get(k);
            Map<String, String> map = new HashMap<>();
            map.put("key", k);
            map.put("value", val);
            map.put(str, parseDate(val));
            list.add(map);
        }
        Collections.sort(list, new Comparator<Map<String, String>>() {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                return o1.get(str).compareTo(o2.get(str));
            }
        });
        return Result.wrapSuccessfulResult(list);
    }
    private String parseDate(String timeMillis){
        if(timeMillis==null){
            return null;
        }
        Date date = new Date(Long.parseLong(timeMillis));
        return DateUtils.dateToString(date, DateUtils.yyyy_MM_dd_HH_mm_ss);
    }

}
