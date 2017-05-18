package com.tqmall.sigma.biz.beans.tonglian.result.order;

import com.tqmall.sigma.biz.beans.tonglian.result.BaseResult;
import lombok.Data;

import java.util.List;

/**
 *
 * 查询账户收支明细，返回结果
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class QueryInExpDetailResult extends BaseResult {
    private Long totalNum; //该账户收支明细总条数
    private List<InExpDetailResult> inExpDetail; //收支明细
}
