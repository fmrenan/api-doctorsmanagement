package com.renanmuniz.backend.repositories;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.renanmuniz.backend.entities.Address;
import com.renanmuniz.backend.tests.Factory;

@DataJpaTest
public class AddressRepositoryTests {

	private String existingCep;
	private String nonExistingCep;
	private Long totalAdresses;
	private Address address;
	@Autowired
	private AddressRepository repository;
	
	@BeforeEach
	public void setup() throws Exception {
		existingCep = "08090284";
		nonExistingCep = "29315736";
		totalAdresses = 1L;
		address = Factory.createAddress();
	}
	
	@Test
	public void findAlllShouldReturnAListOfAddress() {
		List<Address> result = repository.findAll();

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertTrue(result.size() == totalAdresses);
	}

	@Test
	public void findByCepShouldReturnANotNullableAddressOptionalWhenIdExists() {
		Address result = repository.findByCep(existingCep);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(1L,result.getId());
	}
	
	@Test
	public void findByCepShouldReturnNullWhenCepNonExists() {
		Address result = repository.findByCep(nonExistingCep);

		Assertions.assertNull(result);
	}

	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
		address.setId(null);
		
		address = repository.save(address);
		
		Assertions.assertNotNull(address.getId());
		Assertions.assertEquals(totalAdresses + 1, address.getId());
	}
	
	
}
