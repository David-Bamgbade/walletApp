package com.walletapp.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TransferResponse {
    private long id;
    private long accountNumber;
    private double balance;
    private LocalDateTime sentAt;
    private LocalDateTime receivedAt;
    private LocalDateTime addedMoneyAt;
    private String message;
    private boolean transferSuccessful;
    private boolean accountCreated;
}
