package com.renanmuniz.backend.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.DoctorInsertDTO;
import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;
import com.renanmuniz.backend.repositories.DoctorRepository;
import com.renanmuniz.backend.repositories.SpecialtyRepository;
import com.renanmuniz.backend.services.exceptions.ResourceNotFoundException;
import com.renanmuniz.backend.tests.Factory;

@ExtendWith(SpringExtension.class)
public class DoctorServiceTests {

	private Long existingId;
	private Long nonExistingId;
	private Doctor doctor;
	private Specialty specialty;
	private List<Doctor> doctors;
	private DoctorInsertDTO doctorInsertDTO;
	
	@InjectMocks
	private DoctorService service;
		
	@Mock
	private DoctorRepository repository;
	@Mock
	private SpecialtyRepository specRepository;
	@Mock
	private AddressService addressService;
	
	@BeforeEach
	void setup() throws Exception{
		existingId = 1L;
		nonExistingId = 0L;
		doctor = Factory.createDoctor();
		specialty = Factory.createSpecialty();
		doctorInsertDTO = Factory.createDoctorInsertDTO();
		doctors = new ArrayList<>();
		
		when(repository.findAllActive()).thenReturn(doctors);
		when(repository.save(ArgumentMatchers.any())).thenReturn(doctor);
		
		when(repository.findById(existingId)).thenReturn(Optional.of(doctor));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		when(repository.findBySpecialty(specialty)).thenReturn(doctors);
		
		when(repository.getOne(existingId)).thenReturn(doctor);
		when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		when(specRepository.getOne(existingId)).thenReturn(specialty);
		when(specRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	}
	
	@Test
	public void findAllShouldReturnListOfDoctorDTO() {
		List<DoctorDTO> result = service.findAll();
		
		Assertions.assertNotNull(result);
		verify(repository).findAllActive();
		
	}
	
	@Test
	public void findByIdShouldReturnDoctorDTOWhenIdExists() {
		DoctorDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		verify(repository).findById(existingId);		
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});			
	}
	
	@Test
	public void findBySpecialtyShouldReturnListOfDoctorDTOWhenSpecialtyExists() {
		List<DoctorDTO> result = service.findBySpecialty(specialty.getId());
		
		Assertions.assertNotNull(result);
		verify(repository).findBySpecialty(specialty);		
	}
	
	@Test
	public void findBySpecialtyThrowEntityNotFoundExceptionWhenSpecialtyDoesNotExists() {
		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			service.findBySpecialty(nonExistingId);
		});			
	}
	
	@Test
	public void insertShouldReturnDoctorDTOWhenIdExists() {
		DoctorDTO result = service.insert(doctorInsertDTO);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldThrowEntityNotFoundExceptionOWhenIdDoesNotExists() {
		Assertions.assertThrows(EntityNotFoundException.class, ()-> {
			service.update(nonExistingId, doctorInsertDTO);
		});
	}
	
	@Test
	public void softDeleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.softDelete(existingId);
		});
		
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		verify(repository).deleteById(existingId);
		
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
		
		verify(repository).deleteById(nonExistingId);		
	}
	
	
	
}
