package com.auth.techno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Session sessionComponent;

    @Autowired
    public AuthService(Session sessionComponent) {
        this.sessionComponent = sessionComponent;
    }

    public User login(String username, String password) throws AuthLoginException {
        return this.sessionComponent.login(username, password);
    }

    public User register(String username, String password, String email) throws AuthRegisterException {
        return this.sessionComponent.register(username, password, email);
    }

    public boolean logout() {
        return this.sessionComponent.logout();
    }

    public boolean resetPassword(String username) throws AuthLoginException {
        return this.sessionComponent.resetPassword(username);
    }
}
