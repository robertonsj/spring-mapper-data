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
	    	
	    	User user = new User("John Doe", "test@example.com");
	        UserDTO userDTO = new UserDTO("John Doe", "test@example.com");
	    	
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
	    void testGetUserById_successul() {
	    	UserDTO userDTO = new UserDTO("John Doe", "test@example.com");
	        User user = new User("John Doe", "test@example.com");
	        user.setId(1L);
	        
	        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
	        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
	        
	        UserDTO foundUser = userService.getUserById(1L);
	        assertNotNull(foundUser);
	        assertEquals("John Doe", foundUser.getName());
	    }
	    
	    @Test
	    void testGetUserById_userNotFound() {
	    	// Mock user not found behavior
	    	when(userRepository.findById(2L)).thenReturn(Optional.empty());
	    	
	    	assertThrows(UserNotFoundException.class,
	    			() -> userService.getUserById(2L));
	    }

	    @Test
	    void testCreateUser_successful() {
	    	UserDTO userDTO = new UserDTO("John Doe", "test@example.com");
	        User user = new User("John Doe", "test@example.com" );
	        User savedUser = new User("John Doe", "test@example.com");
	        
	        when(userMapper.toUser(userDTO)).thenReturn(user); // Ensure mapping works
	        when(userRepository.save(user)).thenReturn(savedUser);
	        when(userMapper.toUserDTO(user)).thenReturn(userDTO); // Ensure conversion back works
	        
	        UserDTO result = userService.createUser(userDTO);
	        assertNotNull(result);
	        assertEquals("John Doe", result.getName());
	    }
	    
	    @Test
	    void testCreateUser_withNullDTO() {
	    	assertThrows(NullPointerException.class, 
	    			() -> userService.createUser(null));
	    }
	    
	    @Test
	    void testDeleteUser_successful() {
	    	Long id = 1L;
	    	User user = new User("John Doe", "test@example.com" );
	    	user.setId(id);
	        
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
	    	
	    	
	    	User existingUser = new User("Old Name", "old@example.com" );
	    	existingUser.setId(userId);
	    	
	    	User updatedUser = new User("New Name", "new@example.com" );
	    	updatedUser.setId(userId);
	    	
	        UserDTO updatedDTO = new UserDTO("New Name", "new@example.com");
	        
	        UserDTO returnedDTO = new UserDTO("New Name", "new@example.com");
	        
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
	    	UserDTO userDTO = new UserDTO("Test", "test@example.com");
	    	
	    	when(userRepository.findById(userId)).thenReturn(Optional.empty());
	    	
	    	assertThrows(UserNotFoundException.class, 
	    			() -> userService.updateUser(userId, userDTO)
	    	);
	    	
	    	verify(userRepository).findById(userId);
	    	verify(userRepository, never()).save(any());
	    	verify(userMapper, never()).toUserDTO(any());
	    	
	    }
}
