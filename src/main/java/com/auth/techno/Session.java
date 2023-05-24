package com.auth.techno;

import org.springframework.stereotype.Component;
import com.auth.techno.dto.AuthRegisterException;
import com.sun.techno.dto.AuthLoginException;

@Component
public class Session {

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    @Autowired
    public Session(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    private boolean isLoggedIn;
    private User loggedInUser;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public User login(String username, String password) throws AuthLoginException {
        isLoggedIn = true;
        loggedInUser = userRepository.getByUsername(username);
        if(loggedInUser == null) {
            throw AuthLoginException.userNameNotFound();
        }
        // Hash the password
        String hashedPassword = passwordEncoder.encode(password);
        if(loggedInUser.getPassword() == password) {
            this.isLoggedIn = true;
            return loggedInUser;
        }
        throw AuthLoginException.passwordMismatch();
    }

    public User register(String username, String password, String email) throws AuthRegisterException {

        // Check if the username is already taken
        if (userRepository.existsByUsername(username)) {
            throw AuthRegisterException.userNameTaken();
        }
        // Hash the password
        String hashedPassword = passwordEncoder.encode(password);
        // Create a new User entity with the hashed password
        User user = new User(username, hashedPassword, email);
        // Save the user to the database
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public boolean logout() {
        isLoggedIn = false;
        loggedInUser = null;
    }

    public boolean resetPassword(String username) {
        // Check if the username is already taken
        if (userRepository.existsByUsername(username)) {
            throw AuthRegisterException.userNameTaken();
        }
        else {
            return false;
        }
    }
}
