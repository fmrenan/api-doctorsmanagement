package com.renanmuniz.backend.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renanmuniz.backend.dto.DoctorInsertDTO;
import com.renanmuniz.backend.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DoctorResourceIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private Long existingId;
	private Long nonExistingId;
	
	@BeforeEach
	void setup() throws Exception{
		existingId = 1L;
		nonExistingId = 300L;
	}
	
	@Test
	public void findAllShouldReturnListOfDoctorDTO() throws Exception{
		mockMvc.perform(get("/doctors")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.[0].name").value("José Silva Ferreira"));
	}
	
	@Test
	public void insertShouldReturnDoctorDTOWhenValidParams() throws Exception{
		DoctorInsertDTO doctorDTO = Factory.createDoctorInsertDTO();
		
		String expectedName = doctorDTO.getName();
		String expectedCep = doctorDTO.getCep();
		
		String jsonBody = mapper.writeValueAsString(doctorDTO);
		
		mockMvc.perform(post("/doctors").content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value(existingId+1))
			.andExpect(jsonPath("$.name").value(expectedName))
			.andExpect(jsonPath("$.address.cep").value(expectedCep));
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenInvalidValidParams() throws Exception{
		DoctorInsertDTO doctorDTO = Factory.createDoctorInsertDTO();
		doctorDTO.setCrm("12345678");
		doctorDTO.setCep("123456789");
		
		String jsonBody = mapper.writeValueAsString(doctorDTO);
		
		mockMvc.perform(post("/doctors").content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.errors").exists())
			.andExpect(jsonPath("$.errors").isArray());
	}
	
	
	@Test
	public void updateShouldReturnProductDTOWhenIdExists() throws Exception{
		DoctorInsertDTO doctorDTO = Factory.createDoctorInsertDTO();
		
		String expectedName = doctorDTO.getName();
		String expectedCep = doctorDTO.getCep();
		
		String jsonBody = mapper.writeValueAsString(doctorDTO);
		
		mockMvc.perform(put("/doctors/{id}", existingId).content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(existingId))
			.andExpect(jsonPath("$.name").value(expectedName))
			.andExpect(jsonPath("$.address.cep").value(expectedCep));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdNonExists() throws Exception{
		DoctorInsertDTO doctorDTO = Factory.createDoctorInsertDTO();
		
		String jsonBody = mapper.writeValueAsString(doctorDTO);
		
		mockMvc.perform(put("/doctors/{id}", nonExistingId).content(jsonBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception{
		
		mockMvc.perform(delete("/doctors/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdNonExists() throws Exception{
		
		mockMvc.perform(delete("/doctors/{id}", nonExistingId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
