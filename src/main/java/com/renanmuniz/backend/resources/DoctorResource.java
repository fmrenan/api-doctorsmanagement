package com.renanmuniz.backend.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.renanmuniz.backend.dto.DoctorResponseDTO;
import com.renanmuniz.backend.services.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorResource {
	
	@Autowired
	private DoctorService service;
	
	@GetMapping
	public ResponseEntity<List<DoctorDTO>> findAll(){
		List<DoctorDTO> doctors = service.findAll();
		
		return ResponseEntity.ok().body(doctors);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<DoctorDTO> findById(@Valid @PathVariable Long id){
		DoctorDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<DoctorResponseDTO> insert(@Valid @RequestBody DoctorDTO dto){
		DoctorResponseDTO newDto = service.insert(dto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(newDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<DoctorDTO> update(@PathVariable Long id, @RequestBody DoctorDTO dto){
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.softDelete(id);
		
		return ResponseEntity.ok().build();
	}
	
}