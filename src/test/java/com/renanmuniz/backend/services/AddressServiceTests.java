package com.renanmuniz.backend.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.renanmuniz.backend.entities.Address;
import com.renanmuniz.backend.repositories.AddressRepository;
import com.renanmuniz.backend.tests.Factory;

@ExtendWith(SpringExtension.class)
public class AddressServiceTests {

	private String savedCep;
	private Address address;
		
	@InjectMocks
	private AddressService service;
	
	@Mock
	private AddressRepository repository;
		
	@BeforeEach
	public void setup() {
		savedCep = "08090284";
		address = Factory.createAddress();
		
		when(repository.findByCep(savedCep)).thenReturn(address);
		when(repository.save(address)).thenReturn(address);		
	}
	
	@Test
	public void findByCepShouldReturnAddressWhenCepIsSaved() {
		Address result = service.findAddressByCep(savedCep);
		
		Assertions.assertNotNull(result);	
		Assertions.assertEquals("São Paulo", result.getLocalidade());
	}
}
