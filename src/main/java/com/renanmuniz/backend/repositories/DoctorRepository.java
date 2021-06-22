package com.renanmuniz.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;

@RepositoryRestResource
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	@Query(value="SELECT d FROM Doctor d "
			+"WHERE (:name IS NULL OR :name = d.name) "
			+"AND (:crm IS NULL OR :crm = d.crm) "
			+"AND (:phone IS NULL OR :phone = d.phone) "
			+"AND (:cellPhone IS NULL OR :cellPhone = d.cellPhone) "
			+"AND (:specialty IS NULL OR :specialty in elements(d.specialties)) "
			+"AND (:cep IS NULL OR :cep = d.address.cep) "
			+"AND d.active = true")
	public List<Doctor> findAll(String name, String crm, String phone, String cellPhone, 
			Specialty specialty, String cep);

}