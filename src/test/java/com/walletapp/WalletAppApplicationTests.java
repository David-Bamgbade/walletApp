package com.walletapp;

import com.walletapp.dto.request.*;
import com.walletapp.dto.response.*;
import com.walletapp.service.AccountService;
import com.walletapp.service.CustomerService;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class WalletAppApplicationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;


    @Test
    void testToSignUpCustomer() {
        SignupRequest request = new SignupRequest();
        request.setFirstName("Wale");
        request.setLastName("Kelvin");
        request.setPhoneNumber("08032468241");
        request.setEmail("kelx5457@gmail.com");
        request.setPassword("Complish1*");
        request.setAppPin("1235");
        request.setConfirmPassword("Complish1*");
        request.setCreatedAt(LocalDateTime.now());
        SignupResponse response = customerService.signupUser(request);
        assertEquals(response.getMessage(), "Account created");
    }

    @Test
    void testForUserToCreateWallet() {
        PhoneNumberRequest customer = new PhoneNumberRequest();
        customer.setPhoneNumber("08169468242");
        accountService.createWallet(customer);
        assertNotNull(customer);
    }

    @Test
    void testForUserToAddMoneyToWallet() {
        PhoneNumberRequest phoneNumber = new PhoneNumberRequest();
        AddMoneyRequest request = new AddMoneyRequest();
        request.setPhoneNumber("08169468242");
        request.setAmount(20000);
        TransferResponse response = accountService.addMoneyToWallet(request);
        assertEquals(response.getMessage(), "Successfully Added " + request.getAmount() + " to Wallet");
    }

    @Test
    void testToTransferMoney() {
        TransferRequest request = new TransferRequest();
        request.setSenderPhoneNumber("08169468242");
        request.setReceiverPhoneNumber("09110938241");
        request.setAmount(1000);
        request.setAppPin("1235");
        TransferResponse response = accountService.transferMoney(request);
        assertTrue(response.isTransferSuccessful());
    }

    @Test
    void testToRegisterCustomerForAutoDebit() {
        RegisterAutoDebitRequest request = new RegisterAutoDebitRequest();
        request.setAmountTODebit(2000);
        request.setDuration(2);
        request.setTimeUnit(ChronoUnit.MINUTES);
        request.setSenderPhoneNumber("08030438241");
        request.setReceiverPhoneNumber("09190438241");
        MonthlyAutoDebitResponse response = accountService.registerAutoDebit(request);
        assertTrue(response.isSuccess());
    }


    @Test
    void testToTryAutoDebit(){
        Awaitility.await()
                .atMost(20, TimeUnit.MINUTES)
                .until(()->accountService.isSuccesfull());
        assertTrue(accountService.isSuccesfull());
    }


    @Test
    void testToProcessMonthlyAutoDebit() {
       accountService.processMonthlyAutoDebits();
    }

    @Test
    void testToLoginUser() {
        PhoneNumberRequest phoneNumber = new PhoneNumberRequest();
        phoneNumber.setPhoneNumber("08169468242");
        LoginResponse userLogin = accountService.loginUser(phoneNumber);
        assertTrue(userLogin.isLoggedIn());
    }

    @Test
    void testToLogoutUser() {
        PhoneNumberRequest phoneNumber = new PhoneNumberRequest();
        phoneNumber.setPhoneNumber("08030438241");
        LoginResponse logOut = accountService.logOutUser(phoneNumber);
        assertFalse(logOut.isLoggedIn());
    }






}
