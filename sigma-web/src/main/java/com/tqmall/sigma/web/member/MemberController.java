package com.tqmall.sigma.web.member;

import com.tqmall.core.common.entity.Result;
import com.tqmall.sigma.biz.beans.param.*;
import com.tqmall.sigma.biz.beans.result.LoginTypeBO;
import com.tqmall.sigma.biz.bizenum.VerificationCodeTypeEnum;
import com.tqmall.sigma.biz.service.common.VerificationCodeService;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gaorongyu on 17/5/9.
 */
@Controller
@RequestMapping("member")
public class MemberController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 档口或汽配管家登陆后跳转路由
     *
     * @param model
     * @param param
     * @return
     */
    @RequestMapping("login/thirdPart")
    @ResponseBody
    public String loginThirdPart(Model model, BaseParam param) {
        model.addAttribute("userId", param.getUserId());
        model.addAttribute("userType", param.getUserType());
        LoginTypeBO loginTypeBO = memberInfoService.getLoginMemberType(param.getUserId(), param.getUserType());
        switch (loginTypeBO.getLoginMemberStatusEnum()) {
            case UN_SIGN_UP:
                return "";
            case UN_MESSAGE:
                model.addAttribute("memberType", loginTypeBO.getMemberType());
                return "";
            case CHECK_PENDING:
                return "";
            case PASS:
                return "";
            case REFUSE:
                model.addAttribute("failReason", loginTypeBO.getFailReason());
                return "";
            default:
                return "";
        }
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(){
        return "";
    }

    @RequestMapping("create")
    @ResponseBody
    public Result createMember(@RequestBody CreateMemberParam param) {
        return Result.wrapSuccessfulResult(memberInfoService.createMember(param));
    }

    @RequestMapping("sendVerificationCode")
    @ResponseBody
    public Result sendVerificationCode(@RequestBody SendVerificationCodeParam param) {
        param.setVerificationCodeType(VerificationCodeTypeEnum.BIND_PHONE.getId());
        return Result.wrapSuccessfulResult(verificationCodeService.sendTlVerificationCode(param));
    }

    @RequestMapping("bindPhone")
    @ResponseBody
    public Result bindPhone(@RequestBody BindPhoneParam param) {
        return Result.wrapSuccessfulResult(memberInfoService.bindPhone(param));
    }

    @RequestMapping("setMemberInfo")
    @ResponseBody
    public Result setMemberInfo(MemberInfoParam param) {
        return Result.wrapSuccessfulResult(memberInfoService.setMemberInfo(param));
    }

    @RequestMapping("info")
    @ResponseBody
    public Result getMemberInfo(BaseParam param) {
        return Result.wrapSuccessfulResult(memberInfoService.getMemberInfo(param));
    }

    @RequestMapping("identityInfo")
    @ResponseBody
    public Result getIdentityInfo(BaseParam param){
        return Result.wrapSuccessfulResult(memberInfoService.getIdentityInfo(param));
    }
}
