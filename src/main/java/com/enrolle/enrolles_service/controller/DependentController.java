package com.enrolle.enrolles_service.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enrolle.enrolles_service.Exceptions.ResourceNotFoundException;
import com.enrolle.enrolles_service.domain.Dependent;
import com.enrolle.enrolles_service.domain.Response;
import com.enrolle.enrolles_service.service.DependentService;
import com.enrolle.enrolles_service.service.UserService;

@RequestMapping("user")
@RestController
public class DependentController {

	@Autowired
	DependentService dependentService;
	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(DependentController.class);

	@GetMapping("/{userId}/dependents")
	public List<Dependent> getDependentsByUser(@PathVariable(value = "userId") Long userId) {
		return dependentService.getUserDependents(userId);
	}

	@PostMapping("/{userId}/dependent")
	public Response createDependent(@PathVariable(value = "userId") Long userId, @RequestBody Dependent dependent) {

		Object response = dependentService.saveDependents(userId, dependent);
		if (response instanceof Dependent) {
			return new Response(HttpStatus.OK.value(), "Dependent saved successfully for user:" + userId);
		} else {

			return new Response(HttpStatus.NOT_FOUND.value(), response.toString());
		}

	}

	@PutMapping("/{userId}/dependent/{dependentId}")
	public Response updateDependent(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "dependentId") Long dependentId, @Valid @RequestBody Dependent dependent) {

	}

}
