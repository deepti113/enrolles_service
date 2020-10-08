package com.enrolle.enrolles_service.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrolle.enrolles_service.Exceptions.ResourceNotFoundException;
import com.enrolle.enrolles_service.constants.ApplicationConstants;
import com.enrolle.enrolles_service.domain.User;
import com.enrolle.enrolles_service.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 * Saving user
	 * 
	 * @param User
	 * @return
	 */
	public User save(User user) {
		User saveObj = userRepository.save(user);
		return saveObj;
	}

	/**
	 * Updating User details
	 * 
	 * @param newUser
	 * @param id
	 * @return
	 */
	public User update(User newUser, Long id) {
		return userRepository.findById(id).map(user -> {
			user.setName(newUser.getName());
			user.setStatus(newUser.getStatus());
			user.setDateOfBirth(newUser.getDateOfBirth());
			user.setPhoneNumber(newUser.getPhoneNumber());
			return userRepository.save(user);
		}).orElseGet(() -> {
			newUser.setId(id);
			return userRepository.save(newUser);
		});
	}

	/**
	 * Deleting User Details
	 * 
	 * @param id
	 * @return
	 */
	public Object delete(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return true;
		} else {
			logger.error("Error occured while deleting");
			return new ResourceNotFoundException(ApplicationConstants.USER_NOT_FOUND + id);
		}

	}

	/**
	 * To GetAll User Details
	 * 
	 * @return
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * To Get User details for Id
	 * 
	 * @param id
	 * @return
	 */
	public Object getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			logger.error("Error occured while getting user details");
			return new ResourceNotFoundException(ApplicationConstants.USER_NOT_FOUND + id);
		}

	}
}
