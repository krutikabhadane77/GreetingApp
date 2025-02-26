package com.GreetingApp.GreetingApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class AuthUserDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-z]{2,}$",message = "First name should start with capital letter")
    private String firstName;
    @Pattern(regexp = "^[A-Z]{1}[a-z]{2,}$",message = "Last name should start with capital letter")
    private String lastName;
    @Email(message = "Email should be in proper manner!")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&*()-+=])(?=.*[0-9])([a-zA-Z0-9@._-]).{8,}$",message = "Password should contain at least 1 special character, Caps letter,small letter and digit and can not be less than 8 characters")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
