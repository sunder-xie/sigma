package com.tqmall.sigma.biz.service.member;

import com.tqmall.sigma.biz.beans.param.ApplyBindBankCardParam;
import com.tqmall.sigma.biz.beans.param.ConfirmBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.result.BoundCardResult;

import java.util.List;

/**
 * Created by linjian on 17/5/18.
 */
public interface MemberBankCardService {

    Boolean applyBindBankCard(ApplyBindBankCardParam param);

    Boolean confirmBindBankCard(ConfirmBindBankCardParam param);

    BoundCardResult queryBoundBankCardInfo(String cardNo);

    List<BoundCardResult> queryBoundBankCardInfo();

    Boolean unbindBankCard(String cardNo);
}
