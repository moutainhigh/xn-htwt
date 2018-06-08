package com.cdkj.loan.ao;

import java.util.List;

import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.RepayBiz;
import com.cdkj.loan.dto.req.XN630510Req;
import com.cdkj.loan.dto.req.XN630511Req;
import com.cdkj.loan.dto.req.XN630513Req;

public interface IRepayBizAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public void editBankcardNew(XN630510Req req);

    public void editBankcardModify(XN630511Req req);

    public Paginable<RepayBiz> queryRepayBizPage(int start, int limit,
            RepayBiz condition);

    public List<RepayBiz> queryRepayBizList(RepayBiz condition);

    public RepayBiz getRepayBiz(String code);

    public void advanceRepay(String code, String updater, String remark);

    public void enterBlackList(String code, String blackHandleNote,
            String updater, String remark);

    public void confirmClose(XN630513Req req);

}
