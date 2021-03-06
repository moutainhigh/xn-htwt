package com.cdkj.loan.api.impl;

import com.cdkj.loan.ao.IRepayBizAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.dto.req.XN630514Req;
import com.cdkj.loan.dto.res.BooleanRes;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 录入黑名单处理结果(商品分期)
 * @author: xieyj 
 * @since: 2018年6月7日 下午5:40:12 
 * @history:
 */
public class XN630514 extends AProcessor {
    private IRepayBizAO repaybizAO = SpringContextHolder
        .getBean(IRepayBizAO.class);

    private XN630514Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        repaybizAO.enterBlackListProduct(req.getCode(), req.getBlackHandleNote(),
            req.getUpdater(), req.getRemark());
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN630514Req.class);
        ObjValidater.validateReq(req);
    }

}
