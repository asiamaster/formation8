package com.dili.formation8.service.impl;

import com.dili.formation8.dao.BizNumberMapper;
import com.dili.formation8.domain.BizNumber;
import com.dili.formation8.service.BizNumberService;
import com.dili.formation8.utils.DateUtils;
import com.dili.formation8.vo.SequenceNo;
import com.dili.utils.base.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-27 17:16:19.
 */
@Service
public class BizNumberServiceImpl extends BaseServiceImpl<BizNumber, Long> implements BizNumberService {

    private ConcurrentHashMap<BIZ_NUMBER_PREFIX, SequenceNo> bizNumberMap = new ConcurrentHashMap<BIZ_NUMBER_PREFIX, SequenceNo>();

    public BizNumberMapper getActualDao() {
        return (BizNumberMapper)getDao();
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public String getTransferOrderCode() {
        return getCode(BIZ_NUMBER_PREFIX.TRANSFER_ORDER_CODE);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public String getDepositOrderCode() {
        return getCode(BIZ_NUMBER_PREFIX.DEPOSIT_ORDER_CODE);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public String getProductOrderCode() {
        return getCode(BIZ_NUMBER_PREFIX.PRODUCT_ORDER_CODE);
    }

    private Long getBaseBizNumber(String dateStr) {
        Long baseOrderId = NumberUtils.toLong(dateStr) * 1000000;
        return baseOrderId;
    }

    private BizNumber getByType(String type){
        BizNumber bizNumber=new BizNumber();
        bizNumber.setType(type);
        List<BizNumber> list=getActualDao().select(bizNumber);
        if(list==null ||list.isEmpty()){
            return null;
        }
        if(list.size()>1){
            StringBuilder sb=new StringBuilder();
            sb.append("重复的类型:");
            sb.append(type);
            sb.append(",无法确定使用哪一个");
            throw new RuntimeException(sb.toString());
        }
        return list.get(0);
    }

    //    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
    private SequenceNo getSeqNoByNewTransactional(SequenceNo idSequence, String seqIdKey, Long startSeq){
        BizNumber bizNumber = this.getByType(seqIdKey);
        String dateStr = DateUtils.format("yyyyMMdd");
        Long baseOrderId = getBaseBizNumber(dateStr);
        if(startSeq !=null){
            if(startSeq > bizNumber.getValue()){
                idSequence.setStartSeq(startSeq);
            }
            if(idSequence.getStartSeq() > baseOrderId+1000000){
                throw new RuntimeException("当天业务编码分配数超过1000000,无法分配!");
            }
        }else{
            idSequence.setStartSeq(bizNumber.getValue());
        }
        idSequence.setFinishSeq(idSequence.getStartSeq() + idSequence.getStep());
        bizNumber.setValue(idSequence.getFinishSeq());
        this.update(bizNumber);
        return idSequence;
    }

    private String getCode(BIZ_NUMBER_PREFIX key) {
        String dateStr = DateUtils.format("yyyyMMdd");
        Long orderId = getNextSequenceId(key, null);
        //如果不是同天
        if (!dateStr.equals(StringUtils.substring("" + orderId, 0, 8))) {
            orderId = getNextSequenceId(key, getBaseBizNumber(dateStr));
        }
        return key.getPrefix() + orderId;
    }

    /**
     * 获取表唯一ID,
     *
     * @param key
     *            id类型
     * @param startSeq
     *            从指定SEQ开始(只订单编号获取时有用)
     * @return
     */
    private Long getNextSequenceId(BIZ_NUMBER_PREFIX key, Long startSeq) {
        Long seqId = getNextSeqId(key, startSeq);
        for (int i = 0; (seqId <= 0 && i < 5); i++) {// 失败后，最大重复5次获取
            bizNumberMap.remove(key);
            seqId = getNextSeqId(key, startSeq);
        }
        return seqId;
    }

    private Long getNextSeqId(BIZ_NUMBER_PREFIX key, Long startSeq) {
        SequenceNo idSequence = bizNumberMap.get(key);
        if (idSequence == null) {
            idSequence = new SequenceNo();
            bizNumberMap.putIfAbsent(key, idSequence);
            idSequence = bizNumberMap.get(key);
        }
        if (startSeq != null
                || idSequence.getStartSeq() >= idSequence.getFinishSeq()) {
            idSequence = getSeqNoByNewTransactional(idSequence,
                    key.toString(), startSeq);
        }
        return idSequence.next();
    }

    /**
     * 业务编号前缀枚举
     */
    private static enum BIZ_NUMBER_PREFIX {
        // 充值订单编码前缀
        DEPOSIT_ORDER_CODE("DO"),
        // 产品订单编码前缀
        PRODUCT_ORDER_CODE("PO"),
        //转帐订单编码前缀
        TRANSFER_ORDER_CODE("TO");

        private String prefix;

        private BIZ_NUMBER_PREFIX(String prefix) {
            this.setPrefix(prefix);
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

    }
}