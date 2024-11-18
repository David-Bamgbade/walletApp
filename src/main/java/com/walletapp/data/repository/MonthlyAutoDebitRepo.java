package com.walletapp.data.repository;

import com.walletapp.data.model.MonthlyAutoDebit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MonthlyAutoDebitRepo extends JpaRepository <MonthlyAutoDebit, Long> {

    List<MonthlyAutoDebit> findByDateToDebit(LocalDateTime dateToDebit);
}
