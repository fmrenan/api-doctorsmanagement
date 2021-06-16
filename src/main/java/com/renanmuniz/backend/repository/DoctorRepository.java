package com.renanmuniz.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.renanmuniz.backend.entities.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}