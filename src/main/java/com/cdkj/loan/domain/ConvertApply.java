package com.cdkj.loan.domain;

import java.util.Date;
import java.util.List;

import com.cdkj.loan.dao.base.ABaseDO;

/**
* 转正申请
* @author: CYunlai 
* @since: 2018-06-05 21:32:23
* @history:
*/
public class ConvertApply extends ABaseDO {

    private static final long serialVersionUID = 1L;

    // 编号
    private String code;

    // 入职编号
    private String entryCode;

    // 申请人
    private String applyUser;

    // 申请时间
    private Date applyDatetime;

    // 工作总结
    private String workSummary;

    // 总体评价
    private String allEvaluation;

    // 是否转正
    private String isFullWorker;

    // 生效日期
    private Date effectDatetime;

    // 状态
    private String status;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    /*------辅助字段-------*/

    // 申请时间起
    private Date applyDatetimeStart;

    // 申请时间止
    private Date applyDatetimeEnd;

    // 用户
    private SYSUser user;

    // 人事档案
    private Archive archice;

    // 试用期评估列表
    private List<ProbationAssess> probationAssessesList;

    public Archive getArchice() {
        return archice;
    }

    public void setArchice(Archive archice) {
        this.archice = archice;
    }

    public SYSUser getUser() {
        return user;
    }

    public void setUser(SYSUser user) {
        this.user = user;
    }

    public List<ProbationAssess> getProbationAssessesList() {
        return probationAssessesList;
    }

    public void setProbationAssessesList(
            List<ProbationAssess> probationAssessesList) {
        this.probationAssessesList = probationAssessesList;
    }

    public Date getApplyDatetimeStart() {
        return applyDatetimeStart;
    }

    public void setApplyDatetimeStart(Date applyDatetimeStart) {
        this.applyDatetimeStart = applyDatetimeStart;
    }

    public Date getApplyDatetimeEnd() {
        return applyDatetimeEnd;
    }

    public void setApplyDatetimeEnd(Date applyDatetimeEnd) {
        this.applyDatetimeEnd = applyDatetimeEnd;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setEntryCode(String entryCode) {
        this.entryCode = entryCode;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setWorkSummary(String workSummary) {
        this.workSummary = workSummary;
    }

    public String getWorkSummary() {
        return workSummary;
    }

    public void setAllEvaluation(String allEvaluation) {
        this.allEvaluation = allEvaluation;
    }

    public String getAllEvaluation() {
        return allEvaluation;
    }

    public void setIsFullWorker(String isFullWorker) {
        this.isFullWorker = isFullWorker;
    }

    public String getIsFullWorker() {
        return isFullWorker;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdater() {
        return updater;
    }

    public Date getApplyDatetime() {
        return applyDatetime;
    }

    public void setApplyDatetime(Date applyDatetime) {
        this.applyDatetime = applyDatetime;
    }

    public Date getEffectDatetime() {
        return effectDatetime;
    }

    public void setEffectDatetime(Date effectDatetime) {
        this.effectDatetime = effectDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

}
