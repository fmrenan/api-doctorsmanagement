package com.renanmuniz.backend.resources;

import java.util.List;

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

import com.renanmuniz.backend.dto.DoctorDTO;
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
	
	@PostMapping
	public ResponseEntity<DoctorDTO> insert(@RequestBody DoctorDTO dto){
		dto = service.insert(dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<DoctorDTO> update(@PathVariable Long id, @RequestBody DoctorDTO dto){
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		
		return ResponseEntity.ok().build();
	}
	
}