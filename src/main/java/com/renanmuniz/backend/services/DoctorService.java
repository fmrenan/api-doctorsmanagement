package com.renanmuniz.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.DoctorInsertDTO;
import com.renanmuniz.backend.dto.SpecialtyDTO;
import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;
import com.renanmuniz.backend.repositories.DoctorRepository;
import com.renanmuniz.backend.repositories.SpecialtyRepository;
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
	public List<DoctorDTO> findAll(String name, String crm, String phone, String cellPhone, String specialtyName, String cep) {
		name = (name.equals("0")) ? null : name;
		crm = (crm.equals("0")) ? null : crm;
		phone = (phone.equals("0")) ? null : phone;
		cellPhone = (cellPhone.equals("0")) ? null : cellPhone;
		cep = (cep.equals("0")) ? null : cep;
		
		Specialty specialty = (specialtyName.equals("0")) ? null : specialtyRepository.findByName(specialtyName);
		
		List<Doctor> doctors = repository.findAll(name, crm, phone, cellPhone, specialty, cep);
		
		return doctors.stream().map(doc -> new DoctorDTO(doc, doc.getSpecialties())).collect(Collectors.toList());		
	}
	
	@Transactional(readOnly = true)
	public DoctorDTO findById(Long id) {
		Optional<Doctor> obj = repository.findById(id);
		Doctor entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		if(!entity.isActive()) {
			throw new ResourceNotFoundException("Entity not found");
		}
		
		return new DoctorDTO(entity, entity.getSpecialties());
	}
	
	@Transactional
	public DoctorDTO insert(DoctorInsertDTO dto) {
		Doctor entity = new Doctor(); 
		convertDtoToEntity(dto, entity);
		
		entity.setAddress(addressService.findAddressByCep(dto.getCep()));
		entity = repository.save(entity);
		
		return new DoctorDTO(entity, entity.getSpecialties());
	}
	
	@Transactional
	public DoctorDTO update(Long id, DoctorInsertDTO dto) {
		try {
			Doctor entity = repository.getOne(id); 
			convertDtoToEntity(dto, entity);
			
			entity.setAddress(addressService.findAddressByCep(dto.getCep()));
			entity = repository.save(entity);
			
			return new DoctorDTO(entity, entity.getSpecialties());
		}catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	
	@Transactional
	public void softDelete(Long id) {
		try {
			Doctor entity = repository.getOne(id); 
						
			entity.setActive(false);
			
			entity = repository.save(entity);
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
	
	private void convertDtoToEntity(DoctorInsertDTO dto, Doctor entity) {
		entity.setName(dto.getName());
		entity.setCrm(dto.getCrm());
		entity.setPhone(dto.getPhone());
		entity.setCellPhone(dto.getCellPhone());
		entity.getSpecialties().clear();
		
		for(SpecialtyDTO specDto : dto.getSpecialties()) {
			Specialty specialty = specialtyRepository.getOne(specDto.getId());
			
			entity.getSpecialties().add(specialty);
		}		
	}
	
}