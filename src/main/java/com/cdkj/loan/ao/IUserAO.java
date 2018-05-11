package com.cdkj.loan.ao;

import java.util.List;

import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.User;

public interface IUserAO {
    String DEFAULT_ORDER_COLUMN = "user_id";

    // C端用户注册
    public String doRegister(String mobile, String nickname, String loginPwd,
            String smsCaptcha, String kind);

    // 检查手机号是否存在
    public void doCheckMobile(String mobile);

    // 验证码注册用户
    public String doCaptchaLoginReg(String mobile, String smsCaptcha,
            String loginPwd, String confirmPwd);

    // 用户登录
    public String doLogin(String loginName, String loginPwd, String kind);

    // 注册
    public String insertUser(User data);

    // 分页查询
    public Paginable<User> queryUserPage(int start, int limit, User condition);

    // 列表查询
    public List<User> queryUserList(User condition);

    // 详情查询
    public User getUser(String userId);

}
