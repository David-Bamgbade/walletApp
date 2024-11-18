package com.walletapp.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Entity
public class MonthlyAutoDebit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int duration;
    @Enumerated(EnumType.STRING)
    private ChronoUnit timeUnit;
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private double amountToDebit;
    private LocalDateTime dateToDebit;
}
