package com.cdkj.loan.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.loan.ao.IBudgetOrderAO;
import com.cdkj.loan.bo.IAccountBO;
import com.cdkj.loan.bo.IBankBO;
import com.cdkj.loan.bo.IBankcardBO;
import com.cdkj.loan.bo.IBizTeamBO;
import com.cdkj.loan.bo.IBudgetOrderBO;
import com.cdkj.loan.bo.IBudgetOrderFeeBO;
import com.cdkj.loan.bo.IBudgetOrderGpsBO;
import com.cdkj.loan.bo.ICreditBO;
import com.cdkj.loan.bo.ICreditUserBO;
import com.cdkj.loan.bo.IDepartmentBO;
import com.cdkj.loan.bo.IGpsBO;
import com.cdkj.loan.bo.IInvestigateReportBO;
import com.cdkj.loan.bo.ILoanProductBO;
import com.cdkj.loan.bo.ILogisticsBO;
import com.cdkj.loan.bo.INodeFlowBO;
import com.cdkj.loan.bo.IRepayBizBO;
import com.cdkj.loan.bo.IRepayPlanBO;
import com.cdkj.loan.bo.IRepointBO;
import com.cdkj.loan.bo.ISYSBizLogBO;
import com.cdkj.loan.bo.ISYSUserBO;
import com.cdkj.loan.bo.ISmsOutBO;
import com.cdkj.loan.bo.IUserBO;
import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.common.AmountUtil;
import com.cdkj.loan.common.DateUtil;
import com.cdkj.loan.core.StringValidater;
import com.cdkj.loan.domain.Bank;
import com.cdkj.loan.domain.BizTeam;
import com.cdkj.loan.domain.BudgetOrder;
import com.cdkj.loan.domain.BudgetOrderFee;
import com.cdkj.loan.domain.BudgetOrderGps;
import com.cdkj.loan.domain.Credit;
import com.cdkj.loan.domain.CreditUser;
import com.cdkj.loan.domain.Department;
import com.cdkj.loan.domain.Gps;
import com.cdkj.loan.domain.InvestigateReport;
import com.cdkj.loan.domain.LoanProduct;
import com.cdkj.loan.domain.NodeFlow;
import com.cdkj.loan.domain.RepayBiz;
import com.cdkj.loan.domain.Repoint;
import com.cdkj.loan.domain.SYSUser;
import com.cdkj.loan.domain.User;
import com.cdkj.loan.dto.req.XN632120Req;
import com.cdkj.loan.dto.req.XN632123Req;
import com.cdkj.loan.dto.req.XN632125Req;
import com.cdkj.loan.dto.req.XN632126ReqGps;
import com.cdkj.loan.dto.req.XN632128Req;
import com.cdkj.loan.dto.req.XN632130Req;
import com.cdkj.loan.dto.req.XN632133Req;
import com.cdkj.loan.dto.req.XN632135Req;
import com.cdkj.loan.dto.req.XN632141Req;
import com.cdkj.loan.dto.req.XN632180Req;
import com.cdkj.loan.dto.req.XN632190Req;
import com.cdkj.loan.dto.req.XN632191Req;
import com.cdkj.loan.dto.req.XN632192Req;
import com.cdkj.loan.enums.EAccountType;
import com.cdkj.loan.enums.EApproveResult;
import com.cdkj.loan.enums.EBackAdvanceStatus;
import com.cdkj.loan.enums.EBizErrorCode;
import com.cdkj.loan.enums.EBizLogType;
import com.cdkj.loan.enums.EBoolean;
import com.cdkj.loan.enums.EBudgetFrozenStatus;
import com.cdkj.loan.enums.EBudgetOrderNode;
import com.cdkj.loan.enums.ECurrency;
import com.cdkj.loan.enums.EDealType;
import com.cdkj.loan.enums.EIDKind;
import com.cdkj.loan.enums.EInvestigateReportNode;
import com.cdkj.loan.enums.EIsAdvanceFund;
import com.cdkj.loan.enums.ELoanProductStatus;
import com.cdkj.loan.enums.ELoanRole;
import com.cdkj.loan.enums.ELogisticsType;
import com.cdkj.loan.enums.ERepointStatus;
import com.cdkj.loan.enums.ESysRole;
import com.cdkj.loan.enums.EUserKind;
import com.cdkj.loan.exception.BizException;

@Service
public class BudgetOrderAOImpl implements IBudgetOrderAO {

    @Autowired
    private IBudgetOrderBO budgetOrderBO;

    @Autowired
    private IDepartmentBO departmentBO;

    @Autowired
    private ISYSBizLogBO sysBizLogBO;

    @Autowired
    private INodeFlowBO nodeFlowBO;

    @Autowired
    private IBudgetOrderFeeBO budgetOrderFeeBO;

    @Autowired
    private ISYSUserBO sysUserBO;

    @Autowired
    private IBudgetOrderGpsBO budgetOrderGpsBO;

    @Autowired
    private IGpsBO gpsBO;

    @Autowired
    private ILogisticsBO logisticsBO;

    @Autowired
    private ILoanProductBO loanProductBO;

    @Autowired
    private ICreditBO creditBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IBankcardBO bankcardBO;

    @Autowired
    private IRepayBizBO repayBizBO;

    @Autowired
    private IRepayPlanBO repayPlanBO;

    @Autowired
    private IBankBO bankBO;

    @Autowired
    private ICreditUserBO creditUserBO;

    @Autowired
    private IRepointBO repointBO;

    @Autowired
    private IBizTeamBO bizTeamBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private IInvestigateReportBO investigateReportBO;

    @Override
    @Transactional
    public void editBudgetOrder(XN632120Req req) {
        BudgetOrder data = budgetOrderBO.getBudgetOrder(req.getCode());

        Long loanAmount = StringValidater.toLong(req.getLoanAmount());
        data.setLoanAmount(loanAmount);
        if (EDealType.SEND.getCode().equals(req.getDealType())) {
            // 上架贷款产品信息
            LoanProduct loanProduct = loanProductBO
                .getLoanProduct(req.getLoanProductCode());
            if (!ELoanProductStatus.PUBLISH_YES.getCode()
                .equals(loanProduct.getStatus())) {
                throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                    "贷款商品未上架");
            }
            data.setLoanProductCode(loanProduct.getCode());
            data.setLoanProductName(loanProduct.getName());
            data.setRegion(req.getRegion());
            data.setLoanBank(loanProduct.getLoanBank());
            data.setGpsFee(loanProduct.getGpsFee());
            // 公证费=贷款额*公证费比例

            data.setAuthFee(
                AmountUtil.mul(loanAmount, loanProduct.getAuthRate()));
            // 银行服务费=前置*贷款额/（1+前置）
            Long amount = AmountUtil.mul(loanAmount, loanProduct.getPreRate());
            data.setBankFee(
                AmountUtil.div(amount, (1.0 + loanProduct.getPreRate())));
            // 根据是否前置计算公司服务费
            Long companyFee = null;
            if (EBoolean.YES.getCode().equals(loanProduct.getIsPre())) {
                companyFee = AmountUtil.mul(loanAmount,
                    loanProduct.getPreRate());
            } else {
                Long amount1 = AmountUtil.mul(loanAmount,
                    (loanProduct.getYearRate() * 3 - 0.09));
                companyFee = AmountUtil.div(amount1,
                    loanProduct.getPreRate() + 1);
            }
            data.setCompanyFee(companyFee);

        }

        data.setTeamFee(StringValidater.toLong(req.getTeamFee()));

        data.setBizType(req.getBizType());
        data.setLoanPeriod(req.getLoanPeriod());
        data.setInvoiceCompany(req.getInvoiceCompany());
        data.setCarBrand(req.getCarBrand());
        data.setCarSeries(req.getCarSeries());

        data.setCarModel(req.getCarModel());
        data.setCarType(req.getCarType());
        data.setCarPic(req.getCarPic());
        data.setCarHgzPic(req.getCarHgzPic());
        data.setDriveLicense(req.getDriveLicense());
        data.setEvaluateColumn(req.getEvaluateColumn());
        data.setCarFrameNo(req.getCarFrameNo());
        data.setCarEngineNo(req.getCarEngineNo());
        data.setOriginalPrice(StringValidater.toLong(req.getOriginalPrice()));
        data.setInvoicePrice(StringValidater.toLong(req.getInvoicePrice()));

        data.setCarColor(req.getCarColor());
        data.setMonthDeposit(StringValidater.toLong(req.getMonthDeposit()));
        data.setFirstAmount(StringValidater.toLong(req.getFirstAmount()));
        data.setFirstRate(StringValidater.toDouble(req.getFirstRate()));

