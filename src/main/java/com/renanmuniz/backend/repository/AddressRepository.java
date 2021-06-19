package com.renanmuniz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.renanmuniz.backend.entities.Address;

@RepositoryRestResource
public interface AddressRepository extends JpaRepository<Address, Long> {

	Address findByCep(String cep);
}