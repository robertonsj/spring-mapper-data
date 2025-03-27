package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
//import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class) // Enables Mockito in JUnit 5
public class UserServiceTest {
	 	
		@Mock
	    private UserRepository userRepository; // Mock repository
	 	
	    @InjectMocks
	    private UserService userService; // Inject mock into service

	    private User user1, user2;
//	    private UserDTO userDTO;

	    @BeforeEach
	    void setUp() {
	    	
	        user1 = new User(1L, "John Doe", "john@example.com");
	        user2 = new User(2L, "Jane Doe", "jane@example.com");
	        
//	        userDTO = new UserDTO();
//	        userDTO.setName("John Doe");
//	        userDTO.setEmail("john@example.com");
	    }

	    @Test
	    void testGetAllUsers() {
	    	//Mock behavior of userRepository
	        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
	        
	        // Call service method
//	        List<UserDTO> users = userService.getAllUsers();
	        
	        List<User> users = userService.getAllUsers();

	        // Assertions
	        assertEquals(2, users.size());
	        assertEquals("John Doe", users.get(0).getName());
	        assertEquals("Jane Doe", users.get(1).getName());
	        
	        // Verify method call
	        verify(userRepository, times(1)).findAll();
	    }

	    @Test
	    void testCreateUser() {
	    	// Mock the conversion and save operation
	        when(userRepository.save(any(User.class))).thenReturn(user1, user2);
	    	
//	    	//Mock the mapping from DTO to Entity
//	    	when(UserMapper.INSTANCE.userDTOToUser(userDTO)).thenReturn(user1);
//	    	when(userRepository.save(any(User.class))).thenReturn(user1);
//	    	when(UserMapper.INSTANCE.userToUserDTO(user1)).thenReturn(userDTO);

	        // Call service method with UserDTO
//	        UserDTO savedUser = userService.createUser(userDTO);
	        User savedUser = userService.createUser(user1);

	        // Assertions
	        assertNotNull(savedUser);
	        assertEquals("John Doe", savedUser.getName());
	    }
}
