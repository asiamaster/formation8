package com.dili.formation8.vo;

import com.dili.formation8.domain.DataDictionaryValue;

import javax.validation.constraints.NotNull;

/**
 * Created by asiam on 2017/5/3 0003.
 */
public class DataDictionaryValueCondition extends DataDictionaryValue {
    @NotNull
    private String ddCode;

    public String getDdCode() {
        return ddCode;
    }

    public void setDdCode(String ddCode) {
        this.ddCode = ddCode;
    }
}
