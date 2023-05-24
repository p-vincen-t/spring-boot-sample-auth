package com.auth.techno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Controller {

    private final AuthService service;

    @Autowired
    public Controller(AuthService Service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = getValidationMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try {
            User user = this.service.login(authRequest.getUsername(), authRequest.getUserName());
            return ResponseEntity.ok(user);
        } catch (AuthLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = getValidationMessage(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        try {
            User user = this.service.register(authRequest.getUsername(), authRequest.getPassword(), authRequest.getEmail());

            return ResponseEntity.ok(user);
        } catch (AuthRegisterException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        if(this.service.logout()) {
            return ResponseEntity.ok("user logged out");
        }
        else {
            return ResponseEntity.status(404).body("user not logged in");
        }
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody AuthRequest authRequest) {
        if(this.service.resetPassword(authRequest.getUsername())) {
            return ResponseEntity.ok("user password reset successfully");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
        }
    }

    private String getValidationMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getDefaultMessage()).append("\n");
        }
        return errorMessage.toString().trim();
    }
}
