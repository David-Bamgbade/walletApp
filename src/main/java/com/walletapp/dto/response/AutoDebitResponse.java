package com.walletapp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutoDebitResponse {
    private String message;
    private boolean successful;
}
