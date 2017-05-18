package com.tqmall.sigma.biz.service.member;

import com.tqmall.sigma.biz.beans.tonglian.result.CardBinInfoResult;

/**
 * Created by gaorongyu on 17/5/16.
 */
public interface BankCardService {
    CardBinInfoResult getBankCardBin(String cardNo);

    CardBinInfoResult getBankCardBinForBind(String cardNo);
}
