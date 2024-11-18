package com.walletapp.data.model;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long accountNumber;
    private double accountBalance = 0.0;
    private String transFerPin;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private boolean loggedIn;
    private LocalDateTime createdAt;
}
