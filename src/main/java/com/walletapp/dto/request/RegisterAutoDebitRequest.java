package com.walletapp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.temporal.ChronoUnit;

@Setter
@Getter
public class RegisterAutoDebitRequest {
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private double amountTODebit;
    private int duration;
    private ChronoUnit timeUnit;
}
