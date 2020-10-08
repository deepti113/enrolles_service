package com.enrolle.enrolles_service.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(DependentService.class);

	/**
	 * Get Dependents For user
	 * 
	 * @param userId
	 * @return
	 */
	public List<Dependent> getUserDependents(Long userId) {
		return dependentRepository.findByUserId(userId);
	}

	/**
	 * To Save dependents for User
	 * 
	 * @param userId
	 * @param dependent
	 * @return
	 */
	public Object saveDependents(Long userId, Dependent dependent) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			dependent.setUser(user.get());
			return dependentRepository.save(dependent);
		}
		logger.error("Error occured while saving Dependents");
		return new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND_FOR_USER + userId);
	}

	/**
	 * Update Dependent details for User
	 * 
	 * @param userId
	 * @param newDependent
	 * @param dependentId
	 * @return
	 */
	public Object updateDependent(Long userId, Dependent newDependent, Long dependentId) {
		if (!userRepository.existsById(userId)) {
			logger.error("Error occured while updating Dependents");
			throw new ResourceNotFoundException(ApplicationConstants.USER_NOT_FOUND);
		}
		return dependentRepository.findById(dependentId).map(dependent -> {
			dependent.setName(newDependent.getName());
			dependent.setDateOfBirth(newDependent.getDateOfBirth());
			return dependentRepository.save(dependent);
		}).orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND));
	}

	/**
	 * Delete dependent for user
	 * 
	 * @param userId
	 * @param dependentId
	 * @return
	 */
	public Object delete(Long userId, Long dependentId) {
		Optional<Dependent> dependent = dependentRepository.findByIdAndUserId(dependentId, userId);
		if (dependent.isPresent()) {
			dependentRepository.deleteById(dependentId);
			return true;
		}
		logger.error("Error occured while deleting Dependents");
		return new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND + dependentId);
	}

	/**
	 * Get Dependent Details by Id
	 * 
	 * @param Id
	 * @return
	 */
	public Object getDependentById(Long Id) {
		Optional<Dependent> dependent = dependentRepository.findById(Id);
		if (dependent.isPresent()) {
			return dependent.get();
		} else {
			logger.error("Error occured while getting Dependents");
			return new ResourceNotFoundException(ApplicationConstants.DEPENDENT_NOT_FOUND + Id);
		}

	}

}
