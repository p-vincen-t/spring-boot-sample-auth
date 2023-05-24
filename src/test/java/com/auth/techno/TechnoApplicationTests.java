package com.auth.techno;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TechnoApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private SessionController sessionController;

	@Mock
	private BindingResult bindingResult;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void register_ValidUser_ReturnsOkResponse() {
		// Arrange
		RegisterRequest registerRequest = new RegisterRequest("john", "password", "john@example.com");
		when(bindingResult.hasErrors()).thenReturn(false);
		when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(false);
		when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashedPassword");

		// Act
		ResponseEntity<?> response = sessionController.register(registerRequest, bindingResult);

		// Assert
		verify(userRepository).save(any(User.class));
		assert response.getStatusCode() == HttpStatus.OK;
		assert response.getBody().equals("User registered successfully");
	}

	@Test
	void register_InvalidRequest_ReturnsBadRequest() {
		// Arrange
		RegisterRequest registerRequest = new RegisterRequest("", "", "");
		when(bindingResult.hasErrors()).thenReturn(true);

		// Act
		ResponseEntity<?> response = sessionController.register(registerRequest, bindingResult);

		// Assert
		verify(userRepository, never()).save(any(User.class));
		assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
	}

	@Test
	void register_DuplicateUsername_ReturnsBadRequest() {
		// Arrange
		RegisterRequest registerRequest = new RegisterRequest("john", "password", "john@example.com");
		when(bindingResult.hasErrors()).thenReturn(false);
		when(userRepository.existsByUsername(registerRequest.getUsername())).thenReturn(true);

		// Act
		ResponseEntity<?> response = sessionController.register(registerRequest, bindingResult);

		// Assert
		verify(userRepository, never()).save(any(User.class));
		assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
		assert response.getBody().equals("Username is already taken");
	}

}
