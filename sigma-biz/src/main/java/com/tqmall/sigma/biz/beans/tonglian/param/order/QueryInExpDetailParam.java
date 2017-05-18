package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Data;

/**
 *
 * 查询账户收支明细，参数对象
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class QueryInExpDetailParam {
    private String bizUserId;
    private String dateStart; //开始日期 格式：yyyy-MM-dd，非必填字段，请求通联接口时，不能传null或者空，直接不传这个属性即可
    private String dateEnd; //结束日期 格式：yyyy-MM-dd，非必填字段
    private Integer startPosition = 1; //起始位置，从1开始
    private Integer queryNum = 20; //查询条数，默认20条

}
