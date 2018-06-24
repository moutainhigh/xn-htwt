package com.cdkj.loan.api.impl;

import com.cdkj.loan.ao.ILogisticsAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.dto.req.XN632151Req;
import com.cdkj.loan.dto.res.BooleanRes;
import com.cdkj.loan.enums.EBoolean;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 收件并审核通过
 * @author: silver 
 * @since: 2018年5月30日 下午4:08:33 
 * @history:
 */
public class XN632151 extends AProcessor {
    private ILogisticsAO logisticsAO = SpringContextHolder
        .getBean(ILogisticsAO.class);

    private XN632151Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        logisticsAO.receiveLogistics(req.getCode(), req.getReceiver(),
            req.getOperater(), req.getRemark());
        return new BooleanRes(true, EBoolean.YES.getCode());
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN632151Req.class);
        ObjValidater.validateReq(req);
    }

}
