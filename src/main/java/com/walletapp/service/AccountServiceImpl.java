package com.walletapp.service;

import com.walletapp.data.model.Account;
import com.walletapp.data.model.Customer;
import com.walletapp.data.model.MonthlyAutoDebit;
import com.walletapp.data.repository.AccountRepo;
import com.walletapp.data.repository.CustomerRepo;
import com.walletapp.data.repository.MonthlyAutoDebitRepo;
import com.walletapp.dto.request.*;
import com.walletapp.dto.response.*;
import com.walletapp.mapper.CreateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class AccountServiceImpl implements AccountService {

    private Long generateAccountNumber(){
        Random rand = new Random();
        return rand.nextLong(1000000000);
    }

    private double newUserAccountBalance() {
        return 0.0;
    }

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private MonthlyAutoDebitRepo monthlyAutoDebitRepo;

    private boolean transferExecuted;


    public boolean isSuccesfull(){
        return transferExecuted;
    }



    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    public void processMonthlyAutoDebits() {
        MonthlyAutoDebitResponse response = new MonthlyAutoDebitResponse();
        LocalDateTime dateToDebit = LocalDateTime.now();

        // Retrieve all configurations where the nextDebitDate is today or in the past
        List<MonthlyAutoDebit> debitDetails = monthlyAutoDebitRepo.findByDateToDebit(dateToDebit);

        for (MonthlyAutoDebit detail : debitDetails) {
            Optional<Account> senderAccount = accountRepo.findByPhoneNumber(detail.getSenderPhoneNumber());
            Optional<Account> receiverAccount = accountRepo.findByPhoneNumber(detail.getReceiverPhoneNumber());

            if (senderAccount.isPresent()) {
                Account myAccount = senderAccount.get();

                if (detail.getAmountToDebit() <= myAccount.getAccountBalance()) {
                    myAccount.setAccountBalance(myAccount.getAccountBalance() - detail.getAmountToDebit());
                    accountRepo.save(myAccount);

                    if (receiverAccount.isPresent()) {
                        Account receiver = receiverAccount.get();
                        receiver.setAccountBalance(receiver.getAccountBalance() + detail.getAmountToDebit());
                        accountRepo.save(receiver);
                    }

                    // Set the next debit date (next month)
                    detail.setDateToDebit(dateToDebit.plus(1, ChronoUnit.MONTHS));
                    monthlyAutoDebitRepo.save(detail); // Save updated configuration

                    System.out.println("Successfully debited myAccount for : " + detail.getAmountToDebit());
                } else {
                    System.out.println("Insufficient balance for phone number");
                }
            } else {
                System.out.println("Account not found for phone number");
            }
        }
        response.setSuccess(true);
        transferExecuted = true;
    }


    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    public void processSecondsAutoDebits() {
        MonthlyAutoDebitResponse response = new MonthlyAutoDebitResponse();
        LocalDateTime dateToDebit = LocalDateTime.now();

        // Retrieve all configurations where the nextDebitDate is today or in the past
        List<MonthlyAutoDebit> debitDetails = monthlyAutoDebitRepo.findByDateToDebit(dateToDebit);

        for (MonthlyAutoDebit detail : debitDetails) {
            Optional<Account> senderAccount = accountRepo.findByPhoneNumber(detail.getSenderPhoneNumber());
            Optional<Account> receiverAccount = accountRepo.findByPhoneNumber(detail.getReceiverPhoneNumber());

            if (senderAccount.isPresent()) {
                Account myAccount = senderAccount.get();

                if (detail.getAmountToDebit() <= myAccount.getAccountBalance()) {
                    myAccount.setAccountBalance(myAccount.getAccountBalance() - detail.getAmountToDebit());
                    accountRepo.save(myAccount);

                    if (receiverAccount.isPresent()) {
                        Account receiver = receiverAccount.get();
                        receiver.setAccountBalance(receiver.getAccountBalance() + detail.getAmountToDebit());
                        accountRepo.save(receiver);
                    }

                    // Set the next debit date (next month)
                    detail.setDateToDebit(dateToDebit.plus(2, ChronoUnit.MINUTES));
                    monthlyAutoDebitRepo.save(detail); // Save updated configuration

                    System.out.println("Successfully debited myAccount for : " + detail.getAmountToDebit());
                } else {
                    System.out.println("Insufficient balance for phone number");
                }
            } else {
                System.out.println("Account not found for phone number");
            }
        }
        response.setSuccess(true);
        transferExecuted = true;
    }


    public MonthlyAutoDebitResponse registerAutoDebit(RegisterAutoDebitRequest request) {
        LocalDateTime nextDebitDate = LocalDateTime.now().plus(request.getDuration(), request.getTimeUnit());
        MonthlyAutoDebit config = new MonthlyAutoDebit();
        config.setSenderPhoneNumber(request.getSenderPhoneNumber());
        config.setReceiverPhoneNumber(request.getReceiverPhoneNumber());
        config.setAmountToDebit(request.getAmountTODebit());
        config.setDateToDebit(nextDebitDate);
        config.setTimeUnit(request.getTimeUnit());
        config.setDuration(request.getDuration());
        monthlyAutoDebitRepo.save(config);
        MonthlyAutoDebitResponse response = new MonthlyAutoDebitResponse();
        response.setSuccess(true);
        processSecondsAutoDebits();
        return response;
    }


    public CreateWalletResponse createWallet(PhoneNumberRequest phoneNumber) {
        Optional<Customer> existingCustomer = customerRepo.findByPhoneNumber(phoneNumber.getPhoneNumber());
        if (existingCustomer.isPresent()) {
            existingCustomer.get();
            Account account = new Account();
            account.setFirstName(existingCustomer.get().getFirstName());
            account.setLastName(existingCustomer.get().getLastName());
            account.setEmail(duplicateEmail(existingCustomer.get().getEmail()));
            account.setPhoneNumber(duplicatePhoneNumber(existingCustomer.get().getPhoneNumber()));
            account.setAccountNumber(generateAccountNumber());
            account.setAccountBalance(newUserAccountBalance());
            account.setTransFerPin(existingCustomer.get().getAppPin());
            account.setCreatedAt(LocalDateTime.now());
            accountRepo.save(account);
            return CreateAccount.createWalletResponse(account);
        } else {
            throw new IllegalArgumentException("User Is Not Registered");
        }
    }

    public TransferResponse addMoneyToWallet(AddMoneyRequest request) {
        TransferResponse response = new TransferResponse();

        Optional<Account> myAccount = accountRepo.findByPhoneNumber(request.getPhoneNumber());

       if (myAccount.isPresent()) {
           Account account = myAccount.get();
           account.setAccountBalance(account.getAccountBalance() +validateAmount(request.getAmount()));
           accountRepo.save(account);
       }

        else {
                throw new IllegalArgumentException("Can't Add Money to Wallet");
            }
        response.setMessage("Successfully Added " + request.getAmount() + " to Wallet");
        response.setAddedMoneyAt(LocalDateTime.now());
        return response;
    }

    @Override
    public TransferResponse transferMoney(TransferRequest request) {
        TransferResponse response = new TransferResponse();

            Optional<Account> myAccount = accountRepo.findByPhoneNumber(request.getSenderPhoneNumber());
            if (myAccount.isPresent()) {
                Account senderAccount = myAccount.get();
                if (senderAccount.isLoggedIn()) {
                    senderAccount.setAccountBalance(senderAccount.getAccountBalance() - validateAmount(request.getAmount()));
                    accountRepo.save(senderAccount);
                } else {
                    throw new IllegalArgumentException("NOT LOGGED IN");
                }
                Optional<Account> receiverAccount = accountRepo.findByPhoneNumber(request.getReceiverPhoneNumber());
                if (receiverAccount.isPresent()) {
                    Account account = receiverAccount.get();
                    account.setAccountBalance(account.getAccountBalance() + request.getAmount());
                    account.getFirstName();
                    account.getLastName();
                    accountRepo.save(account);
                } else {
                    throw new IllegalArgumentException("Can't Transfer Money to Wallet");
                }
                response.setMessage("Successfully Sent " + request.getAmount() + " to " + receiverAccount.get().getFirstName() + receiverAccount.get().getLastName());
                response.setTransferSuccessful(true);
                response.setSentAt(LocalDateTime.now());
            }
        return response;
    }


    public LoginResponse loginUser(PhoneNumberRequest phoneNumber) {
        LoginResponse response = new LoginResponse();
        Optional<Account> account = accountRepo.findByPhoneNumber(phoneNumber.getPhoneNumber());
        if (account.isPresent()) {
            Account myAccount = account.get();
            myAccount.setLoggedIn(true);
            accountRepo.save(myAccount);
        }
        else {
            throw new IllegalArgumentException("Not Logged In");
        }
        response.setLoggedIn(true);
        return response;
    }



    public LoginResponse logOutUser(PhoneNumberRequest phoneNumber) {
            LoginResponse response = new LoginResponse();
            Optional<Account> myAccount = accountRepo.findByPhoneNumber(phoneNumber.getPhoneNumber());
            if (myAccount.isPresent()) {
                Account account = myAccount.get();
                account.setLoggedIn(false);
                accountRepo.save(account);
                response.setLoggedIn(false);
            }else {
                throw new IllegalArgumentException("Can't Log Out");
            }
            return response;
    }


    private double validateAmount(double amount) {
        if (amount > 0) {
            return amount;
        }
        else {
            throw new IllegalArgumentException("Amount Must Be More Than Zero");
        }
    }

    private String duplicateEmail(String email) {
        Optional<Account> customerAccount =accountRepo.findByEmail(email);
        if (customerAccount.isPresent()) {
           throw new IllegalArgumentException("Email already exists");
        }
        return email.toLowerCase();
    }

    private String duplicatePhoneNumber(String phoneNumber) {
        Optional<Account> customerAccount =accountRepo.findByPhoneNumber(phoneNumber);
        if (customerAccount.isPresent()) {
            throw new IllegalArgumentException("PhoneNumber already exist");
        }
        return phoneNumber;
    }


}
