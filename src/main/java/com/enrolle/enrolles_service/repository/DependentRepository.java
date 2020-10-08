package com.enrolle.enrolles_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enrolle.enrolles_service.domain.Dependent;

@Repository
public interface DependentRepository extends JpaRepository<Dependent, Long> {

	List<Dependent> findByUserId(Long userId);

	Optional<Dependent> findByIdAndUserId(Long id, Long userId);
}
