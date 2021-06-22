package com.renanmuniz.backend.repositories;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.renanmuniz.backend.entities.Specialty;

@DataJpaTest
public class SpecialtyRepositoryTests {

	private String existingSpecialty;
	private String nonExistingSpecialty;
	private Long totalSpecialties;
	
	@Autowired
	private SpecialtyRepository repository;
	
	@BeforeEach
	public void setup() throws Exception {
		existingSpecialty = "Alergologia";
		nonExistingSpecialty = "Cirurgião Plástico";
		totalSpecialties = 8L;
	}
	
	@Test
	public void findAlllShouldReturnAListOfSpecialties() {
		List<Specialty> result = repository.findAll();

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertTrue(result.size() == totalSpecialties);
	}

	@Test
	public void findByNameShouldReturnANotNullableSpecialtyWhenNameExists() {
		Specialty result = repository.findByName(existingSpecialty);

		Assertions.assertTrue(result.getId() == 1L);
		Assertions.assertTrue(result.getName().equals(existingSpecialty));
	}
	
	@Test
	public void findByNameShouldReturnNullWhenNameNonExists() {
		Specialty result = repository.findByName(nonExistingSpecialty);

		Assertions.assertNull(result);
	}

	
	
}
