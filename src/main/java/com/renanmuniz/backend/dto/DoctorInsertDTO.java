package com.renanmuniz.backend.dto;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import com.renanmuniz.backend.entities.Doctor;

public class DoctorInsertDTO extends DoctorDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Não pode ser vazio")
	@Digits(fraction = 0, integer = 8, message="Deve ser um número com no máximo 8 dígitos")
	private String cep;
	
	public DoctorInsertDTO() {};
	
	public DoctorInsertDTO(String cep) {
		super();
		this.cep = cep;
	}
	
	public DoctorInsertDTO(Doctor entity) {
		super(entity, entity.getSpecialties());
		cep = entity.getAddress().getCep();
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}	
}