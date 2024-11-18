package com.walletapp.data.repository;
import com.walletapp.data.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findByPhoneNumber(String phoneNumber);

    Optional<Account> findByEmail(String email);



    @Override
    List<Account> findAll();

}
