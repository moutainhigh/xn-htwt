package com.cdkj.loan.api.impl;

import com.cdkj.loan.ao.IUserAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.common.PhoneUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.dto.req.XN805041Req;
import com.cdkj.loan.dto.res.PKCodeRes;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 前端用户普通注册
 * @author: myb858 
 * @since: 2015年8月23日 上午11:42:00
 * @history:
 */
public class XN805041 extends AProcessor {
    private IUserAO userAO = SpringContextHolder.getBean(IUserAO.class);

    private XN805041Req req = null;

    @Override
    public Object doBusiness() throws BizException {

        synchronized (XN805041.class) {
            String userId = userAO
                .doRegister(req.getMobile(), req.getNickname(),
                    req.getLoginPwd(), req.getSmsCaptcha(), null);

            return new PKCodeRes(userId);
        }

    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN805041Req.class);
        PhoneUtil.checkMobile(req.getMobile());// 判断格式
        ObjValidater.validateReq(req);
    }
}
