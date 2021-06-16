package com.renanmuniz.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.SpecialtyDTO;
import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;
import com.renanmuniz.backend.repository.DoctorRepository;
import com.renanmuniz.backend.repository.SpecialtyRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository repository;
	@Autowired
	private SpecialtyRepository specialtyRepository;
	
	@Transactional(readOnly = true)
	public List<DoctorDTO> findAll() {
		List<Doctor> doctors = repository.findAll();
		
		return doctors.stream().map(doc -> new DoctorDTO(doc, doc.getSpecialties())).collect(Collectors.toList());		
	}
	
	@Transactional
	public DoctorDTO insert(DoctorDTO dto) {
		Doctor entity = new Doctor(); 
		convertDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		
		return new DoctorDTO(entity, entity.getSpecialties());
	}
	
	@Transactional
	public DoctorDTO update(Long id, DoctorDTO dto) {
		Doctor entity = repository.getOne(id); 
		convertDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		return new DoctorDTO(entity, entity.getSpecialties());
	}
	
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	private void convertDtoToEntity(DoctorDTO dto, Doctor entity) {
		entity.setName(dto.getName());
		entity.setCrm(dto.getCrm());
		entity.setPhone(dto.getPhone());
		entity.setCellPhone(dto.getCellPhone());
		entity.setCep(dto.getCep());
		entity.getSpecialties().clear();
		
		for(SpecialtyDTO specDto : dto.getSpecialties()) {
			Specialty specialty = specialtyRepository.getOne(specDto.getId());
			
			entity.getSpecialties().add(specialty);
		}		
	}

	
}
