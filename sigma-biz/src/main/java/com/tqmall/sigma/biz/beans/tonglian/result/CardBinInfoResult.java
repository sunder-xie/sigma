package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class CardBinInfoResult {
    //卡bin
    private String cardBin;
    //卡种
    private Integer cardType;
    //发卡行代码
    private String bankCode;
    //发卡行名称
    private String bankName;
    //卡名
    private String cardName;
    //卡片长度
    private Integer cardLenth;
    //状态（1：有效 0：无效）
    private Integer cardState;
    //卡种名称
    private String cardTypeLabel;

    /**
     * 业务参数
     */
    //是否支持该银行卡
    private boolean isSupport;
}
