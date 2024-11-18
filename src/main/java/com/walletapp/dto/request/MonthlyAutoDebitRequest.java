package com.walletapp.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MonthlyAutoDebitRequest {
    private int duration;
    @Enumerated(EnumType.STRING)
    private ChronoUnit timeUnit;
    private String phoneNumber;
    private double amountToDebit;
    private LocalDate debitDate;
}
