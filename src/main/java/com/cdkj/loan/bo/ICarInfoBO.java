package com.cdkj.loan.bo;

import com.cdkj.loan.bo.base.IPaginableBO;
import com.cdkj.loan.domain.CarInfo;
import com.cdkj.loan.dto.req.XN632120Req;
import com.cdkj.loan.dto.req.XN632500Req;
import java.util.List;

public interface ICarInfoBO extends IPaginableBO<CarInfo> {

    boolean isCarInfoExist(String code);

    String saveCarInfo(String bizCode);

    int removeCarInfo(String code);

    int refreshCarInfo(CarInfo data);

    List<CarInfo> queryCarInfoList(CarInfo condition);

    CarInfo getCarInfo(String code);

    CarInfo getCarInfoByBizCode(String bizCode);

    void saveAttachment(XN632120Req req);

    void saveAttachment(XN632500Req req);

    void entryFbhInfoByBiz(String bizCode, String policyDatetime,
            String policyDueDate);

}
