package com.tqmall.sigma.test.biz;

import com.tqmall.sigma.biz.beans.tonglian.param.PayPwdParam;
import com.tqmall.sigma.biz.beans.tonglian.param.UpdatePhoneParam;
import com.tqmall.sigma.biz.bizenum.IdentityTypeEnum;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberPwdService;
import com.tqmall.sigma.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by linjian on 17/4/7.
 */
public class TlMemberPwdServiceTest extends BaseTest {

    @Autowired
    private TlMemberPwdService tlMemberPwdService;

    @Test
    public void test_getSetPayPwdUrl() {
        PayPwdParam param = new PayPwdParam();
        param.setBizUserId("2658");
        param.setPhone("13989649617");
        param.setName("王小二");
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("610422198600000000");
        param.setJumpUrl("http://www.baidu.com");
        param.setBackUrl("http://app.yipei360.com/sigma/allinpay/back/url/setPayPwd");
        String url = tlMemberPwdService.getSetPayPwdUrl(param);
        System.out.println(url);
    }

    @Test
    public void test_getUpdatePayPwdUrl() {
        PayPwdParam param = new PayPwdParam();
        param.setBizUserId("66666");
        param.setName("马邵娟");
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("360681199006011389");
        param.setJumpUrl("http://www.baidu.com");
        String url = tlMemberPwdService.getUpdatePayPwdUrl(param);
        System.out.println(url);
    }

    @Test
    public void test_getResetPayPwdUrl() {
        PayPwdParam param = new PayPwdParam();
        param.setBizUserId("66666");
        param.setName("马邵娟");
        param.setPhone("15968195053");
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("360681199006011389");
        param.setJumpUrl("http://www.baidu.com");
        String url = tlMemberPwdService.getResetPayPwdUrl(param);
        System.out.println(url);

    }

    @Test
    public void test_getUpdatePhoneByPayPwdUrl() {
        UpdatePhoneParam param = new UpdatePhoneParam();
        param.setBizUserId("66666");
        param.setName("马邵娟");
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("360681199006011389");
        param.setOldPhone("15968195053");
        param.setJumpUrl("http://www.baidu.com");
        param.setBackUrl("http://app.yipei360.com/sigma/allinpay/back/url/updatePhoneByPayPwd");
        String url = tlMemberPwdService.getUpdatePhoneByPayPwdUrl(param);
        System.out.println(url);
    }
}
