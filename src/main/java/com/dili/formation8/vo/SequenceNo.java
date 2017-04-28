package com.dili.formation8.vo;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by asiam on 2017/4/27 0027.
 */
public class SequenceNo{
    private Long step =50L;//步长
    private AtomicLong startSeq =new AtomicLong(1);//开始ID
    private Long finishSeq =0L;//结束ID
    public synchronized Long next(){
        return startSeq.getAndIncrement();
    }
    public Long getStep() {
        return step;
    }
    public Long getStartSeq() {
        return startSeq.get();
    }

    public void setStartSeq(Long startSeq) {
        this.startSeq = new AtomicLong(startSeq);
    }

    public Long getFinishSeq() {
        return finishSeq;
    }

    public void setFinishSeq(Long finishSeq) {
        this.finishSeq = finishSeq;
    }
}
