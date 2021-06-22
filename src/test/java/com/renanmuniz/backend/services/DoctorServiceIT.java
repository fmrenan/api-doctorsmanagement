package com.renanmuniz.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.DoctorInsertDTO;
import com.renanmuniz.backend.repositories.DoctorRepository;
import com.renanmuniz.backend.services.exceptions.ResourceNotFoundException;
import com.renanmuniz.backend.tests.Factory;

@SpringBootTest
@Transactional
public class DoctorServiceIT {

	@Autowired
	private DoctorService service;
	
	@Autowired
	private DoctorRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long totalDoctors;
	
	@BeforeEach
	void setup() throws Exception{
		existingId = 1L;
		nonExistingId = 0L;
		totalDoctors = 1L;
	}
	
	@Test
	public void findAllShouldReturnListWhenDefaultParams() {
		List<DoctorDTO> result = service.findAll("0", "0", "0", "0", "0", "0");
		
		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getId());
		assertEquals("1234567", result.get(0).getCrm());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyListWhenNameDoesNotExists() {
		List<DoctorDTO> result = service.findAll("Abc", "0", "0", "0", "0", "0");
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void insertShouldInsertResourceWhenDoctorIsValid() {
		DoctorInsertDTO insertDto = Factory.createDoctorInsertDTO();
		
		service.insert(insertDto);
		
		Assertions.assertEquals(totalDoctors+1, repository.findAll().size());
	}
	
	@Test
	public void insertShouldThrowHttpClientErrorExceptionResourceWhenCepIsNotValid() {
		DoctorInsertDTO insertDto = Factory.createDoctorInsertDTO();
		insertDto.setCep(null);		
		
		Assertions.assertThrows(HttpClientErrorException.class, ()->{
			service.insert(insertDto);
		});
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingId);
		
		Assertions.assertEquals(totalDoctors-1, repository.findAll().size());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			service.delete(nonExistingId);
		});
	}
	
	
}
