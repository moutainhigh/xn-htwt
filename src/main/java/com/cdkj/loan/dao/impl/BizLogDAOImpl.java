package com.cdkj.loan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.loan.dao.IBizLogDAO;
import com.cdkj.loan.dao.base.support.AMybatisTemplate;
import com.cdkj.loan.domain.BizLog;

@Repository("bizLogDAOImpl")
public class BizLogDAOImpl extends AMybatisTemplate implements IBizLogDAO {

    @Override
    public int insert(BizLog data) {
        return super.insert(NAMESPACE.concat("insert_bizLog"), data);
    }

    @Override
    public int delete(BizLog data) {
        return super.delete(NAMESPACE.concat("delete_bizLog"), data);
    }

    @Override
    public BizLog select(BizLog condition) {
        return super.select(NAMESPACE.concat("select_bizLog"), condition,
            BizLog.class);
    }

    @Override
    public long selectTotalCount(BizLog condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_bizLog_count"),
            condition);
    }

    @Override
    public List<BizLog> selectList(BizLog condition) {
        return super.selectList(NAMESPACE.concat("select_bizLog"), condition,
            BizLog.class);
    }

    @Override
    public List<BizLog> selectList(BizLog condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_bizLog"), start, count,
            condition, BizLog.class);
    }

}