package com.walletapp.service;


import com.walletapp.dto.request.*;
import com.walletapp.dto.response.*;

public interface AccountService {
    CreateWalletResponse createWallet(PhoneNumberRequest user);
    TransferResponse addMoneyToWallet(AddMoneyRequest request);
    TransferResponse transferMoney(TransferRequest request);
    MonthlyAutoDebitResponse registerAutoDebit(RegisterAutoDebitRequest request);
    LoginResponse loginUser(PhoneNumberRequest phoneNumber);
    LoginResponse logOutUser(PhoneNumberRequest phoneNumber);
    void processMonthlyAutoDebits();
    void processSecondsAutoDebits();
    boolean isSuccesfull();
}