        data.setSettleAddress(req.getSettleAddress());
        data.setApplyUserName(req.getApplyUserName());
        data.setGender(req.getGender());
        data.setAge(StringValidater.toInteger(req.getAge()));
        data.setMarryState(req.getMarryState());
        data.setPolitical(req.getPolitical());
        data.setNation(req.getNation());

        data.setEducation(req.getEducation());
        data.setIdKind(EIDKind.IDCard.getCode());
        data.setIdNo(req.getIdNo());
        data.setFamilyNumber(StringValidater.toInteger(req.getFamilyNumber()));
        data.setMobile(req.getMobile());

        data.setNowAddress(req.getNowAddress());
        data.setIsCardMailAddress(req.getIsCardMailAddress());
        data.setPostCode1(req.getPostCode1());
        data.setResidenceAddress(req.getResidenceAddress());
        data.setPostCode2(req.getPostCode2());
        data.setFamilyMainAsset(req.getFamilyMainAsset());
        data.setMainAssetInclude(req.getMainAssetInclude());

        data.setMainIncome(req.getMainIncome());
        data.setWorkCompanyName(req.getWorkCompanyName());
        data.setWorkCompanyAddress(req.getWorkCompanyAddress());
        data.setWorkIsCardMailAddress(req.getWorkIsCardMailAddress());
        data.setWorkCompanyProperty(req.getWorkCompanyProperty());
        data.setWorkBelongIndustry(req.getWorkBelongIndustry());
        data.setWorkProfession(req.getWorkProfession());
        data.setWorkDatetime(req.getWorkDatetime());
        data.setSelfCompanyArea(req.getSelfCompanyArea());
        data.setOtherWorkNote(req.getOtherWorkNote());
        data.setWorkAssetPdf(req.getWorkAssetPdf());
        data.setEmployeeQuantity(
            StringValidater.toInteger(req.getEmployeeQuantity()));

        data.setEnterpriseMonthOutput(req.getEnterpriseMonthOutput());
        data.setPosition(req.getPosition());
        data.setPostTitle(req.getPostTitle());
        data.setMonthIncome(req.getMonthIncome());
        data.setMateName(req.getMateName());

        data.setMateMobile(req.getMateMobile());
        data.setMateIdNo(req.getMateIdNo());
        data.setMateEducation(req.getMateEducation());
        data.setMateCompanyName(req.getMateCompanyName());
        data.setMateCompanyAddress(req.getMateCompanyAddress());
        data.setMateCompanyContactNo(req.getMateCompanyContactNo());

