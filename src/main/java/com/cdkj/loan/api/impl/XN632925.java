package com.cdkj.loan.api.impl;

import com.cdkj.loan.ao.IMobileReportDemoAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.dto.req.XN632925Req;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 社保地区列表查询
 * @author: CYL 
 * @since: 2018年9月17日 上午10:58:27 
 * @history:
 */
public class XN632925 extends AProcessor {
    private IMobileReportDemoAO mobileReportDemoAO = SpringContextHolder
        .getBean(IMobileReportDemoAO.class);

    private XN632925Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        return mobileReportDemoAO.socialsecurityArea(req);
        // return mobileReportDemoAO.callBackUrl();
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN632925Req.class);
        ObjValidater.validateReq(req);
    }
}
