package com.cdkj.loan.dto.req;

public class XN630406Req extends AListReq {

    /** 
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
     */
    private static final long serialVersionUID = 67761930202644722L;

    // 名称（选填）
    private String name;

    // 字母序号（选填）
    private String letter;

    // 状态（选填）
    private String status;

    // 是否推荐（选填）
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
