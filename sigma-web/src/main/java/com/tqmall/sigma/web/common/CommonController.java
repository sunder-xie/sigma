package com.tqmall.sigma.web.common;

import com.tqmall.sigma.biz.service.common.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by gaorongyu on 17/5/9.
 */
@Controller
@RequestMapping("common")
public class CommonController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @RequestMapping("VerificationCode")
    @ResponseBody
    public String getVerificationCode(String unikey){
        return verificationCodeService.getVerificationCode(unikey);
    }
}
