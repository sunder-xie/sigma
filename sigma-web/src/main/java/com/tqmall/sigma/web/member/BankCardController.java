package com.tqmall.sigma.web.member;

import com.tqmall.core.common.entity.Result;
import com.tqmall.sigma.biz.beans.param.ApplyBindBankCardParam;
import com.tqmall.sigma.biz.beans.param.ConfirmBindBankCardParam;
import com.tqmall.sigma.biz.service.member.BankCardService;
import com.tqmall.sigma.biz.service.member.MemberBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gaorongyu on 17/5/16.
 */
@Controller
@RequestMapping("bankCard")
public class BankCardController {
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private MemberBankCardService memberBankCardService;

    @RequestMapping("bin/bind")
    @ResponseBody
    public Result getBankCardBinForBind(@RequestParam("cardNo")String cardNo){
        return Result.wrapSuccessfulResult(bankCardService.getBankCardBinForBind(cardNo));
    }

    @RequestMapping("bindApply")
    @ResponseBody
    public Result applyBindBankCard(ApplyBindBankCardParam param){
        return Result.wrapSuccessfulResult(memberBankCardService.applyBindBankCard(param));
    }

    @RequestMapping("confirmBind")
    @ResponseBody
    public Result bindBankCard(ConfirmBindBankCardParam param){
        return Result.wrapSuccessfulResult(memberBankCardService.confirmBindBankCard(param));
    }

    @RequestMapping("boundInfo")
    @ResponseBody
    public Result queryBoundBankCardInfo(@RequestParam("cardNo") String cardNO){
        return Result.wrapSuccessfulResult(memberBankCardService.queryBoundBankCardInfo(cardNO));
    }

    @RequestMapping("allBoundInfo")
    @ResponseBody
    public Result queryAllBoundBankCardInfo(){
        return Result.wrapSuccessfulResult(memberBankCardService.queryBoundBankCardInfo());
    }

    @RequestMapping("unbind")
    @ResponseBody
    public Result unbindBankCard(@RequestParam("cardNo") String cardNO){
        return Result.wrapSuccessfulResult(memberBankCardService.unbindBankCard(cardNO));
    }
}
