package com.renanmuniz.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.tests.Factory;

@DataJpaTest
public class DoctorRepositoryTests {

	private Long existingId;
	private Long nonExistingId;
	private Long totalDoctors;
	private Doctor doctor;
	@Autowired
	private DoctorRepository repository;
	
	@BeforeEach
	public void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 300L;
		totalDoctors = 1L;
		doctor = Factory.createDoctor();
	}
	
	@Test
	public void findAlllShouldReturnAListOfDoctors() {
		List<Doctor> result = repository.findAll();

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertTrue(result.size() == totalDoctors);
	}

	@Test
	public void findByIdShouldReturnANotNullableDoctorOptionalWhenIdExists() {
		Optional<Doctor> result = repository.findById(existingId);

		Assertions.assertTrue(result.isPresent());
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		doctor.setId(null);
		
		doctor = repository.save(doctor);
		
		Assertions.assertNotNull(doctor.getId());
		Assertions.assertEquals(totalDoctors + 1, doctor.getId());
	}

	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		repository.deleteById(existingId);

		Optional<Doctor> result = repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void softDeleteShouldInactivateWhenIdExists() {
		doctor = repository.getOne(existingId);
		
		doctor.setActive(false);		
		doctor = repository.save(doctor);
		
		Assertions.assertFalse(doctor.isActive());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdNonExists() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
	
}
