package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	@Autowired
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}
	
	//Find all users and then return them as DTOs
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.map(userMapper::toUserDTO)
				.collect(Collectors.toList());
	}
	
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(
						"User not found with ID: " + id));
		return userMapper.toUserDTO(user);
	}

	
	public UserDTO createUser(UserDTO userDTO) {
		User user = userMapper.toUser(userDTO);
		User savedUser = userRepository.save(user);	
		return userMapper.toUserDTO(savedUser);
	}
	
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(
						"User not found with ID: " + id));
		userRepository.delete(user);
	}
	
	public UserDTO updateUser(Long id, UserDTO userDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(
						"User not found with ID: " + id));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		User savedUser = userRepository.save(user);
		return userMapper.toUserDTO(savedUser);
				
	}
	
	
	

	
}
