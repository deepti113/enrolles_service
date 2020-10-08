package com.enrolle.enrolles_service.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enrolle.enrolles_service.domain.Response;
import com.enrolle.enrolles_service.domain.User;
import com.enrolle.enrolles_service.service.UserService;

@RequestMapping("enrolles")
@RestController
public class UserController {
	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/* Return all User */
	@GetMapping("/users")
	public Response getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (CollectionUtils.isEmpty(users)) {
			return new Response(HttpStatus.NO_CONTENT.value(), "Users details not available");
		}
		return new Response(HttpStatus.OK.value(), "Sucess", users);
	}

	/**/
	@PostMapping(value = "/addUser")
	public Response saveUser(@Valid @RequestBody User user) {
		Long userId;
		if (null != user) {
			userId = userService.saveOrUpdate(user);
		} else {
			logger.info("User details are not available");
			return new Response(HttpStatus.NO_CONTENT.value(), "user details not available");
		}
		return new Response(HttpStatus.OK.value(), "user saved sucessfully with Id: " + userId);
	}

	/* Get User by Id */
	@GetMapping("/user/{userId}")
	private Response getUser(@PathVariable("userId") Long userId) {
		Object obj = userService.getUserById(userId);
		if (obj instanceof User) {
			return new Response(HttpStatus.OK.value(), "", obj);
		} else {
			return new Response(HttpStatus.NOT_FOUND.value(), "", obj);
		}
	}

	@PutMapping("user/{id}")
	private Response update(@Valid @RequestBody User user, @PathVariable Long id) {
		User updatedUser = userService.update(user, id);
		if (null != updatedUser) {
			return new Response(HttpStatus.OK.value(), "", updatedUser);
		}
		return new Response(HttpStatus.NOT_FOUND.value(), "", updatedUser);
	}

	@DeleteMapping("delete/{userId}")
	private Response deleteUser(@PathVariable("userId") Long userId) {
		Object obj = userService.delete(userId);
		if (obj == Boolean.TRUE) {
			return new Response(HttpStatus.OK.value(), "Success");
		}
		return new Response(HttpStatus.NOT_FOUND.value(), "User cannot be deleted with id:" + userId, userId);
	}

}
