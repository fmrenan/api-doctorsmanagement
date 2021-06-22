package com.renanmuniz.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renanmuniz.backend.entities.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long>{

	Specialty findByName(String name);
	
}