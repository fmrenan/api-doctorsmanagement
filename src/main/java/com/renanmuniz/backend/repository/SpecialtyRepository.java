package com.renanmuniz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renanmuniz.backend.entities.Specialty;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long>{

}