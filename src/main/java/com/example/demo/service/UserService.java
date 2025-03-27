package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//Find all users and then return them as DTOs
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.map(UserMapper.INSTANCE::userToUserDTO)
				.collect(Collectors.toList());
	}
	
	public UserDTO createUser(UserDTO userDTO) {
		User user = UserMapper.INSTANCE.userDTOToUser(userDTO);
		User savedUser = userRepository.save(user);
		return UserMapper.INSTANCE.userToUserDTO(savedUser);
	}
	
}
