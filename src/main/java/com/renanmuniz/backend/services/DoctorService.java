package com.renanmuniz.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renanmuniz.backend.dto.AddressDTO;
import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.DoctorResponseDTO;
import com.renanmuniz.backend.dto.SpecialtyDTO;
import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;
import com.renanmuniz.backend.repository.DoctorRepository;
import com.renanmuniz.backend.repository.SpecialtyRepository;
import com.renanmuniz.backend.services.exceptions.DataBaseException;
import com.renanmuniz.backend.services.exceptions.ResourceNotFoundException;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository repository;
	@Autowired
	private SpecialtyRepository specialtyRepository;
	@Autowired
	private AddressService addressService;
	
	@Transactional(readOnly = true)
	public List<DoctorDTO> findAll() {
		List<Doctor> doctors = repository.findAll();
		
		return doctors.stream().map(doc -> new DoctorDTO(doc, doc.getSpecialties())).collect(Collectors.toList());		
	}
	
	@Transactional(readOnly = true)
	public DoctorDTO findById(Long id) {
		Optional<Doctor> obj = repository.findById(id);
		Doctor entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new DoctorDTO(entity, entity.getSpecialties());
	}
	
	@Transactional
	public DoctorResponseDTO insert(DoctorDTO dto) {
		
		Doctor entity = new Doctor(); 
		convertDtoToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		AddressDTO address = addressService.getAddressByCep(dto.getCep());
		
		return new DoctorResponseDTO(entity, entity.getSpecialties(), address);
	}
	
	@Transactional
	public DoctorDTO update(Long id, DoctorDTO dto) {
		try {
			Doctor entity = repository.getOne(id); 
			convertDtoToEntity(dto, entity);
			
			entity = repository.save(entity);
			
			return new DoctorDTO(entity, entity.getSpecialties());
		}catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
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
