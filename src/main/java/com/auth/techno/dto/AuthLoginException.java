package com.auth.techno.dto;
public class AuthLoginException extends Exception {
    public AuthLoginException(String message) {
        super(message);
    }

    public static AuthLoginException userNameNotFound() {
        return new AuthLoginException("Username not found");
    }
    public static AuthLoginException passwordMismatch() {
        return new AuthLoginException("Password mismatch");
    }
}