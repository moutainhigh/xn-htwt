package com.cdkj.loan.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.message.BasicNameValuePair;

/**
 * @Description:信用卡账单
 * @author kx
 * @version v1.2.0
 */
public class BillDemo extends AbstractCredit {

    // 业务参数
    public static final String method = "api.bill.get";// 请求接口

    public static final String bizType = "bill";// 业务类型

    public static final String callBackUrl = "";// 回调地址

    public static final String username = "admin@qq.com";// 用户名---需客户指定

    public static final String password = "admin123";// 密码---需客户指定

    public static final String billType = "email";// 账单类型

    public static final String bankCode = "ALL";// 账单银行

    public static void main(String[] args) throws Exception {

        // 启动服务
        BillDemo service = new BillDemo();
        service.process();

    }

    public void process() throws Exception {
        System.out.println("开始获取信用卡账单信息");

        try {

            // 提交受理请求对象
            List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
            reqParam.add(new BasicNameValuePair("apiKey", apiKey));// API授权
            reqParam.add(new BasicNameValuePair("version", version));// 调用的接口版本
            reqParam.add(new BasicNameValuePair("callBackUrl", callBackUrl));// callBackUrl
            reqParam.add(new BasicNameValuePair("method", method));// 接口名称

            reqParam.add(new BasicNameValuePair("username", username));// 账号
            reqParam.add(new BasicNameValuePair("password",
                new String(Base64.encodeBase64(password.getBytes("UTF-8")))));// 密码
            reqParam.add(new BasicNameValuePair("billType", billType));// 账单类型
            reqParam.add(new BasicNameValuePair("bankCode", bankCode));// 账单银行
            reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));// 请求参数签名

            // 提交受理请求
            doProcess(reqParam);

        } catch (Exception ex) {
            System.out.println("开始获取信用卡账单信息异常：" + ex);
        }
    }

    /**
     * 获取业务类型
     */
    @Override
    public String getBizType() {
        return bizType;
    }
}
