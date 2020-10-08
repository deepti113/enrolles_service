package com.enrolle.enrolles_service.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.enrolle.enrolles_service.constants.ApplicationConstants;
import com.enrolle.enrolles_service.domain.Response;
import com.enrolle.enrolles_service.domain.User;
import com.enrolle.enrolles_service.service.DependentService;
import com.enrolle.enrolles_service.service.UserService;

@RequestMapping("enrolles")
@RestController
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	DependentService dependentService;

	/* To get all User */
	@GetMapping("/users")
	public Response getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (CollectionUtils.isEmpty(users)) {
			return new Response(HttpStatus.NO_CONTENT.value(), ApplicationConstants.USERS_NOT_FOUND);
		}
		return new Response(HttpStatus.OK.value(), ApplicationConstants.SUCCESS, users);
	}

	/**/
	@PostMapping(value = "/addUser")
	public Response saveUser(@Valid @RequestBody User user) {
		User savedUser;
		if (null != user) {
			savedUser = userService.save(user);
		} else {
			return new Response(HttpStatus.NO_CONTENT.value(), ApplicationConstants.USERS_NOT_FOUND);
		}
		return new Response(HttpStatus.OK.value(), ApplicationConstants.USER_SAVED + savedUser.getId());
	}

	@PutMapping("user/{id}")
	private Response update(@Valid @RequestBody User user, @PathVariable Long id) {
		User updatedUser = userService.update(user, id);
		if (null != updatedUser) {
			return new Response(HttpStatus.OK.value(), ApplicationConstants.SUCCESS, updatedUser);
		}
		return new Response(HttpStatus.NOT_FOUND.value(), ApplicationConstants.USER_NOT_FOUND, updatedUser);
	}

	/* Get User by Id */
	@GetMapping("/user/{userId}")
	private Response getUser(@PathVariable("userId") Long userId) {
		Object obj = userService.getUserById(userId);
		if (obj instanceof User) {
			return new Response(HttpStatus.OK.value(), ApplicationConstants.USER_RETRIEVE_SUCCESS, obj);
		} else {
			return new Response(HttpStatus.NOT_FOUND.value(), ApplicationConstants.ERROR, obj);
		}
	}

	@DeleteMapping("delete/{userId}")
	private Response deleteUser(@PathVariable("userId") Long userId) {
		Object obj = userService.delete(userId);
		if (obj == Boolean.TRUE) {
			return new Response(HttpStatus.OK.value(), ApplicationConstants.USER_DELETE_SUCCESS);
		}
		return new Response(HttpStatus.NOT_FOUND.value(), ApplicationConstants.USER_DELETE_FAILURE + userId, userId);
	}

}
