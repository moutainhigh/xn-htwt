package com.cdkj.loan.api.impl;

import org.apache.commons.lang3.StringUtils;

import com.cdkj.loan.ao.ICreditAO;
import com.cdkj.loan.api.AProcessor;
import com.cdkj.loan.common.DateUtil;
import com.cdkj.loan.common.JsonUtil;
import com.cdkj.loan.core.ObjValidater;
import com.cdkj.loan.domain.Credit;
import com.cdkj.loan.dto.req.XN632116Req;
import com.cdkj.loan.exception.BizException;
import com.cdkj.loan.exception.ParaException;
import com.cdkj.loan.spring.SpringContextHolder;

/**
 * 征信分页查询
 * @author: jiafr 
 * @since: 2018年5月26日 上午10:57:21 
 * @history:
 */
public class XN632116 extends AProcessor {

    private ICreditAO creditAO = SpringContextHolder.getBean(ICreditAO.class);

    private XN632116Req req = null;

    @Override
    public Object doBusiness() throws BizException {
        Credit condition = new Credit();
        condition.setCode(req.getCode());
        condition.setSaleUserId(req.getSaleUserId());
        condition.setInsideJob(req.getInsideJob());
        condition.setTeamCode(req.getTeamCode());
        condition.setUserNameQuery(req.getUserName());
        condition.setBudgetCode(req.getBudgetOrderCode());
        condition.setApplyDatetimeStart(
            DateUtil.getFrontDate(req.getApplyDatetimeStart(), false));
        condition.setApplyDatetimeEnd(
            DateUtil.getFrontDate(req.getApplyDatetimeEnd(), true));
        condition.setCurNodeCode(req.getCurNodeCode());

        String orderColumn = req.getOrderColumn();
        if (StringUtils.isBlank(orderColumn)) {
            orderColumn = ICreditAO.DEFAULT_ORDER_COLUMN;
        }
        condition.setOrder(orderColumn, req.getOrderDir());
        int start = Integer.valueOf(req.getStart());
        int limit = Integer.valueOf(req.getLimit());
        return creditAO.queryCreditPage(start, limit, condition);
    }

    @Override
    public void doCheck(String inputparams, String operator)
            throws ParaException {
        req = JsonUtil.json2Bean(inputparams, XN632116Req.class);
        ObjValidater.validateReq(req);

    }

}
