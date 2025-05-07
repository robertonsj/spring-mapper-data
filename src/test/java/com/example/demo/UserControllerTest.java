package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

//	@Test
//	void contextLoads() {
//	}
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
	void clean() {
		userRepository.deleteAll();
	}
	
	//POST SUCCESS
	@Test
	void testCreateUser_success() throws Exception {
//		String userJson = "{\"name\":\"John Doe\", \"email\":\"created@example.com\"}";
		String userJson = TestUtils.toJson(new UserDTO("John Doe", "created@example.com"));
		
		mockMvc.perform(
				post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson)
			)
			.andExpect(status().isCreated());
	}
	
	//POST FAILURE
	@Test
	void testCreateUser_missingName() throws Exception {
		String userJson = TestUtils.toJson(new UserDTO("", "john@mail.com"));
		
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
			.andExpect(status().isBadRequest());
	}
	
	//POST FAILURE
	@Test
	void testCreateUser_invalidEmail() throws Exception {
		String userJson = TestUtils.toJson(new UserDTO("John Doe", "invalid-email"));
		
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
			.andExpect(status().isBadRequest());
	}
	
	//GET SUCCESS
	@Test
	void testGetAllUsers() throws Exception {
		User user = new User("John Doe", "john@example.com");
        User user2 = new User("Jane Doe", "jane@example.com");
        
		userRepository.save(user);
		userRepository.save(user2);
		
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("John Doe"))
				.andExpect(jsonPath("$[1].name").value("Jane Doe"));
	}
	
	//GET SUCESS
	@Test
	void testGetUserById_success() throws Exception{
		
		User user = new User("Roberto", "roberto@example.com");
//		user.setId(2L);
		userRepository.save(user);
		
		mockMvc.perform(get("/users/{id}", user.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Roberto"))
			.andExpect(jsonPath("$.email").value("roberto@example.com"));
		
	}

}
