package com.GreetingApp.GreetingApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class LoginDTO {
    @Email(message = "Email should be in proper manner!")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&*()-+=])(?=.*[0-9])([a-zA-Z0-9@._-]).{8,}$",message = "Password should contain at least 1 special character, Caps letter,small letter and digit and can not be less than 8 characters")
    private String password;

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
