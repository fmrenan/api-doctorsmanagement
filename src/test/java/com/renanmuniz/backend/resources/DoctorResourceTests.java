package com.renanmuniz.backend.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.DoctorInsertDTO;
import com.renanmuniz.backend.services.DoctorService;
import com.renanmuniz.backend.services.exceptions.ResourceNotFoundException;
import com.renanmuniz.backend.tests.Factory;

@WebMvcTest(DoctorResource.class)
public class DoctorResourceTests {
	
	private Long existingId;
	private Long nonExistingId;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DoctorService service;
	
	private DoctorDTO doctorDTO;
	private DoctorInsertDTO doctorInsertDTO;
	private List<DoctorDTO> doctorsDTO;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 0L;
		
		doctorDTO = Factory.createDoctorDTO();
		doctorInsertDTO = Factory.createDoctorInsertDTO();
		doctorsDTO = new ArrayList<DoctorDTO>();
		
		when(service.findAll(any(), any(), any(), any(), any(), any())).thenReturn(doctorsDTO);
		
		when(service.findById(existingId)).thenReturn(doctorDTO);
		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
		when(service.insert(any())).thenReturn(doctorDTO);
		
		when(service.update(eq(existingId), any())).thenReturn(doctorDTO);
		when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		
		doNothing().when(service).softDelete(existingId);
		doThrow(ResourceNotFoundException.class).when(service).softDelete(nonExistingId);		
	}
	
	
	@Test
	public void findAllShouldReturnListOfDoctor() throws Exception{
		mockMvc.perform(get("/doctors").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnDoctorWhenIdExists() throws Exception {
		mockMvc.perform(get("/doctors/{id}", existingId).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.name").exists())
		.andExpect(jsonPath("$.crm").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		mockMvc.perform(get("/doctors/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnCreatedAndDoctorDTO() throws Exception {
		String jsonBody = mapper.writeValueAsString(doctorInsertDTO);
		
		mockMvc.perform(post("/doctors").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.name").exists())
		.andExpect(jsonPath("$.crm").exists());
	}
	
	
	@Test
	public void updateShouldReturnDoctorDTOWhenIdExists() throws Exception{
		String jsonBody = mapper.writeValueAsString(doctorDTO);
		
		mockMvc.perform(put("/doctors/{id}", existingId).content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name").exists())
			.andExpect(jsonPath("$.crm").exists());
	}
	
	@Test
	public void updateShoudReturnNotFoundWhenIdDoesNotExists() throws Exception{
		String jsonBody = mapper.writeValueAsString(doctorDTO);
		
		mockMvc.perform(put("/doctors/{id}", nonExistingId).content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception{
		mockMvc.perform(delete("/doctors/{id}", existingId))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
		mockMvc.perform(delete("/doctors/{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
}
