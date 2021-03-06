package com.cdkj.loan.dto.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 发布制度
 * @author: silver 
 * @since: 2018年6月8日 上午10:38:53 
 * @history:
 */
public class XN632732Req {
    // 编号
    @NotBlank
    private String code;

    // 备注
    private String remark;

    // 更新人
    @NotBlank
    private String updater;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

}
