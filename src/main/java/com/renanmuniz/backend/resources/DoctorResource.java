package com.renanmuniz.backend.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.DoctorInsertDTO;
import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.services.DoctorService;
import com.sipios.springsearch.anotation.SearchSpec;

@RestController
@RequestMapping("/doctors")
public class DoctorResource {
	
	@Autowired
	private DoctorService service;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<DoctorDTO> findById(@Valid @PathVariable Long id){
		DoctorDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/specialty/{specialty}")
	public ResponseEntity<List<DoctorDTO>> findBySpecialty(@Valid @PathVariable Long specialty){
		List<DoctorDTO> doctors = service.findBySpecialty(specialty);
		
		return ResponseEntity.ok().body(doctors);
	}
	
	@GetMapping
	public ResponseEntity<List<DoctorDTO>> search(@SearchSpec Specification<Doctor> specs){
	    
		List<DoctorDTO> doctors = service.search(specs);
		
		return ResponseEntity.ok().body(doctors);
	}
	
	@PostMapping
	public ResponseEntity<DoctorDTO> insert(@Valid @RequestBody DoctorInsertDTO dto){
		DoctorDTO newDto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<DoctorDTO> update(@PathVariable Long id, @RequestBody DoctorInsertDTO dto){
		DoctorDTO newDto = service.update(id, dto);
		
		return ResponseEntity.ok().body(newDto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.softDelete(id);
		
		return ResponseEntity.ok().build();
	}
	
}