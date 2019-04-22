package com.cdkj.loan.bo;

import java.util.List;

import com.cdkj.loan.bo.base.IPaginableBO;
import com.cdkj.loan.domain.CreditJour;
import com.cdkj.loan.dto.req.XN632490Req;
import com.cdkj.loan.dto.req.XN632492Req;

public interface ICreditJourBO extends IPaginableBO<CreditJour> {

    public String saveCreditJour(XN632490Req req);

    public void removeCreditJour(String code);

    public void refreshCreditJour(XN632492Req req);

    public List<CreditJour> queryCreditJourList(CreditJour condition);

    public CreditJour getCreditJour(String code);

}