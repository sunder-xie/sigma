package com.tqmall.sigma.test.util;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.tqmall.sigma.biz.beans.tonglian.result.order.InExpDetailResult;
import com.tqmall.sigma.biz.beans.tonglian.result.order.QueryInExpDetailResult;
import com.tqmall.sigma.component.utils.JsonUtils;
import org.junit.Test;

import java.util.List;

/**
 * Created by huangzhangting on 17/4/7.
 */
public class JsonUtilTest {

    @Test
    public void test_1() throws Exception{
        List<InExpDetailResult> list = Lists.newArrayList();
        InExpDetailResult detailResult = new InExpDetailResult();
        detailResult.setBizOrderNo("12312");
        detailResult.setAccountSetName("hzthzt");
        list.add(detailResult);
        detailResult = new InExpDetailResult();
        detailResult.setBizOrderNo("5678");
        detailResult.setAccountSetName("hzthzt");
        list.add(detailResult);

        QueryInExpDetailResult result = new QueryInExpDetailResult();
//        result.setBizUserId("88888");
        result.setTotalNum(90l);
        result.setInExpDetail(list);

        long time1 = System.currentTimeMillis();

        //fastjson 自动忽略null属性
//        System.out.println(com.alibaba.fastjson.JSON.toJSONString(result));

        //jackson 不会忽略null属性
//        System.out.println(JsonUtil.objectToStr(result));

        //gson 默认自动忽略null属性，可以通过下面的配置序列化null属性
        Gson gson = new GsonBuilder().serializeNulls().create();
        System.out.println(gson.toJson(result));
        System.out.println("耗时："+(System.currentTimeMillis()-time1));

    }

    @Test
    public void test_2(){
        String jsonStr = "{\"bizUserId\":\"88888\",\"inExpDetail\":[{\"accountSetName\":\"hzthzt\",\"bizOrderNo\":\"12312\"},{\"accountSetName\":\"hzthzt\",\"bizOrderNo\":\"5678\"}],\"totalNum\":90}";
        jsonStr = "{\"unknow\":\"88888\",\"bizUserId\":\"88888\",\"inExpDetail\":[{\"accountSetName\":\"hzthzt\",\"bizOrderNo\":\"12312\"},{\"accountSetName\":\"hzthzt\",\"bizOrderNo\":\"5678\"}],\"totalNum\":90}";
        System.out.println(jsonStr);
        long time1 = System.currentTimeMillis();

        //jackson 如果有未知属性，会报错，需要手动在类上加 @JsonIgnoreProperties(ignoreUnknown = true)
//        QueryInExpDetailResult result = JsonUtil.strToObject(jsonStr, QueryInExpDetailResult.class);

        //fastjson 自动忽略未知属性
//        QueryInExpDetailResult result = com.alibaba.fastjson.JSON.parseObject(jsonStr, QueryInExpDetailResult.class);

        //gson 自动忽略未知属性
        Gson gson = new Gson();
        QueryInExpDetailResult result = gson.fromJson(jsonStr, QueryInExpDetailResult.class);

        System.out.println("耗时："+(System.currentTimeMillis()-time1));
        System.out.println(result.getInExpDetail().size());
    }


