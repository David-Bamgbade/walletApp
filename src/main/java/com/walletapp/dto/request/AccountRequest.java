package com.walletapp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountRequest {
    private long id;
    private Long accountNumber;
    private double amount;
    private String phoneNumber;
    private String email;
    private String transferPin;
}
