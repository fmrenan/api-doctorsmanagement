package com.renanmuniz.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.renanmuniz.backend.entities.Doctor;

@RepositoryRestResource
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

	@Query(value = "SELECT * FROM doctors d WHERE d.active = 'true'", nativeQuery = true)
	public List<Doctor> findAllActive();
}