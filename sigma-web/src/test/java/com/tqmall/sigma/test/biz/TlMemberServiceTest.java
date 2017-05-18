package com.tqmall.sigma.test.biz;

import com.tqmall.sigma.biz.beans.tonglian.param.*;
import com.tqmall.sigma.biz.beans.tonglian.result.*;
import com.tqmall.sigma.biz.bizenum.IdentityTypeEnum;
import com.tqmall.sigma.biz.bizenum.MemberTypeEnum;
import com.tqmall.sigma.biz.bizenum.SourceEnum;
import com.tqmall.sigma.biz.bizenum.VerificationCodeTypeEnum;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberService;
import com.tqmall.sigma.component.utils.JsonUtils;
import com.tqmall.sigma.component.utils.MapUtils;
import com.tqmall.sigma.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by linjian on 17/4/1.
 */
public class TlMemberServiceTest extends BaseTest {

    @Autowired
    private TlMemberService tlMemberService;

    @Test
    public void test_createMember() {
        CreateMemberResult result = tlMemberService.createMember("22222", MemberTypeEnum.PERSONAL.getId(), SourceEnum.PC.getId());
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_sendVerificationCode() {
        MemberPhoneResult result = tlMemberService.sendVerificationCode("8-8", "15158133523", VerificationCodeTypeEnum.BIND_PHONE.getId());
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_bindPhone() {
        MemberPhoneResult result = tlMemberService.bindPhone("8-8", "15158133523", "456401");
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_changeBindPhone() {
        ChangeBindPhoneParam param = new ChangeBindPhoneParam();
        param.setBizUserId("66666");
        param.setOldPhone("18968111823");
        param.setOldVerificationCode("798369");
        param.setNewPhone("15968195053");
        param.setNewVerificationCode("278830");
        ChangeBindPhoneResult result = tlMemberService.changeBindPhone(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_setRealName() {
        SetRealNameParam param = new SetRealNameParam();
        param.setBizUserId("2658");
        param.setName("王小二");
        param.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        param.setIdentityNo("610422198600000000");
        SetRealNameResult result = tlMemberService.setRealName(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_setCompanyInfo() {
        SetCompanyInfoParam param = new SetCompanyInfoParam();
        param.setBizUserId("77777");
        param.setBackUrl("http://app.yipei360.com/sigma/allinpay/back/url/setCompanyInfo");
        CompanyBasicInfoParam companyBasicInfo = new CompanyBasicInfoParam();
        companyBasicInfo.setCompanyName("企业test0320");
        companyBasicInfo.setBusinessLicense("1234567890");
        companyBasicInfo.setLegalName("法人姓名0320");
        companyBasicInfo.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        companyBasicInfo.setLegalIds("12345678901");
        companyBasicInfo.setLegalPhone("123456789012");
        companyBasicInfo.setAccountNo("123456789023");
        companyBasicInfo.setParentBankName("开户银行0320");
        companyBasicInfo.setBankCityNo("12345");
        param.setCompanyBasicInfo(companyBasicInfo);
        BaseResult result = tlMemberService.setCompanyInfo(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_setPersonalInfo() {
        SetPersonalInfoParam param = new SetPersonalInfoParam();
        param.setBizUserId("66666");
        PersonalBasicInfoParam userInfo = new PersonalBasicInfoParam();
        userInfo.setName("个人test66666");
        param.setUserInfo(userInfo);
        BaseResult result = tlMemberService.setPersonalInfo(param);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_getMemberInfo() {
        MemberInfoResult result = tlMemberService.getMemberInfo("5");
        if (result.getMemberType().equals(MemberTypeEnum.COMPANY.getId())) {
            CompanyBasicInfoResult info = MapUtils.mapToObject(result.getMemberInfo(), CompanyBasicInfoResult.class);
            System.out.println(JsonUtils.toJson(info));
        } else {
            PersonalBasicInfoResult info = MapUtils.mapToObject(result.getMemberInfo(), PersonalBasicInfoResult.class);
            System.out.println(JsonUtils.toJson(info));
        }
    }

    @Test
    public void test_lockMember() {
        BaseResult result = tlMemberService.lockMember("77777");
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_unlockMember() {
        BaseResult result = tlMemberService.unlockMember("5");
        System.out.println(JsonUtils.toJson(result));
    }
}
