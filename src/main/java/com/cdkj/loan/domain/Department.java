package com.cdkj.loan.domain;

import java.util.Date;
import java.util.List;

import com.cdkj.loan.dao.base.ABaseDO;

/**
 * 公司/部门/职位
 * @author: xieyj 
 * @since: 2018年6月22日 下午3:00:12 
 * @history:
 */
public class Department extends ABaseDO {
    private static final long serialVersionUID = 682677933434404190L;

    private String code;// 编号

    private String type;// 类型(1=子公司，2=部门，3=职位)

    private String name;// 部门名称

    private String leadUserId; // 负责人用户编号

    private String parentCode;// 上级部门编号

    private int orderNo;// 序号

    private String status;// 状态

    private String updater;// 更新人

    private Date updateDatetime;// 更新时间

    private String remark;// 备注

    /*-----------辅助字段------------*/

    private String leadName;// 负责人名称

    private String leadMobile;// 负责人手机号

    private String keyword;// 关键字

    private List<String> typeList; // 类型列表

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeadUserId() {
        return leadUserId;
    }

    public void setLeadUserId(String leadUserId) {
        this.leadUserId = leadUserId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLeadMobile() {
        return leadMobile;
    }

    public void setLeadMobile(String leadMobile) {
        this.leadMobile = leadMobile;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }
}
