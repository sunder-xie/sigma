package com.tqmall.sigma.biz.service.tonglian.member;

import com.tqmall.sigma.biz.beans.tonglian.param.ApplyBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.param.BindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.param.ThreeElementsBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.result.*;

/**
 * Created by linjian on 17/4/6.
 */
public interface TlMemberBankCardService {

    GetBankCardBinResult getBankCardBin(String cardNo);

    BindBankCardResult applyBindBankCard(ApplyBindBankCardParam param);

    BindBankCardResult bindBankCard(BindBankCardParam param);

    VerifyPayBindBankCardResult applyVerifyPayBindBankCard(ApplyBindBankCardParam param);

    VerifyPayBindBankCardResult affirmVerifyPayBindCard(BindBankCardParam param);

    BindBankCardResult threeElementsBindBankCard(ThreeElementsBindBankCardParam param);

    SetSafeCardResult setSafeCard(String bizUserId, String cardNo);

    QueryBankCardResult queryBankCard(String bizUserId, String cardNo);

    UnbindBankCardResult unbindBankCard(String bizUserId, String cardNo);
}
