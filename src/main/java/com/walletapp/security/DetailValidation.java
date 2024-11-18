package com.walletapp.security;


public class DetailValidation {

    public String validateEmail(String email) {
        boolean regex = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
         if (regex) {
            return email;
        }
        else {
            throw new IllegalArgumentException("Invalid Email");
        }
    }

    public String validateFirstName(String firstName) {
        boolean regex = firstName.matches("^[a-zA-Z]+([a-zA-Z0-9]( _ | - | \\\\. | , | # | \\\\+ )*)*[a-zA-Z0-9]+$");
        if (regex) {
            return firstName.toLowerCase();
        }
        else{
            throw new IllegalArgumentException("Invalid first name try again");
        }
    }

    public String validateLastName(String lastName) {
        boolean regex = lastName.matches("^[a-zA-Z]+([a-zA-Z0-9]( _ | - | \\\\. | , | # | \\\\+ )*)*[a-zA-Z0-9]+$");
        if (regex) {
            return lastName.toLowerCase();
        }
        else{
            throw new IllegalArgumentException("Invalid last name try again");
        }
    }

    public String validatePassword (String password) {
        boolean regex = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        if (regex) {
            return password;
        }

         else {
            throw new IllegalArgumentException("Invalid password try again");
        }
    }

    public String validatePin (String pin){
        boolean regex = pin.matches("^\\d{4}$") ;
        if (regex){
            return pin;
        }
        else {
            throw new IllegalArgumentException("Invalid pin try again");
        }
    }


    public String validatePhoneNumber(String phoneNumber) {
        boolean regex = phoneNumber.matches("^[0-9]{11}$");
        if (regex) {
            return phoneNumber;
        } else {
            throw new IllegalArgumentException("Invalid phone number try again");
        }
    }





}
