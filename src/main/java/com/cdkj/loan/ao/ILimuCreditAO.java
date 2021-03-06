package com.cdkj.loan.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.LimuCredit;
import com.cdkj.loan.dto.req.XN632949Req;

@Component
public interface ILimuCreditAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public void addLimuCredit(LimuCredit data);

    public int editLimuCredit(LimuCredit data);

    public Paginable<LimuCredit> queryLimuCreditPage(int start, int limit,
            LimuCredit condition);

    public List<LimuCredit> queryLimuCreditList(LimuCredit condition);

    public LimuCredit getLimuCredit(int id);

    public Object getLimuCreditByType(XN632949Req req);

    // 查询状态
    public Object queryLimuCreditMap(String userId);

}
