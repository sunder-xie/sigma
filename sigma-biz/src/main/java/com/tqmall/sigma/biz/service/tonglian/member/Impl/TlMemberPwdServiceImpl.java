package com.tqmall.sigma.biz.service.tonglian.member.Impl;

import com.tqmall.sigma.biz.beans.tonglian.param.PayPwdParam;
import com.tqmall.sigma.biz.beans.tonglian.param.UpdatePhoneParam;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberPwdService;
import com.tqmall.sigma.component.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by linjian on 17/4/7.
 */
@Service
public class TlMemberPwdServiceImpl implements TlMemberPwdService {

    private static final String MEMBER_PWD_SERVICE = "MemberPwdService";

    private static final String SET_PWD_URL = "http://122.227.225.142:23661/pwd/setPayPwd.html?";

    private static final String UPDATE_PWD_URL = "http://122.227.225.142:23661/pwd/updatePayPwd.html?";

    private static final String RESET_PWD_URL = "http://122.227.225.142:23661/pwd/resetPayPwd.html?";

    private static final String UPDATE_PHONE_URL = "http://122.227.225.142:23661/pwd/updatePhoneByPayPwd.html?";

    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;

    @Override
    public String getSetPayPwdUrl(PayPwdParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getPhone(), "手机号码不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        Assert.hasLength(param.getJumpUrl(), "跳转返回页面地址不能为空");
        Assert.hasLength(param.getBackUrl(), "后台通知地址不能为空");
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        Map<String, Object> requestParam = interfaceInvokeBiz.getReq(MEMBER_PWD_SERVICE, "setPayPwd", MapUtils.objectToMap(param));
        return SET_PWD_URL + interfaceInvokeBiz.getRequestParamStr(requestParam, true);
    }

    @Override
    public String getUpdatePayPwdUrl(PayPwdParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        Map<String, Object> requestParam = interfaceInvokeBiz.getReq(MEMBER_PWD_SERVICE, "updatePayPwd", MapUtils.objectToMap(param));
        return UPDATE_PWD_URL + interfaceInvokeBiz.getRequestParamStr(requestParam, true);
    }

    @Override
    public String getResetPayPwdUrl(PayPwdParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.hasLength(param.getPhone(), "手机号码不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        Map<String, Object> requestParam = interfaceInvokeBiz.getReq(MEMBER_PWD_SERVICE, "resetPayPwd", MapUtils.objectToMap(param));
        return RESET_PWD_URL + interfaceInvokeBiz.getRequestParamStr(requestParam, true);
    }

    @Override
    public String getUpdatePhoneByPayPwdUrl(UpdatePhoneParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        Assert.hasLength(param.getOldPhone(), "原手机号码不能为空");
        Assert.hasLength(param.getBackUrl(), "后台通知地址不能为空");
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        Map<String, Object> requestParam = interfaceInvokeBiz.getReq(MEMBER_PWD_SERVICE, "updatePhoneByPayPwd", MapUtils.objectToMap(param));
        return UPDATE_PHONE_URL + interfaceInvokeBiz.getRequestParamStr(requestParam, true);
    }
}
