/**
 * @Title Sms.java 
 * @Package com.ogc.standard.domain 
 * @Description 
 * @author dl  
 * @date 2018年8月22日 下午12:54:19 
 * @version V1.0   
 */
package com.cdkj.loan.domain;

import java.util.Date;

import com.cdkj.loan.dao.base.ABaseDO;

/** 
 * 消息
 * @author: dl 
 * @since: 2018年8月22日 下午12:54:19 
 * @history:
 */
public class Sms extends ABaseDO {

    private static final long serialVersionUID = -3821903316597198985L;

    // ***********db properties***********
    // 消息编号
    private String code;

    // 消息类型
    private String type;

    // 消息标题
    private String title;

    // 消息内容
    private String content;

    // 状态
    private String status;

    // 创建时间
    private Date createDatetime;

    // 更新人
    private String updater;

    // 更新时间
    private Date updateDatetime;

    // 备注
    private String remark;

    // ***********db properties***********

    private SYSUser sysUser;

    public SYSUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SYSUser sysUser) {
        this.sysUser = sysUser;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
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

}
