package com.walletapp.service;

import com.walletapp.dto.request.SignupRequest;
import com.walletapp.dto.response.SignupResponse;

public interface CustomerService {
    SignupResponse signupUser(SignupRequest request);

}
