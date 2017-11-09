/**
 * @Title EthTransactionAOImpl.java 
 * @Package com.cdkj.coin.ao.impl 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年11月7日 下午8:33:42 
 * @version V1.0   
 */
package com.cdkj.coin.ao.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.coin.ao.IChargeAO;
import com.cdkj.coin.ao.IEthTransactionAO;
import com.cdkj.coin.bo.IAccountBO;
import com.cdkj.coin.bo.IChargeBO;
import com.cdkj.coin.bo.IEthAddressBO;
import com.cdkj.coin.bo.IEthCollectionBO;
import com.cdkj.coin.bo.IEthTransactionBO;
import com.cdkj.coin.bo.ISYSConfigBO;
import com.cdkj.coin.common.SysConstants;
import com.cdkj.coin.core.OrderNoGenerater;
import com.cdkj.coin.domain.Account;
import com.cdkj.coin.domain.Charge;
import com.cdkj.coin.domain.EthAddress;
import com.cdkj.coin.domain.EthCollection;
import com.cdkj.coin.enums.EBizType;
import com.cdkj.coin.enums.EChannelType;
import com.cdkj.coin.enums.ECurrency;
import com.cdkj.coin.enums.EEthAddressType;
import com.cdkj.coin.enums.EEthCollectionStatus;
import com.cdkj.coin.enums.ESystemAccount;
import com.cdkj.coin.eth.CtqEthTransaction;
import com.cdkj.coin.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2017年11月7日 下午8:33:42 
 * @history:
 */
@Service
public class EthTransactionAOImpl implements IEthTransactionAO {

    @Autowired
    private IChargeBO chargeBO;

    @Autowired
    private IChargeAO chargeAO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IEthAddressBO ethAddressBO;

    @Autowired
    private IEthTransactionBO ethTransactionBO;

    @Autowired
    private IEthCollectionBO ethCollectionBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Override
    @Transactional
    public void chargeNotice(CtqEthTransaction ctqEthTransaction) {
        EthAddress ethAddress = ethAddressBO.getEthAddress(EEthAddressType.X,
            ctqEthTransaction.getTo());
        if (ethAddress == null) {
            throw new BizException("xn6250000", "充值地址不存在");
        }
        Charge condition = new Charge();
        condition.setRefNo(ctqEthTransaction.getHash());
        if (chargeBO.getTotalCount(condition) > 0) {
            return;
        }
        Account account = accountBO.getAccountByUser(ethAddress.getUserId(),
            ECurrency.ETH.getCode());
        String payGroup = OrderNoGenerater.generate("PG");
        BigDecimal amount = new BigDecimal(ctqEthTransaction.getValue());
        // 充值订单落地
        String code = chargeBO.applyOrderOnline(account, payGroup,
            ctqEthTransaction.getHash(), EBizType.AJ_CHARGE, "ETH充值-来自地址："
                    + ctqEthTransaction.getFrom(), amount, EChannelType.ETH,
            "程序");
        // 账户加钱
        accountBO.changeAmount(account.getAccountNumber(), EChannelType.ETH,
            ctqEthTransaction.getHash(), payGroup, code, EBizType.AJ_CHARGE,
            "ETH充值-来自地址：" + ctqEthTransaction.getFrom(), amount);
        // 落地交易记录
        ethTransactionBO.saveEthTransaction(ctqEthTransaction);
    }

    @Override
    @Transactional
    public void collection(String fromAddress) {
        BigDecimal limit = sysConfigBO
            .getBigDecimalValue(SysConstants.COLLECTION_LIMIT);
        BigDecimal balance = ethAddressBO.getEthBalance(fromAddress);
        // 余额大于配置值时，进行归集
        if (balance.compareTo(limit) > 0 || balance.compareTo(limit) == 0) {
            // 获取地址信息
            EthAddress xEthAddress = ethAddressBO.getEthAddress(
                EEthAddressType.X, fromAddress);
            // 获取今日归集地址
            EthAddress wEthAddress = ethAddressBO.getWEthAddressToday();
            String toAddress = wEthAddress.getAddress();
            // 预估矿工费用
            BigDecimal gasPrice = ethTransactionBO.getGasPrice();
            BigDecimal gasUse = new BigDecimal(21000);
            BigDecimal txFee = gasPrice.multiply(gasUse);
            BigDecimal value = balance.subtract(txFee);
            // 归集广播
            String txHash = ethTransactionBO.broadcast(fromAddress,
                xEthAddress.getPassword(), toAddress, value);
            if (StringUtils.isBlank(txHash)) {
                throw new BizException("xn625000", "归集—交易广播失败");
            }
            // 归集记录落地
            ethCollectionBO.saveEthCollection(fromAddress, toAddress, value,
                txHash);
        }
    }

    @Override
    @Transactional
    public void collectionNotice(CtqEthTransaction ctqEthTransaction) {
        // 根据交易hash查询归集记录
        EthCollection collection = ethCollectionBO
            .getEthCollectionByTxHash(ctqEthTransaction.getHash());
        if (!EEthCollectionStatus.Broadcast.getCode().equals(
            collection.getStatus())) {
            throw new BizException("xn625000", "交易已处理，请勿重复处理");
        }
        // 归集订单状态更新
        BigDecimal gasPrice = new BigDecimal(ctqEthTransaction.getGasPrice());
        BigDecimal gasUse = new BigDecimal(ctqEthTransaction.getGas()
            .toString());
        BigDecimal txFee = gasPrice.multiply(gasUse);
        ethCollectionBO.colectionNotice(collection, txFee,
            ctqEthTransaction.getBlockCreateDatetime());
        // 平台冷钱包加钱
        accountBO.changeAmount(ESystemAccount.SYS_ACOUNT_TG_ETH.getCode(),
            EChannelType.ETH, ctqEthTransaction.getHash(), "", collection
                .getCode(), EBizType.AJ_COLLECTION,
            "归集-来自地址：" + collection.getFromAddress(), new BigDecimal(
                ctqEthTransaction.getValue()));
        // 平台盈亏账户记入矿工费
        accountBO.changeAmount(ESystemAccount.SYS_ACOUNT_ETH.getCode(),
            EChannelType.ETH, ctqEthTransaction.getHash(), "",
            collection.getCode(), EBizType.AJ_MFEE,
            "归集地址：" + collection.getFromAddress(), txFee.negate());
        // 落地交易记录
        ethTransactionBO.saveEthTransaction(ctqEthTransaction);
    }
}
