package com.enrolle.enrolles_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.enrolle.enrolles_service.Exceptions.ResourceNotFoundException;
import com.enrolle.enrolles_service.domain.Response;
import com.enrolle.enrolles_service.domain.User;
import com.enrolle.enrolles_service.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public Long saveOrUpdate(User user) {
		User saveObj = userRepository.save(user);
		return saveObj.getId();
	}

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

	public Object delete(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return true;
		} else {
			return new ResourceNotFoundException("User with id" + id + "not Found");
		}

	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Object getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			return new ResourceNotFoundException("User with id" + id + "not Found");
		}

	}
}
