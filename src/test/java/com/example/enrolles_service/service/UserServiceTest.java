package com.example.enrolles_service.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.enrolle.enrolles_service.domain.User;
import com.enrolle.enrolles_service.repository.UserRepository;
import com.enrolle.enrolles_service.service.UserService;

public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock

	private UserRepository userRepository;

	private static User user;

	public UserServiceTest() {
	}

	@BeforeEach
	public void setup() {
		user = new User();
		user.setPhoneNumber("123456");
		user.setName("User");
		user.setDateOfBirth(new Date());
		user.setStatus(Boolean.TRUE);
		user.setId(new Long(1));
		when(userRepository.save(any())).thenReturn(user);
	}

	@Test
	public void testGetUser() {
		List<User> usersList = userService.getAllUsers();
		assertNotNull(usersList);
	}

	@Test
	public void testSaveUser() {
		when(userRepository.save(any())).thenReturn(user);
		User savedUser = userService.save(user);
		assertEquals(new Long(1), savedUser.getId());
	}

	@Test
	public void testDelete() {
		userService.delete(new Long(1));
	}

	@Test
	public void testGetUserById() {
		Object savedUser = userService.getUserById(new Long(1));
		assertNotNull(user);
	}
}