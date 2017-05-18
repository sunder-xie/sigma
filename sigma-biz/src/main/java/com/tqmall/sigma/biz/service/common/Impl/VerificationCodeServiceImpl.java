package com.tqmall.sigma.biz.service.common.Impl;

import com.google.common.collect.Lists;
import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.sigma.biz.beans.encrypt.DES;
import com.tqmall.sigma.biz.beans.param.SendVerificationCodeParam;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.biz.bizenum.RedisKeyEnum;
import com.tqmall.sigma.biz.service.common.VerificationCodeService;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberService;
import com.tqmall.sigma.biz.redis.RedisClient;
import com.tqmall.sigma.biz.redis.RedisKeys;
import com.tqmall.sigma.entity.MemberInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaorongyu on 17/5/9.
 */
@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private TlMemberService tlMemberService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    public String getVerificationCode(String uniqueKey) {
        List<Character> list = Lists.newArrayList();
        for (char c = 'a'; c <= 'z'; c++) {
            list.add(c);
        }
        String verificationCode = "";
        for (int i = 0; i < 4; i++) {
            verificationCode += list.get((int) (Math.random() * 26));
        }
        //确认db 目前一分钟
        redisClient.set(RedisKeyEnum.VERIFICATION_CODE.getKey() + uniqueKey, verificationCode, RedisKeys.EXPIRE_ONE_MINUTE);
        return verificationCode;
    }

    @Override
    public String getNumVerificationCode(String uniqueKey) {
        String verificationCode = (int) (Math.random() * 10000) + "";
        //确认db 目前一分钟
        redisClient.set(RedisKeyEnum.VERIFICATION_CODE_NUM.getKey() + uniqueKey, verificationCode, RedisKeys.EXPIRE_ONE_MINUTE);
        return verificationCode;
    }

    @Override
    public Boolean checkVerificationCode(String uniqueKey, String verificationCode, String redisKey) {
        String realVerificationCode = redisClient.get(redisKey + uniqueKey);
        if (realVerificationCode == null) {
            throw new BusinessCheckFailException(SigmaError.IDENTIFY_CODE_OUTTIME);
        }
        if (realVerificationCode.equals(verificationCode)) {
            return Boolean.TRUE;
        }
        redisClient.delKey(RedisKeyEnum.VERIFICATION_CODE.getKey() + uniqueKey);
        return Boolean.FALSE;
    }

    @Override
    public Boolean sendTlVerificationCode(SendVerificationCodeParam param) {
        Integer userId;
        try {
            userId = Integer.parseInt(DES.decryptDES(param.getUserId()));
        } catch (Exception e) {
            throw new BusinessCheckFailException(SigmaError.CODE_NULL);
        }
        //获取memberId
        MemberInfoDO member = memberInfoService.getMemberInfoByBizUserIdAndType(userId, param.getUserType());
        tlMemberService.sendVerificationCode(member.getId().toString(), param.getPhone(), param.getVerificationCodeType());
        return Boolean.TRUE;
    }
}
