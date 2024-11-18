package com.walletapp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CreateWalletRequest {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String accountNumber;
    private String accountBalance;
    private String appPin;
    private String message;
    private LocalDateTime createdAt;
}
