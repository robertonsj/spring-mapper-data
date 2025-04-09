package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
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
	    void testGetUserById() {
	    	UserDTO userDTO = new UserDTO();
	        userDTO.setName("John Doe");
	        userDTO.setId(1L);

	        User user = new User();
	        user.setName("John Doe");
	        user.setId(1L);
	        
	        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
	        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
	        
	        UserDTO foundUser = userService.getUserById(1L);
	        assertNotNull(foundUser);
	        assertEquals("John Doe", foundUser.getName());
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
	    
	    @Test
	    void testDeleteUser_successful() {
	    	Long id = 1L;
	    	User user = new User();
	    	user.setId(id);
	        user.setName("John Doe");
	        user.setEmail("test@example.com");
	        
	        //When
	        when(userRepository.findById(id)).thenReturn(Optional.of(user));
	        //Then
	        userService.deleteUser(id);
	        
	        verify(userRepository).findById(id);
	        verify(userRepository).delete(user);
	        
	    }
	    
	    @Test
	    void testDeleteUser_userNotFound() {
	    	Long id = 2L;
	    	
	    	//When
	    	when(userRepository.findById(id)).thenReturn(Optional.empty());
	    	//Then
	    	assertThrows(UserNotFoundException.class, () -> {
	    		userService.deleteUser(id);
	    	});
	    	
	    	verify(userRepository).findById(id);
	    	verify(userRepository, never()).delete(any());
	    }
	    
	    @Test
	    void testUpdateUser_successful() {
	    	Long userId = 1L;
	    	
	    	User existingUser = new User();
	        existingUser.setId(userId);
	        existingUser.setName("Old Name");
	        existingUser.setEmail("old@example.com");
	        
	        UserDTO updatedDTO = new UserDTO();
	        updatedDTO.setName("New Name");
	        updatedDTO.setEmail("new@example.com");
	        
	        User updatedUser = new User();
	        updatedUser.setId(userId);
	        updatedUser.setName("New Name");
	        updatedUser.setEmail("new@example.com");
	        
	        UserDTO returnedDTO = new UserDTO();
	        returnedDTO.setId(userId);
	        returnedDTO.setName("New Name");
	        returnedDTO.setEmail("new@example.com");
	        
	        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
	        when(userRepository.save(existingUser)).thenReturn(updatedUser);
	        when(userMapper.toUserDTO(updatedUser)).thenReturn(returnedDTO);
	        
	        UserDTO result = userService.updateUser(userId, updatedDTO);
	        
	        assertNotNull(result);
	        assertEquals("New Name", result.getName());
	        assertEquals("new@example.com", result.getEmail());
	        
	        verify(userRepository).findById(userId);
	        verify(userRepository).save(updatedUser);
	        verify(userMapper).toUserDTO(updatedUser);
	        
	    }
	    
	    @Test
	    void testUpdateUser_userNotFound() {
	    	Long userId = 2L;
	    	UserDTO userDTO = new UserDTO();
	        userDTO.setName("Test");
	        userDTO.setEmail("test@example.com");
	    	
	    	when(userRepository.findById(userId)).thenReturn(Optional.empty());
	    	
	    	assertThrows(UserNotFoundException.class, () -> {
	    		userService.updateUser(userId, userDTO);
	    	});
	    	
	    	verify(userRepository).findById(userId);
	    	verify(userRepository, never()).save(any());
	    	verify(userMapper, never()).toUserDTO(any());
	    	
	    }
}
