package com.example.enrolles_service.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.enrolle.enrolles_service.domain.Dependent;
import com.enrolle.enrolles_service.domain.User;
import com.enrolle.enrolles_service.repository.DependentRepository;
import com.enrolle.enrolles_service.repository.UserRepository;
import com.enrolle.enrolles_service.service.DependentService;
import com.enrolle.enrolles_service.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class DependecyServiceTest {

	@InjectMocks
	private DependentService dependentService;

	@Mock
	private DependentRepository dependentRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserService userservice;

	private static Optional<User> user;
	private static Dependent dependent;

	public DependecyServiceTest() {
	}

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		User user = new User();
		user.setPhoneNumber("123456");
		user.setName("User");
		user.setDateOfBirth(new Date());
		user.setStatus(Boolean.TRUE);
		user.setId(new Long(1));
		dependent = new Dependent();
		dependent.setId(new Long(1));
		dependent.setUser(user);
		dependent.setName("dependent");
		dependent.setDateOfBirth(new Date());
	}

	@Test
	public void testUserDependents() {
		when(dependentRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(dependent));
		List<Dependent> dependentList = dependentService.getUserDependents(anyLong());
		assertEquals(1, dependentList.size());
	}


	@Test
	public void testDelete() {
		dependentRepository.deleteById(anyLong());
	}

	@Test
	public void testGetDependentById() {
		Object user = dependentService.getDependentById(anyLong());
		assertNotNull(user);
		if (user instanceof User) {
			assertEquals(user, ((User) user).getName());
		}
	}
}