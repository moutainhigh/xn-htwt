package com.cdkj.loan.ao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.SYSBizLog;

@Component
public interface ISYSBizLogAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    // 分页
    public Paginable<SYSBizLog> querySYSBizLogPage(int start, int limit,
            SYSBizLog condition);

    // 列表
    public List<SYSBizLog> querySYSBizLogList(SYSBizLog condition);

    // 详情
    public SYSBizLog getSYSBizLog(int id);

    public Paginable<SYSBizLog> todoListOSS(int start, int limit,
            SYSBizLog condition);

    public Object todoListAPP(SYSBizLog condition);

}
