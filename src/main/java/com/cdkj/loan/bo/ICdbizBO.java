package com.cdkj.loan.bo;

import com.cdkj.loan.bo.base.IPaginableBO;
import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.BizTeam;
import com.cdkj.loan.domain.Cdbiz;
import com.cdkj.loan.domain.SYSUser;
import com.cdkj.loan.dto.req.XN632123Req;
import java.util.List;

public interface ICdbizBO extends IPaginableBO<Cdbiz> {

    Cdbiz saveCdbiz(String bankCode, String bizType, Long dkAmount,
            SYSUser sysUser, BizTeam bizTeam, String node, String dealType, String remark);

    int refreshCdbiz(Cdbiz data);

    List<Cdbiz> queryCdbizList(Cdbiz condition);

    List<Cdbiz> queryListByTeamCode(String teamCode);

    List<Cdbiz> queryListByYwyUser(String ywyUser);

    Paginable<Cdbiz> getPaginableByRoleCode(Cdbiz condition, int start,
            int limit);

    Cdbiz getCdbiz(String code);

    void refreshStatus(Cdbiz cdbiz, String status);

    void refreshStatus(Cdbiz cdbiz, String status, String remark);

    void refreshMqStatus(Cdbiz cdbiz, String status);

    void refreshFbhgpsStatus(Cdbiz cdbiz, String status);

    void refreshFircundangStatus(Cdbiz cdbiz, String status);

    void refreshSeccundangStatus(Cdbiz cdbiz, String status);

    void refreshZfStatus(Cdbiz cdbiz, String status);

    void refreshYwy(Cdbiz cdbiz, String ywyUser);

    void refreshMakeCardStatus(Cdbiz cdbiz, String status);

    void refreshMakeCardNode(Cdbiz cdbiz, String node);

    void refershCurNodeCode(Cdbiz cdbiz, String node);

    void refreshInsideJob(Cdbiz cdbiz, String insideJob);

    void refreshIntevCurNodeCode(Cdbiz cdbiz, String intevCurNodeCode);

    void interview(Cdbiz cdbiz, XN632123Req req);

    void refreshCardAddress(Cdbiz cdbiz, String cardPostAddress);

    void refreshRepayCard(Cdbiz cdbiz, String repayCardNumber);

}
