package com.example.demo.mapper;

import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
//	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	UserDTO toUserDTO(User user);
	User toUser(UserDTO userDTO);
	
}
