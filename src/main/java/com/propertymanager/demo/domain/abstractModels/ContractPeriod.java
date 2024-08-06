package com.propertymanager.demo.domain.abstractModels;

import lombok.Getter;

@Getter
public enum ContractPeriod {
    TWO_YEARS(2),
    THREE_YEARS(3),
    ;
    private final Integer code;


    ContractPeriod(Integer code) {
        this.code = code;
    }

     public static ContractPeriod fromCode(Integer code) {
        for (ContractPeriod period : ContractPeriod.values()) {
            if (period.getCode().equals(code)) {
                return period;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

}
