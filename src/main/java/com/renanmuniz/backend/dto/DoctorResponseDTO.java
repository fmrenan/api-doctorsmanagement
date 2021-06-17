package com.renanmuniz.backend.dto;

import java.util.Set;

import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;

public class DoctorResponseDTO extends DoctorDTO{
	private static final long serialVersionUID = 1L;

	private AddressDTO address;

	public DoctorResponseDTO(Doctor entity, Set<Specialty> specialties, AddressDTO address) {
		super(entity, specialties);
		this.address = address;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	
}