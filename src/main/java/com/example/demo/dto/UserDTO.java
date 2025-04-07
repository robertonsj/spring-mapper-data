package com.example.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
	
//	@NotBlank(message = "Name is required")
	private String name;
	
//	@NotBlank(message = "Email is required")
//	@Email(message = "Invalid email format")
	private String email;
	
	
}
