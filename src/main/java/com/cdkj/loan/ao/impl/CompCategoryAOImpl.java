package com.cdkj.loan.ao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.loan.ao.ICompCategoryAO;
import com.cdkj.loan.bo.ICompCategoryBO;
import com.cdkj.loan.bo.ISYSUserBO;
import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.CompCategory;
import com.cdkj.loan.domain.SYSUser;
import com.cdkj.loan.dto.req.XN632740Req;
import com.cdkj.loan.dto.req.XN632741Req;
import com.cdkj.loan.exception.BizException;

/**
 * 库存类别
 * @author: silver 
 * @since: 2018年6月4日 下午9:55:23 
 * @history:
 */
@Service
public class CompCategoryAOImpl implements ICompCategoryAO {

    @Autowired
    private ICompCategoryBO compCategoryBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Override
    public String addCompCategory(XN632740Req req) {
        CompCategory data = new CompCategory();
        data.setName(req.getName());
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        data.setUpdateDatetime(new Date());
        return compCategoryBO.saveCompCategory(data);
    }

    @Override
    public void editCompCategory(XN632741Req req) {
        if (!compCategoryBO.isCompCategoryExist(req.getCode())) {
            throw new BizException("xn0000", "库存不存在");
        }
        CompCategory data = new CompCategory();
        data.setCode(req.getCode());
        data.setName(req.getName());
        data.setUpdater(req.getUpdater());
        data.setRemark(req.getRemark());
        data.setUpdateDatetime(new Date());

        compCategoryBO.refreshCompCategory(data);
    }

    @Override
    public Paginable<CompCategory> queryCompCategoryPage(int start, int limit,
            CompCategory condition) {
        Paginable<CompCategory> paginable = compCategoryBO.getPaginable(start,
            limit, condition);
        for (CompCategory compCategory : paginable.getList()) {
            initCompCategory(compCategory);
        }
        return paginable;
    }

    @Override
    public List<CompCategory> queryCompCategoryList(CompCategory condition) {
        List<CompCategory> compCategoryList = compCategoryBO
            .queryCompCategoryList(condition);
        for (CompCategory compCategory : compCategoryList) {
            initCompCategory(compCategory);
        }
        return compCategoryList;
    }

    @Override
    public CompCategory getCompCategory(String code) {
        CompCategory compCategory = compCategoryBO.getCompCategory(code);
        initCompCategory(compCategory);
        return compCategory;
    }

    private void initCompCategory(CompCategory compCategory) {
        if (StringUtils.isNotBlank(compCategory.getUpdater())) {
            SYSUser sysUser = sysUserBO.getUser(compCategory.getUpdater());
            compCategory.setUpdaterName(sysUser.getRealName());
        }
    }
}
