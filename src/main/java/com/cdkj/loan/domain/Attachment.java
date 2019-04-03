package com.cdkj.loan.domain;

import com.cdkj.loan.dao.base.ABaseDO;

/**
* 业务
* @author: tao 
* @since: 2019-03-28 10:25:12
* @history:
*/
public class Attachment extends ABaseDO {

	private static final long serialVersionUID = 1L;

	// 编号
	private String code;

	// 业务编号
	private String bizCode;

	// 附件类型
	private String attachType;

	// url地址
	private String url;

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}

	public String getAttachType() {
		return attachType;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}