package com.enrolle.enrolles_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enrolle.enrolles_service.Exceptions.ResourceNotFoundException;
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
			user.map(usr -> {
				dependent.setUser(usr);
				return dependentRepository.save(dependent);
			});
		}
		return new ResourceNotFoundException("Dependent not found for user id " + userId);
	}

	public Dependent update(Long userId, Dependent newDependent) {
		return dependentRepository.findById(userId).map(dependent -> {
			dependent.setName(newDependent.getName());
			dependent.setDateOfBirth(newDependent.getDateOfBirth());
			return dependentRepository.save(dependent);
		}).orElseGet(() -> {
			newDependent.setId(userId);
			return dependentRepository.save(newDependent);
		});
	}

	public void delete(Long id) {
		dependentRepository.deleteById(id);
	}

	public Object getDependentById(Long Id) {
		Optional<Dependent> dependent = dependentRepository.findById(Id);
		if (dependent.isPresent()) {
			return dependent.get();
		} else {
			return new ResourceNotFoundException("Dependent with id" + Id + "not Found");
		}

	}

}
