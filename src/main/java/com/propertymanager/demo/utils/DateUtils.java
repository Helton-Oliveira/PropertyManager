package com.propertymanager.demo.utils;

import com.propertymanager.demo.domain.abstractModels.ContractPeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtils {

    public static LocalDate calculateFutureDate(LocalDateTime startDate, ContractPeriod period) {
       var contractCreationDate = startDate.toLocalDate();
       return contractCreationDate.plusYears(period.getCode());
    }
}
