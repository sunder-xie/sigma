package com.tqmall.sigma.component.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Type;
import java.util.*;

/**
 * do 和 bo 的互相转化类
 */
@Slf4j
public class BdUtil {

    public static <DO, BO> BO do2bo(DO request, Class<BO> cls) {
        if (null == request) return null;
        BO result;
        try {
            result = cls.newInstance();
            BeanUtils.copyProperties(request, result);
        } catch (Exception e) {
            throw new IllegalArgumentException("对象copy失败，请检查相关module", e);
        }
        return result;
    }

    public static <DO, BO> List<BO> do2bo4List(List<DO> request, Class<BO> cls) {
        List<BO> result = Lists.newArrayList();
        for (DO obj : request) {
            result.add(do2bo(obj, cls));
        }
        return result;
    }

    public static <DO, BO> DO bo2do(BO request, Class<DO> cls) {
        if (null == request) return null;
        DO result;
        try {
            result = cls.newInstance();
            BeanUtils.copyProperties(request, result);
        } catch (Exception e) {
            throw new IllegalArgumentException("对象copy失败，请检查相关module", e);
        }
        return result;
    }

    public static <DO, BO> List<DO> bo2do4List(Collection<BO> request, Class<DO> cls) {
        List<DO> result = Lists.newArrayList();
        for (BO obj : request) {
            result.add(bo2do(obj, cls));
        }
        return result;
    }

    public static <DO, BO, K> Map<K, DO> bo2do4Map(Map<K, BO> request, Class<DO> cls) {
        Map<K, DO> result = Maps.newHashMap();
        for (Map.Entry<K, BO> item : request.entrySet()) {
            K key = item.getKey();
            BO val = item.getValue();
            result.put(key, bo2do(val, cls));
        }
        return result;
    }

    public static <DO, BO, K> Map<K, BO> do2bo4Map(Map<K, DO> request, Class<BO> cls) {
        Map<K, BO> result = Maps.newHashMap();
        for (Map.Entry<K, DO> item : request.entrySet()) {
            K key = item.getKey();
            DO val = item.getValue();
            result.put(key, bo2do(val, cls));
        }
        return result;
    }

    public static <target, source> target complexPropertiesCopy(source source, Class<target> cls) {
        if (null == source) return null;
        target target;
        try {
            Gson gson = new Gson();
            String srStr = gson.toJson(source);
            target = gson.fromJson(srStr, cls);
        } catch (Exception e) {
            throw new IllegalArgumentException("对象copy失败，请检查相关module", e);
        }
        return target;
    }

    /**
     * BdUtil.complexPropertiesCopy(source, new TypeToken<List<target>>(){}.getType());
     *
     * @param source
     * @param type
     * @param <target>
     * @param <source>
     * @return
     */
    public static <target, source> target complexPropertiesCopy(source source, Type type) {
        if (null == source) return null;
        target target;
        try {
            Gson gson = new Gson();
            String srStr = gson.toJson(source);
            target = gson.fromJson(srStr, type);
        } catch (Exception e) {
            throw new IllegalArgumentException("对象copy失败，请检查相关module", e);
        }
        return target;
    }

    public static <FO, TO> TO objTran(FO request, Class<TO> cls) {
        if (null == request) return null;
        TO result;
        try {
            result = cls.newInstance();
            BeanUtils.copyProperties(request, result);
        } catch (Exception e) {
            throw new IllegalArgumentException("对象copy失败，请检查相关module", e);
        }
        return result;
    }

    public static <FO, TO> List<TO> objTran4List(List<FO> request, Class<TO> cls) {
        if (request == null) {
            return null;
        }
        List<TO> result = Lists.newArrayList();
        for (FO obj : request) {
            result.add(objTran(obj, cls));
        }
        return result;
    }

}
