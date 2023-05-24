package com.auth.techno.dto;
public class AuthRegisterException extends Exception {
    public AuthRegisterException(String message) {
        super(message);
    }

    public static AuthRegisterException userNameTaken() {
        return new AuthLoginException("Username already taken");
    }
    public static AuthRegisterException emailTaken() {
        return new AuthLoginException("Email already taken");
    }
}