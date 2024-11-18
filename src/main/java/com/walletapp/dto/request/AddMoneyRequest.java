package com.walletapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddMoneyRequest {
    private String phoneNumber;
    private double amount;
}
