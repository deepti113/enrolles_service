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

import com.enrolle.enrolles_service.constants.ApplicationConstants;
import com.enrolle.enrolles_service.domain.Dependent;
import com.enrolle.enrolles_service.domain.Response;
import com.enrolle.enrolles_service.service.DependentService;
import com.enrolle.enrolles_service.service.UserService;

import io.swagger.annotations.Api;

@Api
@RequestMapping("enrolles/user")
@RestController
public class DependentController {

	@Autowired
	DependentService dependentService;
	@Autowired
	UserService userService;


	@GetMapping(value = "/{userId}/dependents")
	public List<Dependent> getDependentsByUser(@PathVariable(value = "userId") Long userId) {
		return dependentService.getUserDependents(userId);
	}

	@PostMapping(value = "/{userId}/dependent")
	public Response createDependent(@PathVariable(value = "userId") Long userId, @RequestBody Dependent dependent) {
		Object response = dependentService.saveDependents(userId, dependent);
		if (response instanceof Dependent) {
			return new Response(HttpStatus.OK.value(), ApplicationConstants.DEPENDENT_SUCCESS + userId, response);
		} else {
			return new Response(HttpStatus.NOT_FOUND.value(), ApplicationConstants.USER_NOT_FOUND, response);
		}
	}

	@PutMapping(value = "/{userId}/dependent/{dependentId}")
	public Response updateDependent(@PathVariable(value = "userId") Long userId, @RequestBody Dependent dependent,
			@PathVariable(value = "dependentId") Long dependentId) {
		Object response = dependentService.updateDependent(userId, dependent, dependentId);
		if (response instanceof Dependent) {
			return new Response(HttpStatus.OK.value(), ApplicationConstants.DEPENDENT_SUCCESS + userId, response);
		} else {
			return new Response(HttpStatus.NOT_FOUND.value(), ApplicationConstants.DEPENDENT_NOT_FOUND, response);
		}
	}

	@DeleteMapping("delete/{userId}/{dependentId}")
	private Response deleteUser(@PathVariable("userId") Long userId, @PathVariable("dependentId") Long dependentId) {
		Object obj = dependentService.delete(userId, dependentId);
		if (obj == Boolean.TRUE) {
			return new Response(HttpStatus.OK.value(), ApplicationConstants.SUCCESS);
		}
		return new Response(HttpStatus.NOT_FOUND.value(), ApplicationConstants.DEPENDENT_DELETE_FAILURE + dependentId,
				obj);
	}

}
