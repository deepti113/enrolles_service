package com.enrolle.enrolles_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrolle.enrolles_service.Exceptions.ResourceNotFoundException;
import com.enrolle.enrolles_service.constants.ApplicationConstants;
import com.enrolle.enrolles_service.domain.Dependent;
import com.enrolle.enrolles_service.domain.User;
import com.enrolle.enrolles_service.repository.DependentRepository;
import com.enrolle.enrolles_service.repository.UserRepository;

@Service
public class DependentService {

	@Autowired
	DependentRepository dependentRepository;
	@Autowired
	UserRepository userRepository;

	public List<Dependent> getUserDependents(Long userId) {
		return dependentRepository.findByUserId(userId);
	}

	public Object saveDependents(Long userId, Dependent dependent) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			dependent.setUser(user.get());
			return dependentRepository.save(dependent);
		}
		return new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND_FOR_USER + userId);
	}

	public Object updateDependent(Long userId, Dependent newDependent, Long dependentId) {
		if (!userRepository.existsById(userId)) {
			throw new ResourceNotFoundException(ApplicationConstants.USER_NOT_FOUND);
		}
		return dependentRepository.findById(dependentId).map(dependent -> {
			dependent.setName(newDependent.getName());
			dependent.setDateOfBirth(newDependent.getDateOfBirth());
			return dependentRepository.save(dependent);
		}).orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND));
	}

	public Object delete(Long userId, Long dependentId) {
		Optional<Dependent> dependent = dependentRepository.findByIdAndUserId(dependentId, userId);
		if (dependent.isPresent()) {
			dependentRepository.deleteById(dependentId);
			return true;
		}
		return new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND + dependentId);
	}

	public Object getDependentById(Long Id) {
		Optional<Dependent> dependent = dependentRepository.findById(Id);
		if (dependent.isPresent()) {
			return dependent.get();
		} else {
			return new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND + Id);
		}

	}

}
