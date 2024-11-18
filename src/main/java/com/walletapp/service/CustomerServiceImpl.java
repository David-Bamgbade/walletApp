package com.walletapp.service;

import com.walletapp.data.model.Customer;
import com.walletapp.data.repository.CustomerRepo;
import com.walletapp.dto.request.SignupRequest;
import com.walletapp.dto.response.SignupResponse;
import com.walletapp.mapper.CreateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Autowired
    EmailService emailService;

    public SignupResponse signupUser(SignupRequest request) {
        CreateAccount createAccount = new CreateAccount();
        Customer customer = new Customer();
        request.setEmail(duplicateEmail(request.getEmail()));
        request.setPhoneNumber(duplicatePhoneNumber(request.getPhoneNumber()));
        Customer saveCustomer = createAccount.createAccountRequest(request, customer);
        emailService.sendWelcomeEmail(saveCustomer.getEmail(), saveCustomer.getFirstName());
        customerRepo.save(saveCustomer);
        return CreateAccount.createAccountResponse(customer);
    }

    private String duplicateEmail(String email) {
        Optional<Customer> customer = customerRepo.findByEmail(email);
        if (customer.isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        return email;
    }

    private String duplicatePhoneNumber(String phoneNumber) {
        Optional<Customer> customerSignUP = customerRepo.findByPhoneNumber(phoneNumber);
        if (customerSignUP.isPresent()) {
            throw new RuntimeException("Phone number already exist");
        }
        return phoneNumber;
    }



}
