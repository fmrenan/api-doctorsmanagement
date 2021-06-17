package com.renanmuniz.backend.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;

public class DoctorDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Não pode ser vazio")
	@Size(max = 120, message = "Não pode conter mais de 120 caracteres.")
	private String name;
	
	@NotBlank(message = "Não pode ser vazio")
	@Digits(fraction = 0, integer = 7, message = "Deve ser um número com no máximo 7 dígitos")
	private String crm;
	
	@NotBlank(message = "Não pode ser vazio")
	@Digits(fraction = 0, integer = 12, message="Deve ser um número com no máximo 12 dígitos")
	private String phone;
	
	@NotBlank(message = "Não pode ser vazio")
	@Digits(fraction = 0, integer = 12, message="Deve ser um número com no máximo 12 dígitos")
	private String cellPhone;
	
	@NotBlank(message = "Não pode ser vazio")
	@Digits(fraction = 0, integer = 8, message="Deve ser um número com no máximo 12 dígitos")
	private String cep;
	
	@NotNull
	@Size(min = 2, message="Deve conter pelo menos duas especialidades")
	private Set<SpecialtyDTO> specialties = new HashSet<>();
	
	public DoctorDTO() {};
	
	public DoctorDTO(Long id, String name, String crm, String phone, String cellPhone, String cep) {
		this.id = id;
		this.name = name;
		this.crm = crm;
		this.phone = phone;
		this.cellPhone = cellPhone;
		this.cep = cep;
	}
	
	public DoctorDTO(Doctor entity) {
		id = entity.getId();
		name = entity.getName();
		crm = entity.getCrm();
		phone = entity.getPhone();
		cellPhone = entity.getCellPhone();
		cep = entity.getCep();
	}
	
	public DoctorDTO(Doctor entity, Set<Specialty> specialties) {
		this(entity);
		
		specialties.forEach(spec -> this.specialties.add(new SpecialtyDTO(spec)));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Set<SpecialtyDTO> getSpecialties() {
		return specialties;
	}
	
	
	
	
}
