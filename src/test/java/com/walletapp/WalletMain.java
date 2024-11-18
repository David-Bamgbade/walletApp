package com.walletapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
    @EnableScheduling
    public class WalletMain {

        public static void main(String[] args) {
            SpringApplication.run(WalletMain.class, args);
        }
    }


