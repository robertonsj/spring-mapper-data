package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@ExtendWith(MockitoExtension.class) // Enables Mockito in JUnit 5
public class UserServiceTest {
	 	
		@Mock
	    private UserRepository userRepository; // Mock repository
		
		@Mock
		private UserMapper userMapper;
	 	
		@InjectMocks
	    private UserService userService; // Inject mock into service

	    @Test
	    void testGetAllUsers() {
	    	
	    	User user = new User();
	        user.setName("John Doe");
	        
	        UserDTO userDTO = new UserDTO();
	        userDTO.setName("John Doe");
	    	
	    	//Mock behavior of userRepository
	    	List<User> userList = Collections.singletonList(user);
	    	when(userRepository.findAll()).thenReturn(userList);
	    	when(userMapper.toUserDTO(user)).thenReturn(userDTO);
	        
	        // Call service method
	        List<UserDTO> dtoList = userService.getAllUsers();

	        // Assertions
	        assertEquals(1, dtoList.size());
	        assertEquals("John Doe", dtoList.get(0).getName());
	        
	    }

	    @Test
	    void testCreateUser() {
	    	UserDTO userDTO = new UserDTO();
	        userDTO.setName("John Doe");

	        User user = new User();
	        user.setName("John Doe");

	        User savedUser = new User();
	        savedUser.setName("John Doe");
	        
	        when(userMapper.toUser(userDTO)).thenReturn(user); // Ensure mapping works
	        when(userRepository.save(user)).thenReturn(savedUser);
	        when(userMapper.toUserDTO(user)).thenReturn(userDTO); // Ensure conversion back works
	        
	        UserDTO result = userService.createUser(userDTO);
	        assertNotNull(result);
	        assertEquals("John Doe", result.getName());
	    }
}
