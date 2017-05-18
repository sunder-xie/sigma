package com.tqmall.sigma.test.biz;

import com.tqmall.sigma.biz.beans.tonglian.param.ApplyBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.param.BindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.param.ThreeElementsBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.result.*;
import com.tqmall.sigma.biz.bizenum.CardTypeEnum;
import com.tqmall.sigma.biz.bizenum.IdentityTypeEnum;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberBankCardService;
import com.tqmall.sigma.component.utils.JsonUtils;
import com.tqmall.sigma.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by linjian on 17/4/1.
 */
public class TlMemberBankCardServiceTest extends BaseTest {

    @Autowired
    private TlMemberBankCardService tlMemberBankCardService;

    @Test
    public void test_getBankCardBin() {
        GetBankCardBinResult result = tlMemberBankCardService.getBankCardBin("6228480329277138775");
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_applyBindBankCard() {
        ApplyBindBankCardParam param = new ApplyBindBankCardParam();
        param.setBizUserId("66666");
        param.setCardNo("6228480329277138774");
        param.setPhone("15968195053");
        param.setName("马邵娟");
        param.setCardType(CardTypeEnum.JJK.getId());
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("360681199006011389");
        BindBankCardResult result = tlMemberBankCardService.applyBindBankCard(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_bindBankCard() {
        BindBankCardParam param = new BindBankCardParam();
        param.setBizUserId("66666");
        param.setTranceNum("D2017040720411");
        param.setTransDate("20170407");
        param.setPhone("15968195053");
        param.setVerificationCode("394126");
        BindBankCardResult result = tlMemberBankCardService.bindBankCard(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_applyVerifyPayBindBankCard() {
        ApplyBindBankCardParam param = new ApplyBindBankCardParam();
        param.setBizUserId("66666");
        param.setCardNo("6228480329277138777");
        param.setPhone("15968195053");
        param.setName("马邵娟");
        param.setCardType(CardTypeEnum.JJK.getId());
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("360681199006011389");
        VerifyPayBindBankCardResult result = tlMemberBankCardService.applyVerifyPayBindBankCard(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_affirmVerifyPayBindCard() {
        BindBankCardParam param = new BindBankCardParam();
        param.setBizUserId("66666");
        param.setTranceNum("SMFSQ00001454");
        param.setVerificationCode("9999");
        VerifyPayBindBankCardResult result = tlMemberBankCardService.affirmVerifyPayBindCard(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_threeElementsBindBankCard() {
        ThreeElementsBindBankCardParam param = new ThreeElementsBindBankCardParam();
        param.setBizUserId("2658");
        param.setName("王小二");
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("610422198600000000");
        param.setCardNo("6228480329277138776");
        BindBankCardResult result = tlMemberBankCardService.threeElementsBindBankCard(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_setSafeCard() {
        SetSafeCardResult result = tlMemberBankCardService.setSafeCard("66666", "6228480329277138775");
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_queryBankCard() {
        QueryBankCardResult result = tlMemberBankCardService.queryBankCard("66666", null);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_unbindBankCard() {
        UnbindBankCardResult result = tlMemberBankCardService.unbindBankCard("66666", "6228480329277138775");
        System.out.println(JsonUtils.toJson(result));
    }
}
