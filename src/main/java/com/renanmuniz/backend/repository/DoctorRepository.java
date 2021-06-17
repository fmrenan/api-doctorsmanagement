package com.renanmuniz.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.renanmuniz.backend.entities.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor>{
	
	@Query(
			  value = "SELECT * FROM tb_doctor d WHERE d.active = 'true'", 
			  nativeQuery = true)	
	List<Doctor> findAllActive();
}