package com.tqmall.sigma.test.biz.member;

import com.tqmall.sigma.biz.beans.encrypt.DES;
import com.tqmall.sigma.biz.beans.param.CreateMemberParam;
import com.tqmall.sigma.biz.bizenum.MemberTypeEnum;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by linjian on 17/4/1.
 */
public class MemberServiceTest extends BaseTest {

    @Autowired
    private MemberInfoService memberInfoService;

    @Test
    public void test_createMember() throws Exception {
        CreateMemberParam param = new CreateMemberParam();
        param.setUserId(DES.encryptDES("675988"));
        param.setUserType(1);
        param.setEmail("675988@qq.com");
        param.setPassword("tqmall2017");
        param.setMemberType(MemberTypeEnum.PERSONAL.getId());
        memberInfoService.createMember(param);
    }
}