        data.setMateZfbJourDatetimeStart(
            DateUtil.strToDate(req.getMateZfbJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setMateZfbJourDatetimeEnd(
            DateUtil.strToDate(req.getMateZfbJourDatetimeEnd(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setMateZfbJourInterest(req.getMateZfbJourInterest());
        data.setMateZfbInterest1(
            StringValidater.toLong(req.getMateZfbInterest1()));
        data.setMateZfbInterest2(
            StringValidater.toLong(req.getMateZfbInterest2()));

        data.setMateZfbJourIncome(
            StringValidater.toLong(req.getMateZfbJourIncome()));
        data.setMateZfbJourExpend(
            StringValidater.toLong(req.getMateZfbJourExpend()));
        data.setMateZfbJourBalance(
            StringValidater.toLong(req.getMateZfbJourBalance()));
        data.setMateZfbJourMonthIncome(
            StringValidater.toLong(req.getMateZfbJourMonthIncome()));
        data.setMateZfbJourMonthExpend(
            StringValidater.toLong(req.getMateZfbJourMonthExpend()));

        data.setMateZfbJourPic(req.getMateZfbJourPic());
        data.setMateZfbJourRemark(req.getMateZfbJourRemark());

        data.setMateWxJourDatetimeStart(
            DateUtil.strToDate(req.getMateWxJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setMateWxJourDatetimeEnd(DateUtil.strToDate(
            req.getMateWxJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setMateWxJourInterest(req.getMateWxJourInterest());
        data.setMateWxInterest1(
            StringValidater.toLong(req.getMateWxInterest1()));
        data.setMateWxInterest2(
            StringValidater.toLong(req.getMateWxInterest2()));

        data.setMateWxJourIncome(
            StringValidater.toLong(req.getMateWxJourIncome()));
        data.setMateWxJourExpend(
            StringValidater.toLong(req.getMateWxJourExpend()));
        data.setMateWxJourBalance(
            StringValidater.toLong(req.getMateWxJourBalance()));
        data.setMateWxJourMonthIncome(
            StringValidater.toLong(req.getMateWxJourMonthIncome()));
        data.setMateWxJourMonthExpend(
            StringValidater.toLong(req.getMateWxJourMonthExpend()));
        data.setMateWxJourPic(req.getMateWxJourPic());
        data.setMateWxJourRemark(req.getMateWxJourRemark());

        data.setMateJourDatetimeStart(DateUtil.strToDate(
            req.getMateJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setMateJourDatetimeEnd(DateUtil.strToDate(
            req.getMateJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setMateJourInterest(req.getMateJourInterest());
        data.setMateInterest1(StringValidater.toLong(req.getMateInterest1()));
        data.setMateInterest2(StringValidater.toLong(req.getMateInterest2()));

        data.setMateJourIncome(StringValidater.toLong(req.getMateJourIncome()));
        data.setMateJourExpend(StringValidater.toLong(req.getMateJourExpend()));
        data.setMateJourBalance(
            StringValidater.toLong(req.getMateJourBalance()));
        data.setMateJourMonthIncome(
            StringValidater.toLong(req.getMateJourMonthIncome()));
        data.setMateJourMonthExpend(
            StringValidater.toLong(req.getMateJourMonthExpend()));

        data.setMateJourPic(req.getMateJourPic());
        data.setMateJourRemark(req.getMateJourRemark());
        data.setMateAssetPdf(req.getMateAssetPdf());

        data.setGuaName(req.getGuaName());
        data.setGuaMobile(req.getGuaMobile());
        data.setGuaIdNo(req.getGuaIdNo());
        data.setGuaPhone(req.getGuaPhone());
        data.setGuaCompanyName(req.getGuaCompanyName());

        data.setGuaCompanyAddress(req.getGuaCompanyAddress());
        data.setGuaHouseAssetAddress(req.getGuaHouseAssetAddress());

        data.setGuaZfbJourDatetimeStart(
            DateUtil.strToDate(req.getGuaZfbJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setGuaZfbJourDatetimeEnd(DateUtil.strToDate(
            req.getGuaZfbJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setGuaZfbJourInterest(req.getGuaZfbJourInterest());
        data.setGuaZfbInterest1(
            StringValidater.toLong(req.getGuaZfbInterest1()));
        data.setGuaZfbInterest2(
            StringValidater.toLong(req.getGuaZfbInterest2()));

        data.setGuaZfbJourIncome(
            StringValidater.toLong(req.getGuaZfbJourIncome()));
        data.setGuaZfbJourExpend(
            StringValidater.toLong(req.getGuaZfbJourExpend()));
        data.setGuaZfbJourBalance(
            StringValidater.toLong(req.getGuaZfbJourBalance()));
        data.setGuaZfbJourMonthIncome(
            StringValidater.toLong(req.getGuaZfbJourMonthIncome()));
        data.setGuaZfbJourMonthExpend(
            StringValidater.toLong(req.getGuaZfbJourMonthExpend()));

        data.setGuaZfbJourPic(req.getGuaZfbJourPic());
        data.setGuaZfbJourRemark(req.getGuaZfbJourRemark());

        data.setGuaWxJourDatetimeStart(
            DateUtil.strToDate(req.getGuaWxJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setGuaWxJourDatetimeEnd(DateUtil.strToDate(
            req.getGuaWxJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setGuaWxJourInterest(req.getGuaWxJourInterest());
        data.setGuaWxInterest1(StringValidater.toLong(req.getGuaWxInterest1()));
        data.setGuaWxInterest2(StringValidater.toLong(req.getGuaWxInterest2()));

        data.setGuaWxJourIncome(
            StringValidater.toLong(req.getGuaWxJourIncome()));
        data.setGuaWxJourExpend(
            StringValidater.toLong(req.getGuaWxJourExpend()));
        data.setGuaWxJourBalance(
            StringValidater.toLong(req.getGuaWxJourBalance()));
        data.setGuaWxJourMonthIncome(
            StringValidater.toLong(req.getGuaWxJourMonthIncome()));
        data.setGuaWxJourMonthExpend(
            StringValidater.toLong(req.getGuaWxJourMonthExpend()));

        data.setGuaWxJourPic(req.getGuaWxJourPic());
        data.setGuaWxJourRemark(req.getGuaWxJourRemark());

        data.setGuaJourDatetimeStart(DateUtil.strToDate(
            req.getGuaJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setGuaJourDatetimeEnd(DateUtil.strToDate(
            req.getGuaJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setGuaJourInterest(req.getGuaJourInterest());
        data.setGuaInterest1(StringValidater.toLong(req.getGuaInterest1()));
        data.setGuaInterest2(StringValidater.toLong(req.getGuaInterest2()));

        data.setGuaJourIncome(StringValidater.toLong(req.getGuaJourIncome()));
        data.setGuaJourExpend(StringValidater.toLong(req.getGuaJourExpend()));
        data.setGuaJourBalance(StringValidater.toLong(req.getGuaJourBalance()));
        data.setGuaJourMonthIncome(
            StringValidater.toLong(req.getGuaJourMonthIncome()));
        data.setGuaJourMonthExpend(
            StringValidater.toLong(req.getGuaJourMonthExpend()));

        data.setGuaJourPic(req.getGuaJourPic());
        data.setGuaJourRemark(req.getGuaJourRemark());
        data.setGuaAssetPdf(req.getGuaAssetPdf());

        data.setEmergencyName1(req.getEmergencyName1());
        data.setEmergencyRelation1(req.getEmergencyRelation1());
        data.setEmergencyMobile1(req.getEmergencyMobile1());

        data.setEmergencyName2(req.getEmergencyName2());
        data.setEmergencyRelation2(req.getEmergencyRelation2());
        data.setEmergencyMobile2(req.getEmergencyMobile2());
        data.setZfbJourDatetimeStart(DateUtil.strToDate(
            req.getZfbJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setZfbJourDatetimeEnd(DateUtil.strToDate(
            req.getZfbJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setZfbJourInterest(req.getZfbJourInterest());
        data.setZfbInterest1(StringValidater.toLong(req.getZfbInterest1()));
        data.setZfbInterest2(StringValidater.toLong(req.getZfbInterest2()));

        data.setZfbJourIncome(StringValidater.toLong(req.getZfbJourIncome()));
        data.setZfbJourExpend(StringValidater.toLong(req.getZfbJourExpend()));
        data.setZfbJourBalance(StringValidater.toLong(req.getZfbJourBalance()));
        data.setZfbJourMonthIncome(
            StringValidater.toLong(req.getZfbJourMonthIncome()));
        data.setZfbJourMonthExpend(
            StringValidater.toLong(req.getZfbJourMonthExpend()));

        data.setZfbJourPic(req.getZfbJourPic());
        data.setZfbJourRemark(req.getZfbJourRemark());
        data.setWxJourDatetimeStart(DateUtil.strToDate(
            req.getWxJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setWxJourDatetimeEnd(DateUtil.strToDate(req.getWxJourDatetimeEnd(),
            DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setWxJourInterest(req.getWxJourInterest());
        data.setWxInterest1(StringValidater.toLong(req.getWxInterest1()));
        data.setWxInterest2(StringValidater.toLong(req.getWxInterest2()));

        data.setWxJourIncome(StringValidater.toLong(req.getWxJourIncome()));
        data.setWxJourExpend(StringValidater.toLong(req.getWxJourExpend()));
        data.setWxJourBalance(StringValidater.toLong(req.getWxJourBalance()));
        data.setWxJourMonthIncome(
            StringValidater.toLong(req.getWxJourMonthIncome()));
        data.setWxJourMonthExpend(
            StringValidater.toLong(req.getWxJourMonthExpend()));

        data.setWxJourPic(req.getWxJourPic());
        data.setWxJourRemark(req.getWxJourRemark());
        data.setJourDatetimeStart(DateUtil.strToDate(req.getJourDatetimeStart(),
            DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setJourDatetimeEnd(DateUtil.strToDate(req.getJourDatetimeEnd(),
            DateUtil.FRONT_DATE_FORMAT_STRING));
        data.setJourInterest(req.getJourInterest());
        data.setInterest1(StringValidater.toLong(req.getInterest1()));
        data.setInterest2(StringValidater.toLong(req.getInterest2()));

        data.setJourIncome(StringValidater.toLong(req.getJourIncome()));
        data.setJourExpend(StringValidater.toLong(req.getJourExpend()));
        data.setJourBalance(StringValidater.toLong(req.getJourBalance()));
        data.setJourMonthIncome(
            StringValidater.toLong(req.getJourMonthIncome()));
        data.setJourMonthExpend(
            StringValidater.toLong(req.getJourMonthExpend()));

        data.setJourPic(req.getJourPic());
        data.setJourRemark(req.getJourRemark());
        data.setAssetPdf(req.getAssetPdf());
        data.setHouseContract(req.getHouseContract());
        data.setHousePicture(req.getHousePicture());
        data.setHkBookPdf(req.getHkBookPdf());
        data.setIdCardPdf(req.getIdCardPdf());
        data.setMarryPdf(req.getMarryPdf());
        data.setOtherPdf(req.getOtherPdf());
        data.setIsAdvanceFund(req.getIsAdvanceFund());

        // 当前节点
        EBudgetOrderNode node = EBudgetOrderNode.WRITE_BUDGET_ORDER;
        if (EDealType.SEND.getCode().equals(req.getDealType())) {
            // 下一个节点
            String nextNode = nodeFlowBO
                .getNodeFlowByCurrentNode(
                    EBudgetOrderNode.WRITE_BUDGET_ORDER.getCode())
                .getNextNode();
            node = EBudgetOrderNode.getMap().get(nextNode);
        }

        data.setCurNodeCode(node.getCode());
        budgetOrderBO.refreshBudgetOrder(data);

        // 日志记录
        sysBizLogBO.saveSYSBizLog(data.getCode(), EBizLogType.BUDGET_ORDER,
            data.getCode(), node.getCode(), node.getValue(), req.getOperator());
    }

    @Override
    public void areaApprove(String code, String approveResult,
            String approveNote, String operator) {

        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);

        if (!EBudgetOrderNode.AREA_APPROVE.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是区域经理审核节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            budgetOrder.setCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(preCurrentNode).getNextNode());
        } else {
            budgetOrder.setCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(preCurrentNode).getBackNode());
        }
        budgetOrder.setRemark(approveNote);
        budgetOrderBO.refreshAreaApprove(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);

    }

    @Override
    @Transactional
    public void riskApprove(String code, String approveResult,
            String approveNote, String operator) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);

        if (!EBudgetOrderNode.RISK_APPROVE.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是风控专员审核节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            budgetOrder
                .setCurNodeCode(
                    nodeFlowBO
                        .getNodeFlowByCurrentNode(
                            EBudgetOrderNode.RISK_APPROVE.getCode())
                        .getNextNode());
        } else {
            budgetOrder
                .setCurNodeCode(
                    nodeFlowBO
                        .getNodeFlowByCurrentNode(
                            EBudgetOrderNode.RISK_APPROVE.getCode())
                        .getBackNode());
        }
        budgetOrder.setRemark(approveNote);
        budgetOrderBO.refreshriskApprove(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void riskChargeApprove(String code, String operator,
            String approveResult, String approveNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);

        if (!EBudgetOrderNode.RISK_CHARGE_APPROVE.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是风控主管审核节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            budgetOrder.setCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(
                    EBudgetOrderNode.RISK_CHARGE_APPROVE.getCode())
                .getNextNode());
            // 生成 收回手续费
            BudgetOrderFee data = new BudgetOrderFee();

            data.setCompanyCode(budgetOrder.getCompanyCode());
            data.setUserId(budgetOrder.getSaleUserId());

            // 应收手续费=银行服务费+公证费+gps费+月供保证金+公司服务费+服务费
            data.setShouldAmount(budgetOrder.getBankFee()
                    + budgetOrder.getAuthFee() + budgetOrder.getGpsFee()
                    + budgetOrder.getMonthDeposit()
                    + budgetOrder.getCompanyFee() + budgetOrder.getTeamFee());
            data.setRealAmount(0L);
            data.setIsSettled(EBoolean.NO.getCode());

            data.setUpdater(operator);
            data.setUpdateDatetime(new Date());
            data.setBudgetOrder(code);
            budgetOrderFeeBO.saveBudgetOrderFee(data);

            // 征信单回写准入单编号
            Credit credit = creditBO.getCredit(budgetOrder.getCreditCode());
            credit.setBudgetCode(budgetOrder.getCode());
            creditBO.refreshCredit(credit);

            LoanProduct loanProduct = loanProductBO
                .getLoanProduct(budgetOrder.getLoanProductCode());
            if (StringUtils.isNotBlank(budgetOrder.getTeamCode())) {
                // 生成返点支付数据
                Repoint repoint = new Repoint();
                // 应返金额=准入单的贷款金额*准入单的贷款产品的返点比例
                Long loanAmount = budgetOrder.getLoanAmount();
                BizTeam bizTeam = bizTeamBO
                    .getBizTeam(budgetOrder.getTeamCode());
                repoint.setTeamCode(budgetOrder.getTeamCode());
                repoint.setCaptain(bizTeam.getCaptain());
                repoint.setBizCode(budgetOrder.getCode());
                repoint.setAccountNo(bizTeam.getAccountNo());

                repoint.setBank(bizTeam.getBank());
                repoint.setSubbranch(bizTeam.getSubbranch());
                double backRate = loanProduct.getBackRate();
                long shouldAmount = AmountUtil.mul(loanAmount, backRate);
                repoint.setShouldAmount(shouldAmount);

                repoint.setStatus(ERepointStatus.TODO.getCode());
                repoint.setUpdater(operator);
                repoint.setUpdateDatetime(new Date());
                repointBO.saveRepoint(repoint);

                // 生成调查报告
                InvestigateReport investigateReport = new InvestigateReport();
                investigateReport.setBudgetOrderCode(budgetOrder.getCode());
                investigateReport
                    .setRepayBizCode(budgetOrder.getRepayBizCode());
                investigateReport.setCompanyCode(budgetOrder.getCompanyCode());
                investigateReport.setBizType(budgetOrder.getBizType());
                investigateReport
                    .setApplyUserName(budgetOrder.getApplyUserName());
                investigateReport.setApplyDatetime(new Date());
                investigateReport.setLoanBank(budgetOrder.getLoanBank());
                investigateReport.setLoanAmount(budgetOrder.getLoanAmount());
                investigateReport.setLoanPeriod(budgetOrder.getLoanPeriod());
                investigateReport
                    .setIsAdvanceFund(budgetOrder.getIsAdvanceFund());
                investigateReport.setSaleUserId(budgetOrder.getSaleUserId());
                String gender = budgetOrder.getGender();
                if (gender == "1") {
                    gender = "男";
                } else {
                    gender = "女";
                }
                String education = budgetOrder.getEducation();
                if (education == "1") {
                    education = "博士及以上";
                } else if (education == "2") {
                    education = "硕士";
                } else if (education == "3") {
                    education = "大学本科";
                } else if (education == "4") {
                    education = "大学专科";
                } else {
                    education = "高中及以下";
                }
                String marryState = budgetOrder.getMarryState();
                if (marryState == "1") {
                    marryState = "未婚";
                } else if (marryState == "2") {
                    marryState = "已婚";
                } else if (marryState == "3") {
                    marryState = "离异";
                } else {
                    marryState = "丧偶";
                }
                String customerInformation = "借款人:"
                        + budgetOrder.getApplyUserName() + ", "
                        + budgetOrder.getAge() + "岁, " + marryState + ", "
                        + "性别：" + gender + ", " + "学历：" + education + ", "
                        + "民族：" + budgetOrder.getNation() + ", " + "身份证号："
                        + budgetOrder.getIdNo() + ", " + "政治面貌："
                        + budgetOrder.getPolitical() + ", " + "户口所在地："
                        + budgetOrder.getResidenceAddress() + ", " + "现在家庭住址："
                        + budgetOrder.getNowAddress() + ", " + "联系电话："
                        + budgetOrder.getMobile() + ", " + "家有"
                        + budgetOrder.getFamilyNumber() + "口人，" + "邮编："
                        + budgetOrder.getPostCode1() + ",   " + "借款人无重大疾病，身体健康";
                investigateReport.setCustomerInformation(customerInformation);
                CreditUser domain = creditUserBO.getCreditUserByCreditCode(
                    budgetOrder.getCreditCode(), ELoanRole.APPLY_USER);
                investigateReport
                    .setBankCreditResultPdf(domain.getBankCreditResultPdf());
                investigateReport
                    .setJourDatetimeStart(budgetOrder.getJourDatetimeStart());
                investigateReport
                    .setZfbJourDatetimeEnd(budgetOrder.getZfbJourDatetimeEnd());

                investigateReport
                    .setZfbJourIncome(budgetOrder.getZfbJourIncome());
                investigateReport
                    .setZfbJourExpend(budgetOrder.getZfbJourExpend());
                investigateReport
                    .setZfbJourBalance(budgetOrder.getZfbJourBalance());
                investigateReport
                    .setZfbJourMonthIncome(budgetOrder.getZfbJourMonthIncome());
                investigateReport
                    .setZfbJourMonthExpend(budgetOrder.getZfbJourMonthExpend());
                investigateReport
                    .setZfbJourRemark(budgetOrder.getZfbJourRemark());

                investigateReport.setWxJourDatetimeStart(
                    budgetOrder.getWxJourDatetimeStart());
                investigateReport
                    .setWxJourDatetimeEnd(budgetOrder.getWxJourDatetimeEnd());
                investigateReport
                    .setWxJourIncome(budgetOrder.getWxJourIncome());
                investigateReport
                    .setWxJourExpend(budgetOrder.getWxJourExpend());
                investigateReport
                    .setWxJourBalance(budgetOrder.getWxJourBalance());
                investigateReport
                    .setWxJourMonthIncome(budgetOrder.getWxJourMonthIncome());
                investigateReport
                    .setWxJourMonthExpend(budgetOrder.getWxJourMonthExpend());
                investigateReport
                    .setWxJourRemark(budgetOrder.getWxJourRemark());

                investigateReport
                    .setJourDatetimeStart(budgetOrder.getJourDatetimeStart());
                investigateReport
                    .setJourDatetimeEnd(budgetOrder.getJourDatetimeEnd());
                investigateReport.setJourIncome(budgetOrder.getJourIncome());
                investigateReport.setJourExpend(budgetOrder.getJourExpend());
                investigateReport.setJourBalance(budgetOrder.getJourBalance());
                investigateReport
                    .setJourMonthIncome(budgetOrder.getJourMonthIncome());
                investigateReport
                    .setJourMonthExpend(budgetOrder.getJourMonthExpend());
                investigateReport.setJourRemark(budgetOrder.getJourRemark());

                investigateReport
                    .setHouseContract(budgetOrder.getHouseContract());
                investigateReport
                    .setHousePicture(budgetOrder.getHousePicture());
                String basicsInformation = "品牌：" + budgetOrder.getCarBrand()
                        + "," + "车型：" + budgetOrder.getCarModel() + ","
                        + "新手指导价" + budgetOrder.getOriginalPrice() + ","
                        + "落户地点：" + budgetOrder.getSettleAddress();
                investigateReport.setBasicsInformation(basicsInformation);
                investigateReport.setCurNodeCode(
                    EInvestigateReportNode.COMMIT_APPLY.getCode());
                String irCode = investigateReportBO
                    .saveInvestigateReport(investigateReport);

                // 日志记录
                sysBizLogBO.saveSYSBizLog(irCode, EBizLogType.INVESTIGATEREPORT,
                    irCode, investigateReport.getCurNodeCode(), approveNote,
                    operator);
            }

        } else {
            budgetOrder.setCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(
                    EBudgetOrderNode.RISK_CHARGE_APPROVE.getCode())
                .getBackNode());
        }
        budgetOrder.setRemark(approveNote);
        budgetOrderBO.refreshriskChargeApprove(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void interview(XN632123Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder.setBankVideo(req.getBankVideo());
        budgetOrder.setBankPhoto(req.getBankPhoto());
        budgetOrder.setCompanyVideo(req.getCompanyVideo());
        budgetOrder.setCompanyContract(req.getCompanyContract());
        budgetOrder.setBankContract(req.getBankContract());
        budgetOrder.setAdvanceFundAmountPdf(req.getAdvanceFundAmountPdf());
        budgetOrder.setOtherVideo(req.getOtherVideo());
        budgetOrder.setInterviewOtherPdf(req.getInterviewOtherPdf());
        EBudgetOrderNode node = EBudgetOrderNode.BIZ_CHARGE_APPROVE;
        budgetOrder.setCurNodeCode(node.getCode());
        budgetOrderBO.interview(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());
    }

    @Override
    @Transactional
    public void bizChargeApprove(String code, String operator,
            String approveResult, String approveNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.BIZ_CHARGE_APPROVE.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是业务总监审核节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        NodeFlow nodeFlow = nodeFlowBO.getNodeFlowByCurrentNode(
            EBudgetOrderNode.BIZ_CHARGE_APPROVE.getCode());
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            if (EBoolean.NO.getCode().equals(budgetOrder.getIsAdvanceFund())) {// 不垫资，直接跳到gps
                budgetOrder.setCurNodeCode(EBudgetOrderNode.GPSAZ.getCode());
            } else {
                budgetOrder.setCurNodeCode(nodeFlowBO
                    .getNodeFlowByCurrentNode(
                        EBudgetOrderNode.BIZ_CHARGE_APPROVE.getCode())
                    .getNextNode());
            }
        } else {
            budgetOrder.setCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(
                    EBudgetOrderNode.BIZ_CHARGE_APPROVE.getCode())
                .getBackNode());
            budgetOrder.setCurNodeCode(nodeFlow.getBackNode());
        }

        budgetOrder.setRemark(approveNote);
        budgetOrderBO.refreshbizChargeApprove(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void advanceFund(XN632125Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        budgetOrder.setAdvanceFundDatetime(DateUtil.strToDate(
            req.getAdvanceFundDatetime(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setAdvanceFundAmount(
            StringValidater.toLong(req.getAdvanceFundAmount()));
        budgetOrder.setBillPdf(req.getBillPdf());

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();

        // 下个节点设置
        budgetOrder.setCurNodeCode(nodeFlowBO
            .getNodeFlowByCurrentNode(EBudgetOrderNode.ADVANCEFUND.getCode())
            .getNextNode());
        budgetOrderBO.advancefund(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());
    }

    @Override
    @Transactional
    public void installGps(String code, String operator,
            List<XN632126ReqGps> gpsAzList) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();

        // 删除
        budgetOrderGpsBO.removeBudgetOrderGpsList(code);
        // 添加
        budgetOrderGpsBO.saveBudgetOrderGpsList(code, gpsAzList);

        // 下个节点设置
        budgetOrder.setCurNodeCode(nodeFlowBO
            .getNodeFlowByCurrentNode(EBudgetOrderNode.GPSAZ.getCode())
            .getNextNode());
        budgetOrderBO.installGps(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void gpsManagerApprove(String code, String operator,
            String approveResult, String approveNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.GPSMANAGERAPPROVE.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是GPS管理员审核节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(approveResult)) {
            budgetOrder.setCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(
                    EBudgetOrderNode.GPSMANAGERAPPROVE.getCode())
                .getNextNode());

            // 更新gps使用状态
            List<BudgetOrderGps> gpslist = budgetOrderGpsBO
                .queryBudgetOrderGpsList(budgetOrder.getCode());
            for (BudgetOrderGps budgetOrderGps : gpslist) {
                Gps gps = gpsBO.getGps(budgetOrderGps.getCode());
                gpsBO.refreshUseGps(gps.getCode(), budgetOrder.getCode());
            }
        } else {
            budgetOrder.setCurNodeCode(nodeFlowBO
                .getNodeFlowByCurrentNode(
                    EBudgetOrderNode.GPSMANAGERAPPROVE.getCode())
                .getBackNode());
        }
        budgetOrder.setRemark(approveNote);
        budgetOrderBO.refreshGpsManagerApprove(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void carSettle(XN632128Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();

        budgetOrder.setCarSettleDatetime(DateUtil.strToDate(
            req.getCarSettleDatetime(), DateUtil.FRONT_DATE_FORMAT_STRING));
        // budgetOrder.setCarNumber(req.getCarNumber());
        budgetOrder.setCarInvoice(req.getCarInvoice());
        budgetOrder.setCarJqx(req.getCarJqx());
        budgetOrder.setCarSyx(req.getCarSyx());
        budgetOrder.setCarSettleOtherPdf(req.getCarSettleOtherPdf());

        NodeFlow nodeFlow = nodeFlowBO
            .getNodeFlowByCurrentNode(EBudgetOrderNode.CARSETTLE.getCode());
        budgetOrder.setCurNodeCode(nodeFlow.getNextNode());

        // 车辆信息落户
        budgetOrderBO.carSettle(budgetOrder);
        // 获取参考材料
        String fileList = nodeFlow.getFileList();
        if (StringUtils.isNotBlank(fileList)) {
            logisticsBO.saveLogistics(ELogisticsType.BUDGET.getCode(),
                budgetOrder.getCode(), budgetOrder.getSaleUserId(),
                EBudgetOrderNode.CARSETTLE.getCode(), nodeFlow.getNextNode(),
                fileList);
        } else {
            throw new BizException("xn0000", "当前节点材料清单不存在");
        }

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());

    }

    @Override
    @Transactional
    public void commitBank(String code, String operator,
            String bankCommitDatetime, String bankCommitNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);

        if (!EBudgetOrderNode.COMMITBANK.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是确认提交银行节点，不能操作");
        }
        if (EBudgetFrozenStatus.FROZEN.getCode()
            .equals(budgetOrder.getFrozenStatus())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前预算单已被冻结，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder.setCurNodeCode(nodeFlowBO
            .getNodeFlowByCurrentNode(EBudgetOrderNode.COMMITBANK.getCode())
            .getNextNode());
        budgetOrder.setBankCommitDatetime(DateUtil.strToDate(bankCommitDatetime,
            DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setRemark(bankCommitNote);
        budgetOrderBO.refreshCommitBank(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void confirmLoan(XN632130Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        if (!EBudgetOrderNode.CONFIRMLOAN.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是确认放款节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder.setReceiptBankCode(req.getReceiptBankCode());
        budgetOrder.setReceiptBankName(req.getReceiptBankName());
        budgetOrder.setReceiptBankcardNumber(req.getReceiptBankcardNumber());
        budgetOrder.setReceiptPdf(req.getReceiptPdf());
        budgetOrder.setReceiptRemark(req.getReceiptRemark());
        budgetOrder.setCurNodeCode(nodeFlowBO
            .getNodeFlowByCurrentNode(EBudgetOrderNode.CONFIRMLOAN.getCode())
            .getNextNode());
        budgetOrderBO.refreshConfirmReceipt(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());

        // 银行已放款待财务退款 生成退客户垫资款数据
        if (EIsAdvanceFund.YES.getCode()
            .equals(budgetOrder.getIsAdvanceFund())) {
            budgetOrder
                .setBackAdvanceStatus(EBackAdvanceStatus.NONEED_BACK.getCode());

        }
        if (EIsAdvanceFund.NO.getCode()
            .equals(budgetOrder.getIsAdvanceFund())) {
            budgetOrder
                .setBackAdvanceStatus(EBackAdvanceStatus.TODO_BACK.getCode());
            // 日志记录
            sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
                EBizLogType.BACK_ADVANCE_FUND, budgetOrder.getCode(),
                EBackAdvanceStatus.TODO_BACK.getCode(),
                EBackAdvanceStatus.TODO_BACK.getValue(), req.getOperator());

        }
        budgetOrderBO.saveBackAdvanceFund(budgetOrder);

    }

    @Override
    @Transactional
    public void entryFk(XN632135Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        if (!EBudgetOrderNode.ENTRYLOAN.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是录入放款信息节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder.setRepayBankcardNumber(req.getRepayBankcardNumber());
        budgetOrder.setRepayBillDate(
            StringValidater.toInteger(req.getRepayBillDate()));
        budgetOrder.setRepayBankDate(
            StringValidater.toInteger(req.getRepayBankDate()));
        budgetOrder.setRepayCompanyDate(DateUtil.strToDate(
            req.getRepayCompanyDate(), DateUtil.FRONT_DATE_FORMAT_STRING));

        budgetOrder.setRepayFirstMonthDatetime(
            DateUtil.strToDate(req.getRepayFirstMonthDatetime(),
                DateUtil.FRONT_DATE_FORMAT_STRING));

        Date tomorrowDate = DateUtil.getTomorrowStart(new Date());
        if (tomorrowDate
            .compareTo(budgetOrder.getRepayFirstMonthDatetime()) > 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "首次还款日期请从明天开始算起");
        }

        budgetOrder.setRepayFirstMonthAmount(
            StringValidater.toLong(req.getRepayFirstMonthAmount()));
        budgetOrder.setRepayMonthAmount(
            StringValidater.toLong(req.getRepayMonthAmount()));

        budgetOrder.setBankFkDatetime(DateUtil.strToDate(req.getBankFkDate(),
            DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setCurNodeCode(nodeFlowBO
            .getNodeFlowByCurrentNode(EBudgetOrderNode.ENTRYLOAN.getCode())
            .getNextNode());
        budgetOrderBO.refreshEntryFk(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());
    }

    @Override
    @Transactional
    public void entryMortgage(String code, String operator,
            String pledgeDatetime, String greenBigSmj) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.ENTRYMORTGAGE.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是录入抵押信息节点，不能操作");
        }
        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        NodeFlow currentNodeFlow = nodeFlowBO
            .getNodeFlowByCurrentNode(preCurrentNode);
        budgetOrder.setCurNodeCode(currentNodeFlow.getNextNode());
        budgetOrder.setPledgeDatetime(DateUtil.strToDate(pledgeDatetime,
            DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setGreenBigSmj(greenBigSmj);
        budgetOrderBO.entryMortgage(budgetOrder);

        // 获取参考材料
        budgetOrder.setCurNodeCode(currentNodeFlow.getNextNode());
        String fileList = currentNodeFlow.getFileList();
        if (StringUtils.isNotBlank(fileList)) {
            logisticsBO.saveLogistics(ELogisticsType.BUDGET.getCode(),
                budgetOrder.getCode(), operator, preCurrentNode,
                currentNodeFlow.getNextNode(), fileList);
        } else {
            throw new BizException("xn0000", "当前节点材料清单不存在");
        }

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void mortgageCommitBank(String code, String operator,
            String pledgeBankCommitDatetime, String pledgeBankCommitNote) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.ENTRYCOMMITBANK.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是确认提交银行节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder
            .setCurNodeCode(
                nodeFlowBO
                    .getNodeFlowByCurrentNode(
                        EBudgetOrderNode.ENTRYCOMMITBANK.getCode())
                    .getNextNode());
        budgetOrder.setPledgeBankCommitDatetime(DateUtil.strToDate(
            pledgeBankCommitDatetime, DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setRemark(pledgeBankCommitNote);
        budgetOrderBO.refreshMortgageCommitBank(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    @Override
    @Transactional
    public void mortgageFinish(XN632133Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        if (!EBudgetOrderNode.MORTGAGEFINISH.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是抵押完成节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        budgetOrder.setCurNodeCode(nodeFlowBO
            .getNodeFlowByCurrentNode(EBudgetOrderNode.MORTGAGEFINISH.getCode())
            .getNextNode());
        budgetOrder.setCarNumber(req.getCarNumber());
        budgetOrder.setCarRegcerti(req.getCarRegcerti());
        budgetOrder.setCarPd(req.getCarPd());
        budgetOrder.setCarKey(req.getCarKey());
        budgetOrder.setCarBigSmj(req.getCarBigSmj());
        budgetOrderBO.refreshMortgageFinish(budgetOrder);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), req.getOperator());
    }

    @Override
    @Transactional
    public void archive(String code, String operator, String enterLocation) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        if (!EBudgetOrderNode.ARCHIVE.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是入档节点，不能操作");
        }

        // 之前节点
        String preCurrentNode = budgetOrder.getCurNodeCode();
        // 归档

        /****** 生成还款业务 ******/
        // 检查用户是否已经注册过
        User user = userBO.getUser(budgetOrder.getMobile(),
            EUserKind.Customer.getCode());
        String userId = null;
        if (user == null) {
            // 用户代注册并实名认证
            userId = userBO.doRegisterAndIdentify(budgetOrder.getMobile(),
                budgetOrder.getIdKind(), budgetOrder.getApplyUserName(),
                budgetOrder.getIdNo());
            distributeAccount(userId, budgetOrder.getMobile(),
                EUserKind.Customer.getCode());
        } else {
            userId = user.getUserId();
        }

        // 绑定用户银行卡
        String bankcardCode = bankcardBO.bind(userId,
            budgetOrder.getApplyUserName(),
            budgetOrder.getRepayBankcardNumber(),
            budgetOrder.getRepayBankCode(), budgetOrder.getRepayBankName(),
            budgetOrder.getRepaySubbranch());

        // 自动生成还款业务
        RepayBiz repayBiz = repayBizBO.generateCarLoanRepayBiz(budgetOrder,
            userId, bankcardCode, operator);

        // 自动生成还款计划
        repayPlanBO.genereateNewRepayPlan(repayBiz);

        // 归档完成，更新预算单信息
        String repayBizCode = repayBiz.getCode();
        budgetOrder.setCurNodeCode(EBudgetOrderNode.ARCHIVE_END.getCode());
        budgetOrderBO.archiveSuccess(budgetOrder, repayBizCode, userId);

        // 日志记录
        EBudgetOrderNode currentNode = EBudgetOrderNode.getMap()
            .get(budgetOrder.getCurNodeCode());
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_ORDER, budgetOrder.getCode(), preCurrentNode,
            currentNode.getCode(), currentNode.getValue(), operator);
    }

    // 分配账号
    private void distributeAccount(String userId, String mobile, String kind) {
        List<String> currencyList = new ArrayList<String>();
        currencyList.add(ECurrency.CNY.getCode());
        currencyList.add(ECurrency.JF.getCode());
        currencyList.add(ECurrency.XYF.getCode());

        for (String currency : currencyList) {
            accountBO.distributeAccount(userId, mobile,
                EAccountType.getAccountType(kind), currency);
        }
    }

    @Override
    public Paginable<BudgetOrder> queryBudgetOrderPage(int start, int limit,
            BudgetOrder condition) {
        Paginable<BudgetOrder> page = budgetOrderBO.getPaginable(start, limit,
            condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (BudgetOrder budgetOrder : page.getList()) {
                initBudgetOrder(budgetOrder);
            }
        }
        return page;
    }

    // 初始化预算单数据，包含公司名称
    private void initBudgetOrder(BudgetOrder budgetOrder) {
        // 业务公司名称
        if (StringUtils.isNotBlank(budgetOrder.getCompanyCode())) {
            Department department = departmentBO
                .getDepartment(budgetOrder.getCompanyCode());
            budgetOrder.setCompanyName(department.getName());
        }

        // 业务员姓名
        if (StringUtils.isNotBlank(budgetOrder.getSaleUserId())) {
            SYSUser sysUser = sysUserBO.getUser(budgetOrder.getSaleUserId());
            budgetOrder.setSaleUserName(sysUser.getRealName());
        }

        // 贷款银行
        if (StringUtils.isNotBlank(budgetOrder.getLoanBank())) {
            Bank loanBank = bankBO.getBank(budgetOrder.getLoanBank());
            budgetOrder.setLoanBankName(loanBank.getBankName());
        }

        // 当时团队
        if (StringUtils.isNotBlank(budgetOrder.getTeamCode())) {
            BizTeam bizTeam = bizTeamBO.getBizTeam(budgetOrder.getTeamCode());
            budgetOrder.setTeamName(bizTeam.getName());
        }
    }

    @Override
    public BudgetOrder getBudgetOrder(String code) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        initBudgetOrder(budgetOrder);
        List<BudgetOrderGps> budgetOrderGpsList = budgetOrderGpsBO
            .queryBudgetOrderGpsList(code);
        budgetOrder.setBudgetOrderGpsList(budgetOrderGpsList);
        return budgetOrder;
    }

    @Override
    public Paginable<BudgetOrder> queryBudgetOrderPageByRoleCode(int start,
            int limit, BudgetOrder condition) {
        if (ESysRole.getMap().get(condition.getRoleCode()) == null) {
            condition.setTeamCode(null);
        }

        Paginable<BudgetOrder> page = budgetOrderBO
            .getPaginableByRoleCode(start, limit, condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (BudgetOrder budgetOrder : page.getList()) {
                initBudgetOrder(budgetOrder);
            }
        }
        return page;
    }

    @Override
    public BudgetOrder getMoreBudget(String code) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(code);
        initBudgetOrder(budgetOrder);

        List<BudgetOrderGps> budgetOrderGpsList = budgetOrderGpsBO
            .queryBudgetOrderGpsList(code);
        budgetOrder.setBudgetOrderGpsList(budgetOrderGpsList);

        Credit credit = creditBO.getCredit(budgetOrder.getCreditCode());
        CreditUser creditUser = new CreditUser();
        creditUser.setCreditCode(credit.getCode());
        List<CreditUser> queryCreditUserList = creditUserBO
            .queryCreditUserList(creditUser);
        credit.setCreditUserList(queryCreditUserList);

        SYSUser user = sysUserBO.getUser(budgetOrder.getSaleUserId());
        budgetOrder.setRoleCode(user.getRoleCode());
        budgetOrder.setCredit(credit);
        return budgetOrder;
    }

    @Override
    public void confirmBackAdvanceFund(XN632180Req req) {

        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        budgetOrder.setBackAdvanceAmount(req.getBackAdvanceAmount());
        budgetOrder.setBackAdvanceAccount(req.getBackAdvanceAccount());
        budgetOrder.setBackAdvanceOpenBank(req.getBackAdvanceOpenBank());
        budgetOrder.setBackAdvanceSubbranch(req.getBackAdvanceSubbranch());
        budgetOrder.setBackAdvanceWaterBill(req.getBackAdvanceWaterBill());

        budgetOrderBO.confirmBackAdvanceFund(budgetOrder);

        // 生成日志
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BACK_ADVANCE_FUND, budgetOrder.getCode(),
            EBackAdvanceStatus.TODO_BACK.getCode(),
            EBackAdvanceStatus.HANDLED_BACK.getCode(),
            EBackAdvanceStatus.HANDLED_BACK.getValue(), req.getOperator());

    }

    @Override
    public void applyCancel(XN632190Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        if (EBudgetOrderNode.COMMITBANK.getCode()
            .compareTo(budgetOrder.getCurNodeCode()) <= 0) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点已经过提交银行节点，不能申请作废！");
        }
        budgetOrder.setRemark(req.getRemark());
        budgetOrder.setFrozenStatus(EBudgetFrozenStatus.FROZEN.getCode());
        budgetOrder.setCancelNodeCode(budgetOrder.getCurNodeCode());
        // 节点
        EBudgetOrderNode currentNode = EBudgetOrderNode.CANCEL_START;
        String nextNode = nodeFlowBO
            .getNodeFlowByCurrentNode(currentNode.getCode()).getNextNode();
        currentNode = EBudgetOrderNode.getMap().get(nextNode);
        budgetOrder.setCurNodeCode(currentNode.getCode());

        budgetOrderBO.applyCancel(budgetOrder);

        // 写日志
        sysBizLogBO.saveSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_CANCEL, budgetOrder.getCode(),
            currentNode.getCode(), currentNode.getValue(), req.getOperator());
    }

    @Override
    public void cancelBizAudit(XN632191Req req) {

        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        if (!EBudgetOrderNode.CANCEL_BIZ_AUDIT.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是业务总监审核节点，不能操作");
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(req.getApproveResult())) {
            // 判断是否已垫资 如果已经垫资 下一个节点是财务审核节点 未垫资 下一个节点时废流程结束节点
            if (null == budgetOrder.getAdvanceFundAmount()
                    && null == budgetOrder.getAdvanceFundDatetime()) {// 没垫资情况
                budgetOrder
                    .setCurNodeCode(EBudgetOrderNode.CANCEL_END.getCode());
                budgetOrder
                    .setFrozenStatus(EBudgetFrozenStatus.NORMAL.getCode());
            } else {// 垫资情况
                String currentNode = nodeFlowBO
                    .getNodeFlowByCurrentNode(budgetOrder.getCurNodeCode())
                    .getNextNode();
                budgetOrder.setCurNodeCode(currentNode);
            }

        } else if (EApproveResult.NOT_PASS.getCode()
            .equals(req.getApproveResult())) {
            budgetOrder.setCurNodeCode(budgetOrder.getCancelNodeCode());
            budgetOrder.setFrozenStatus(EBudgetFrozenStatus.NORMAL.getCode());
        }

        budgetOrderBO.cancelBizAudit(budgetOrder);
        // 写日志
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_CANCEL, budgetOrder.getCode(), preCurrentNode,
            budgetOrder.getCurNodeCode(), req.getApproveNote(),
            req.getOperator());
    }

    @Override
    public void cancelFinanceAudit(XN632192Req req) {

        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());

        if (!EBudgetOrderNode.CANCEL_FINANCE_AUDIT.getCode()
            .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是财务总监审核节点，不能操作");
        }
        String preCurrentNode = budgetOrder.getCurNodeCode();
        if (EApproveResult.PASS.getCode().equals(req.getApproveResult())) {
            String currentNode = nodeFlowBO
                .getNodeFlowByCurrentNode(budgetOrder.getCurNodeCode())
                .getNextNode();
            budgetOrder.setCurNodeCode(currentNode);
            budgetOrder.setFrozenStatus(EBudgetFrozenStatus.NORMAL.getCode());

        } else if (EApproveResult.NOT_PASS.getCode()
            .equals(req.getApproveResult())) {
            budgetOrder.setCurNodeCode(budgetOrder.getCancelNodeCode());
            budgetOrder.setFrozenStatus(EBudgetFrozenStatus.NORMAL.getCode());
        }

        budgetOrderBO.cancelFinanceAudit(budgetOrder);
        // 写日志
        sysBizLogBO.saveNewAndPreEndSYSBizLog(budgetOrder.getCode(),
            EBizLogType.BUDGET_CANCEL, budgetOrder.getCode(), preCurrentNode,
            budgetOrder.getCurNodeCode(), req.getApproveNote(),
            req.getOperator());

    }

    @Override
    public Paginable<BudgetOrder> queryBudgetOrderPageByDz(int start, int limit,
            BudgetOrder condition) {
        return budgetOrderBO.getPaginable(start, limit, condition);
    }

    @Override
    @Transactional
    public void doSmsInterviewInform(String budgetOrderCode) {
        BudgetOrder data = budgetOrderBO.getBudgetOrder(budgetOrderCode);
        if (!EBudgetOrderNode.INTERVIEW.getCode().equals(data.getCurNodeCode())
                && !EBudgetOrderNode.AGAIN_INTERVIEW.getCode()
                    .equals(data.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点不是面签节点");
        }

        String roomCode = data.getCode().substring(data.getCode().length() - 7);
        smsOutBO.sendSmsOut(data.getMobile(),
            "您的车贷准入申请单正在面签，请您现在打开APP，点击\"我的\"->\"开始面签\"，输入房间号[" + roomCode
                    + "]，进入房间并完成面签。");
    }

    @Override
    public void dataSupplement(XN632141Req req) {
        BudgetOrder budgetOrder = budgetOrderBO.getBudgetOrder(req.getCode());
        if (!EBudgetOrderNode.COMMITBANK.getCode()
            .equals(budgetOrder.getCurNodeCode())
                && !EBudgetOrderNode.ENTRYLOAN.getCode()
                    .equals(budgetOrder.getCurNodeCode())
                && !EBudgetOrderNode.CONFIRMLOAN.getCode()
                    .equals(budgetOrder.getCurNodeCode())) {
            throw new BizException(EBizErrorCode.DEFAULT.getCode(),
                "当前节点为" + budgetOrder.getCurNodeCode() + ",不能补录！");
        }

        budgetOrder.setMateName(req.getMateName());
        budgetOrder.setMateMobile(req.getMateMobile());
        budgetOrder.setMateIdNo(req.getMateIdNo());
        budgetOrder.setMateEducation(req.getMateEducation());
        budgetOrder.setMateCompanyName(req.getMateCompanyName());

        budgetOrder.setMateCompanyAddress(req.getMateCompanyAddress());
        budgetOrder.setMateCompanyContactNo(req.getMateCompanyContactNo());
        budgetOrder.setMateZfbJourDatetimeStart(
            DateUtil.strToDate(req.getMateZfbJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setMateZfbJourDatetimeEnd(
            DateUtil.strToDate(req.getMateZfbJourDatetimeEnd(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setMateZfbJourInterest(req.getMateZfbJourInterest());

        budgetOrder.setMateZfbInterest1(
            StringValidater.toLong(req.getMateZfbInterest1()));
        budgetOrder.setMateZfbInterest2(
            StringValidater.toLong(req.getMateZfbInterest2()));
        budgetOrder.setMateZfbJourIncome(
            StringValidater.toLong(req.getMateZfbJourIncome()));
        budgetOrder.setMateZfbJourExpend(
            StringValidater.toLong(req.getMateZfbJourExpend()));
        budgetOrder.setMateZfbJourBalance(
            StringValidater.toLong(req.getMateZfbJourBalance()));

        budgetOrder.setMateZfbJourMonthIncome(
            StringValidater.toLong(req.getMateZfbJourMonthIncome()));
        budgetOrder.setMateZfbJourMonthExpend(
            StringValidater.toLong(req.getMateZfbJourMonthExpend()));
        budgetOrder.setMateZfbJourPic(req.getMateZfbJourPic());
        budgetOrder.setMateZfbJourRemark(req.getMateZfbJourRemark());
        budgetOrder.setMateWxJourDatetimeStart(
            DateUtil.strToDate(req.getMateWxJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));

        budgetOrder.setMateWxJourDatetimeEnd(DateUtil.strToDate(
            req.getMateWxJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setMateWxJourInterest(req.getMateWxJourInterest());
        budgetOrder.setMateWxInterest1(
            StringValidater.toLong(req.getMateWxInterest1()));
        budgetOrder.setMateWxInterest2(
            StringValidater.toLong(req.getMateWxInterest2()));
        budgetOrder.setMateWxJourIncome(
            StringValidater.toLong(req.getMateWxJourIncome()));

        budgetOrder.setMateWxJourExpend(
            StringValidater.toLong(req.getMateWxJourExpend()));
        budgetOrder.setMateWxJourBalance(
            StringValidater.toLong(req.getMateWxJourBalance()));
        budgetOrder.setMateWxJourMonthIncome(
            StringValidater.toLong(req.getMateWxJourMonthIncome()));
        budgetOrder.setMateWxJourMonthExpend(
            StringValidater.toLong(req.getMateWxJourMonthExpend()));
        budgetOrder.setMateWxJourPic(req.getMateWxJourPic());

        budgetOrder.setMateWxJourRemark(req.getMateWxJourRemark());
        budgetOrder.setMateJourDatetimeStart(DateUtil.strToDate(
            req.getMateJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setMateJourDatetimeEnd(DateUtil.strToDate(
            req.getMateJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setMateJourInterest(req.getMateJourInterest());
        budgetOrder
            .setMateInterest1(StringValidater.toLong(req.getMateInterest1()));

        budgetOrder
            .setMateInterest2(StringValidater.toLong(req.getMateInterest2()));
        budgetOrder
            .setMateJourIncome(StringValidater.toLong(req.getMateJourIncome()));
        budgetOrder
            .setMateJourExpend(StringValidater.toLong(req.getMateJourExpend()));
        budgetOrder.setMateJourBalance(
            StringValidater.toLong(req.getMateJourBalance()));
        budgetOrder.setMateJourMonthIncome(
            StringValidater.toLong(req.getMateJourMonthIncome()));

        budgetOrder.setMateJourMonthExpend(
            StringValidater.toLong(req.getMateJourMonthExpend()));
        budgetOrder.setMateJourPic(req.getMateJourPic());
        budgetOrder.setMateJourRemark(req.getMateJourRemark());
        budgetOrder.setMateAssetPdf(req.getMateAssetPdf());
        budgetOrder.setGuaName(req.getGuaName());

        budgetOrder.setGuaMobile(req.getGuaMobile());
        budgetOrder.setGuaIdNo(req.getGuaIdNo());
        budgetOrder.setGuaPhone(req.getGuaPhone());
        budgetOrder.setGuaCompanyName(req.getGuaCompanyName());
        budgetOrder.setGuaCompanyAddress(req.getGuaCompanyAddress());

        budgetOrder.setGuaHouseAssetAddress(req.getGuaHouseAssetAddress());
        budgetOrder.setGuaZfbJourDatetimeStart(
            DateUtil.strToDate(req.getGuaZfbJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setGuaZfbJourDatetimeEnd(DateUtil.strToDate(
            req.getGuaZfbJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setGuaZfbJourInterest(req.getGuaZfbJourInterest());
        budgetOrder.setGuaZfbInterest1(
            StringValidater.toLong(req.getGuaZfbInterest1()));

        budgetOrder.setGuaZfbInterest2(
            StringValidater.toLong(req.getGuaZfbInterest2()));
        budgetOrder.setGuaZfbJourIncome(
            StringValidater.toLong(req.getGuaZfbJourIncome()));
        budgetOrder.setGuaZfbJourExpend(
            StringValidater.toLong(req.getGuaZfbJourExpend()));
        budgetOrder.setGuaZfbJourBalance(
            StringValidater.toLong(req.getGuaZfbJourBalance()));
        budgetOrder.setGuaZfbJourMonthIncome(
            StringValidater.toLong(req.getGuaZfbJourMonthIncome()));

        budgetOrder.setGuaZfbJourMonthExpend(
            StringValidater.toLong(req.getGuaZfbJourMonthExpend()));
        budgetOrder.setGuaZfbJourPic(req.getGuaZfbJourPic());
        budgetOrder.setGuaZfbJourRemark(req.getGuaZfbJourRemark());
        budgetOrder.setGuaWxJourDatetimeStart(
            DateUtil.strToDate(req.getGuaWxJourDatetimeStart(),
                DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setGuaWxJourDatetimeEnd(DateUtil.strToDate(
            req.getGuaWxJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));

        budgetOrder.setGuaWxJourInterest(req.getGuaWxJourInterest());
        budgetOrder
            .setGuaWxInterest1(StringValidater.toLong(req.getGuaWxInterest1()));
        budgetOrder
            .setGuaWxInterest2(StringValidater.toLong(req.getGuaWxInterest2()));
        budgetOrder.setGuaWxJourIncome(
            StringValidater.toLong(req.getGuaWxJourIncome()));
        budgetOrder.setGuaWxJourExpend(
            StringValidater.toLong(req.getGuaWxJourExpend()));

        budgetOrder.setGuaWxJourBalance(
            StringValidater.toLong(req.getGuaWxJourBalance()));
        budgetOrder.setGuaWxJourMonthIncome(
            StringValidater.toLong(req.getGuaWxJourMonthIncome()));
        budgetOrder.setGuaWxJourMonthExpend(
            StringValidater.toLong(req.getGuaWxJourMonthExpend()));
        budgetOrder.setGuaWxJourPic(req.getGuaWxJourPic());
        budgetOrder.setGuaWxJourRemark(req.getGuaWxJourRemark());

        budgetOrder.setGuaJourDatetimeStart(DateUtil.strToDate(
            req.getGuaJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setGuaJourDatetimeEnd(DateUtil.strToDate(
            req.getGuaJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setGuaJourInterest(req.getGuaJourInterest());
        budgetOrder
            .setGuaInterest1(StringValidater.toLong(req.getGuaInterest1()));
        budgetOrder
            .setGuaInterest2(StringValidater.toLong(req.getGuaInterest2()));

        budgetOrder
            .setGuaJourIncome(StringValidater.toLong(req.getGuaJourIncome()));
        budgetOrder
            .setGuaJourExpend(StringValidater.toLong(req.getGuaJourExpend()));
        budgetOrder
            .setGuaJourBalance(StringValidater.toLong(req.getGuaJourBalance()));
        budgetOrder.setGuaJourMonthIncome(
            StringValidater.toLong(req.getGuaJourMonthIncome()));
        budgetOrder.setGuaJourMonthExpend(
            StringValidater.toLong(req.getGuaJourMonthExpend()));

        budgetOrder.setGuaJourPic(req.getGuaJourPic());
        budgetOrder.setGuaJourRemark(req.getGuaJourRemark());
        budgetOrder.setGuaAssetPdf(req.getGuaAssetPdf());
        budgetOrder.setZfbJourDatetimeStart(DateUtil.strToDate(
            req.getZfbJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setZfbJourDatetimeEnd(DateUtil.strToDate(
            req.getZfbJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));

        budgetOrder.setZfbJourInterest(req.getZfbJourInterest());
        budgetOrder
            .setZfbInterest1(StringValidater.toLong(req.getZfbInterest1()));
        budgetOrder
            .setZfbInterest2(StringValidater.toLong(req.getZfbInterest2()));
        budgetOrder.setZfbJourPic(req.getZfbJourPic());
        budgetOrder.setZfbJourRemark(req.getZfbJourRemark());

        budgetOrder.setWxJourDatetimeStart(DateUtil.strToDate(
            req.getWxJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setWxJourDatetimeEnd(DateUtil.strToDate(
            req.getWxJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setWxJourInterest(req.getWxJourInterest());
        budgetOrder
            .setWxInterest1(StringValidater.toLong(req.getWxInterest1()));
        budgetOrder
            .setWxInterest2(StringValidater.toLong(req.getWxInterest2()));

        budgetOrder.setWxJourPic(req.getWxJourPic());
        budgetOrder.setWxJourRemark(req.getWxJourRemark());
        budgetOrder.setJourDatetimeStart(DateUtil.strToDate(
            req.getJourDatetimeStart(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setJourDatetimeEnd(DateUtil.strToDate(
            req.getJourDatetimeEnd(), DateUtil.FRONT_DATE_FORMAT_STRING));
        budgetOrder.setJourInterest(req.getJourInterest());

        budgetOrder.setInterest1(StringValidater.toLong(req.getInterest1()));
        budgetOrder.setInterest2(StringValidater.toLong(req.getInterest2()));
        budgetOrder.setJourPic(req.getJourPic());
        budgetOrder.setJourRemark(req.getJourRemark());
        budgetOrderBO.dataSupplement(budgetOrder);
    }

}