    @Test
    public void test_to_json_object(){
        String str = "{\"sign\":\"TH0f2xCq/V6/24aLpTs6wYNB/uSGznNA3nTEaahKhRGtpDfoi/mCjPYYr3GNGPRv7lZtN5pgoXPDwI9LVRdbESoZCMlKQ6QF1RGINgOURL1YP8k9L0bY+JeTc/PpRM6x0PjgAtoDEigOp4cXPTGEAgczSBB7VTMs47IJXYe2TrY=\",\"status\":\"OK\",\"signedValue\":\"{\\\"totalNum\\\":4,\\\"inExpDetail\\\":[{\\\"bizOrderNo\\\":\\\"hms20170414003\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704141654471299276499\\\",\\\"oriAmount\\\":1000,\\\"curAmount\\\":2000,\\\"chgAmount\\\":1000,\\\"changeTime\\\":\\\"2017-04-14 16:54:47\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"},{\\\"bizOrderNo\\\":\\\"hms20170411001\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704121121313013274997\\\",\\\"oriAmount\\\":0,\\\"curAmount\\\":1000,\\\"chgAmount\\\":1000,\\\"changeTime\\\":\\\"2017-04-12 11:21:31\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"},{\\\"bizOrderNo\\\":\\\"test_refund000\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704111753117183274870\\\",\\\"oriAmount\\\":1,\\\"curAmount\\\":0,\\\"chgAmount\\\":-1,\\\"changeTime\\\":\\\"2017-04-11 17:53:11\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"},{\\\"bizOrderNo\\\":\\\"consumeApply_order_3\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704111644393570274812\\\",\\\"oriAmount\\\":0,\\\"curAmount\\\":1,\\\"chgAmount\\\":1,\\\"changeTime\\\":\\\"2017-04-11 16:44:39\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"}],\\\"bizUserId\\\":\\\"88888\\\"}\"}";
        str = "{\"sign\":\"TH0f2xCq/V6/24aLpTs6wYNB/uSGznNA3nTEaahKhRGtpDfoi/mCjPYYr3GNGPRv7lZtN5pgoXPDwI9LVRdbESoZCMlKQ6QF1RGINgOURL1YP8k9L0bY+JeTc/PpRM6x0PjgAtoDEigOp4cXPTGEAgczSBB7VTMs47IJXYe2TrY=\",\"status\":\"OK\",\"signedValue\":\"{\"totalNum\":4,\"inExpDetail\":[{\"bizOrderNo\":\"hms20170414003\",\"curFreezenAmount\":0,\"tradeNo\":\"1704141654471299276499\",\"oriAmount\":1000,\"curAmount\":2000,\"chgAmount\":1000,\"changeTime\":\"2017-04-14 16:54:47\",\"accountSetName\":\"商户测试应用1现金账户集\"},{\"bizOrderNo\":\"hms20170411001\",\"curFreezenAmount\":0,\"tradeNo\":\"1704121121313013274997\",\"oriAmount\":0,\"curAmount\":1000,\"chgAmount\":1000,\"changeTime\":\"2017-04-12 11:21:31\",\"accountSetName\":\"商户测试应用1现金账户集\"},{\"bizOrderNo\":\"test_refund000\",\"curFreezenAmount\":0,\"tradeNo\":\"1704111753117183274870\",\"oriAmount\":1,\"curAmount\":0,\"chgAmount\":-1,\"changeTime\":\"2017-04-11 17:53:11\",\"accountSetName\":\"商户测试应用1现金账户集\"},{\"bizOrderNo\":\"consumeApply_order_3\",\"curFreezenAmount\":0,\"tradeNo\":\"1704111644393570274812\",\"oriAmount\":0,\"curAmount\":1,\"chgAmount\":1,\"changeTime\":\"2017-04-11 16:44:39\",\"accountSetName\":\"商户测试应用1现金账户集\"}],\"bizUserId\":\"88888\"}\"}";
        JsonObject jsonObject = JsonUtils.toJsonObject(str);
        System.out.println(jsonObject);

        str = "{\\\"totalNum\\\":4,\\\"inExpDetail\\\":[{\\\"bizOrderNo\\\":\\\"hms20170414003\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704141654471299276499\\\",\\\"oriAmount\\\":1000,\\\"curAmount\\\":2000,\\\"chgAmount\\\":1000,\\\"changeTime\\\":\\\"2017-04-14 16:54:47\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"},{\\\"bizOrderNo\\\":\\\"hms20170411001\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704121121313013274997\\\",\\\"oriAmount\\\":0,\\\"curAmount\\\":1000,\\\"chgAmount\\\":1000,\\\"changeTime\\\":\\\"2017-04-12 11:21:31\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"},{\\\"bizOrderNo\\\":\\\"test_refund000\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704111753117183274870\\\",\\\"oriAmount\\\":1,\\\"curAmount\\\":0,\\\"chgAmount\\\":-1,\\\"changeTime\\\":\\\"2017-04-11 17:53:11\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"},{\\\"bizOrderNo\\\":\\\"consumeApply_order_3\\\",\\\"curFreezenAmount\\\":0,\\\"tradeNo\\\":\\\"1704111644393570274812\\\",\\\"oriAmount\\\":0,\\\"curAmount\\\":1,\\\"chgAmount\\\":1,\\\"changeTime\\\":\\\"2017-04-11 16:44:39\\\",\\\"accountSetName\\\":\\\"商户测试应用1现金账户集\\\"}],\\\"bizUserId\\\":\\\"88888\\\"}";
        str = "{\"totalNum\":4,\"inExpDetail\":[{\"bizOrderNo\":\"hms20170414003\",\"curFreezenAmount\":0,\"tradeNo\":\"1704141654471299276499\",\"oriAmount\":1000,\"curAmount\":2000,\"chgAmount\":1000,\"changeTime\":\"2017-04-14 16:54:47\",\"accountSetName\":\"商户测试应用1现金账户集\"},{\"bizOrderNo\":\"hms20170411001\",\"curFreezenAmount\":0,\"tradeNo\":\"1704121121313013274997\",\"oriAmount\":0,\"curAmount\":1000,\"chgAmount\":1000,\"changeTime\":\"2017-04-12 11:21:31\",\"accountSetName\":\"商户测试应用1现金账户集\"},{\"bizOrderNo\":\"test_refund000\",\"curFreezenAmount\":0,\"tradeNo\":\"1704111753117183274870\",\"oriAmount\":1,\"curAmount\":0,\"chgAmount\":-1,\"changeTime\":\"2017-04-11 17:53:11\",\"accountSetName\":\"商户测试应用1现金账户集\"},{\"bizOrderNo\":\"consumeApply_order_3\",\"curFreezenAmount\":0,\"tradeNo\":\"1704111644393570274812\",\"oriAmount\":0,\"curAmount\":1,\"chgAmount\":1,\"changeTime\":\"2017-04-11 16:44:39\",\"accountSetName\":\"商户测试应用1现金账户集\"}],\"bizUserId\":\"88888\"}";
        jsonObject = JsonUtils.toJsonObject(str);
        System.out.println(jsonObject);

        JsonElement jsonElement = JsonUtils.toJsonElement(str);
        System.out.println(jsonElement);
    }

}
