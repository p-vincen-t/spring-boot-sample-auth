package com.auth.techno.dto;

import javax.validation.constraints.NotBlank;

public class PasswordResetRequest {

    @NotBlank(message = "Username is required")
    private String username;

    // Constructors, getters, and setters
    public PasswordResetRequest() {
    }

    public PasswordResetRequest(String username) {
        this.username = username;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
