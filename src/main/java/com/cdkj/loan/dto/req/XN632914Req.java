package com.cdkj.loan.dto.req;

/**
 * 贷后统计
 * @author: jiafr 
 * @since: 2018年7月11日 下午9:02:27 
 * @history:
 */
public class XN632914Req extends APageReq {
    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 5780013307270124748L;

    // 客户姓名
    private String userName;

    // 地区
    private String region;

    // 贷款银行
    private String loanBank;

    // 抵押情况
    private String pledgeStatus;

    // 节点
    private String curNodeCode;

    // 是否作废
    private String isCancel;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public String getPledgeStatus() {
        return pledgeStatus;
    }

    public void setPledgeStatus(String pledgeStatus) {
        this.pledgeStatus = pledgeStatus;
    }

    public String getCurNodeCode() {
        return curNodeCode;
    }

    public void setCurNodeCode(String curNodeCode) {
        this.curNodeCode = curNodeCode;
    }

    public String getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(String isCancel) {
        this.isCancel = isCancel;
    }

}