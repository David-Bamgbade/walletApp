package com.walletapp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private double amount;
    private String appPin;
}
