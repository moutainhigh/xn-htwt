package com.cdkj.loan.api.impl;

import com.cdkj.loan.ao.IBusAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.dto.req.XN632782Req;
import com.cdkj.loan.dto.res.BooleanRes;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 公车修改
 * @author: CYL 
 * @since: 2018年6月23日 下午12:12:28 
 * @history:
 */
public class XN632782 extends AProcessor {
    private IBusAO busAO = SpringContextHolder.getBean(IBusAO.class);

    private XN632782Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        busAO.editBus(req);
        return new BooleanRes(true);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN632782Req.class);
        ObjValidater.validateReq(req);
    }

}
