package com.renanmuniz.backend.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.renanmuniz.backend.entities.Address;
import com.renanmuniz.backend.repositories.AddressRepository;
import com.renanmuniz.backend.services.exceptions.AddressNotFoundException;

@SpringBootTest
@Transactional
public class AddressServiceIT {

	@Autowired
	private AddressService service;
	
	@Autowired
	private AddressRepository repository;
	
	private String savedCep;
	private String nonSavedCep;
	private String nonExistingCep;
	private String invalidCep;
	private Long totalAdresses;
	
	@BeforeEach
	void setup() throws Exception{
		savedCep = "08090284";
		nonSavedCep = "29315736";
		nonExistingCep = "11111111";
		invalidCep = "123456789";
		totalAdresses = 1L;
	}
	
	
	@Test
	public void findAddressByCepShouldReturnAddressWhenSavedCep() {
		Address result = service.findAddressByCep(savedCep);
		
		assertNotNull(result);
		assertEquals("São Paulo", result.getLocalidade());
		assertEquals("SP", result.getUf());
		assertEquals(savedCep, result.getCep());
	}
	
	@Test
	public void findAddressByCepShouldInsertAndReturnAddressWhenNonSavedCep() {
		Address result = service.findAddressByCep(nonSavedCep);
		
		assertNotNull(result);
		assertEquals("Cachoeiro de Itapemirim", result.getLocalidade());
		assertEquals("ES", result.getUf());
		assertEquals(nonSavedCep, result.getCep());
		assertEquals(totalAdresses+1, repository.count());
	}
	
	@Test
	public void findAddressByCepShouldAddressNotFoundExceptionWhenNonExistingCep() {
		assertThrows(AddressNotFoundException.class, () -> {
			service.findAddressByCep(nonExistingCep);
		});
	}
	
	@Test
	public void findAddressByCepShouldThrowHttpClientErrorExceptionWhenInvalidCep() {
		assertThrows(HttpClientErrorException.class, () -> {
			service.findAddressByCep(invalidCep);
		});
	}
	
}
