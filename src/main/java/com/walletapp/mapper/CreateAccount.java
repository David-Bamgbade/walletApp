package com.walletapp.mapper;

import com.walletapp.data.model.Account;
import com.walletapp.data.model.Customer;
import com.walletapp.dto.request.SignupRequest;
import com.walletapp.dto.response.CreateWalletResponse;
import com.walletapp.dto.response.SignupResponse;
import com.walletapp.security.DetailValidation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CreateAccount {


     public static SignupResponse createAccountResponse(Customer user) {
          SignupResponse response = new SignupResponse();
          response.setId(user.getId());
          response.setFirstName(user.getFirstName().toLowerCase());
          response.setLastName(user.getLastName().toLowerCase());
          response.setEmail(user.getEmail().toLowerCase());
          response.setPhoneNumber(user.getPhoneNumber());
          response.setPassword(user.getPassword().toLowerCase());
          response.setConfirmPassword(user.getConfirmPassword().toLowerCase());
          response.setMessage("Account created");
          response.setCreatedAt(LocalDateTime.now());
          return response;
     }

     public Customer createAccountRequest (SignupRequest request, Customer user) {
          BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
          DetailValidation detail = new DetailValidation();
          user.setId(request.getId());
          user.setFirstName(detail.validateFirstName(request.getFirstName().toLowerCase()));
          user.setLastName(detail.validateLastName(request.getLastName()).toLowerCase());
          user.setEmail(detail.validateEmail(request.getEmail().toLowerCase()).toLowerCase());
          user.setPhoneNumber(detail.validatePhoneNumber(request.getPhoneNumber()));
          user.setPassword(encoder.encode(detail.validatePassword(request.getPassword())).toLowerCase());
          user.setConfirmPassword(encoder.encode(detail.validatePassword(request.getConfirmPassword())).toLowerCase());
          user.setAppPin(encoder.encode(detail.validatePin(request.getAppPin())));
          user.setCreatedAt(LocalDateTime.now());
          return user;
     }

     public static CreateWalletResponse createWalletResponse(Account user) {
          DetailValidation detail = new DetailValidation();
          CreateWalletResponse response = new CreateWalletResponse();
          response.setId(user.getId());
          response.setFirstName(detail.validateFirstName(user.getFirstName().toLowerCase()));
          response.setLastName(detail.validateLastName(user.getLastName().toLowerCase()));
          response.setEmail(detail.validateEmail(user.getEmail().toLowerCase()));
          response.setPhoneNumber(detail.validatePhoneNumber(user.getPhoneNumber()));
          response.setMessage("Account created");
          response.setCreatedAt(LocalDateTime.now());
          return response;
     }

     public static Account createWalletRequest (Account user) {
          DetailValidation detail = new DetailValidation();
          BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
          user.setId(user.getId());
          user.setFirstName(detail.validateFirstName(user.getFirstName().toLowerCase()));
          user.setLastName(detail.validateLastName(user.getLastName()).toLowerCase());
          user.setEmail(detail.validateEmail(user.getEmail().toLowerCase()).toLowerCase());
          user.setPhoneNumber(detail.validatePhoneNumber(user.getPhoneNumber()));
          user.setTransFerPin(encoder.encode(detail.validatePin(user.getTransFerPin())));
          return user;
     }
























}
