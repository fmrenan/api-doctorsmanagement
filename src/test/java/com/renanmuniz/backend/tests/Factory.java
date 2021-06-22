package com.renanmuniz.backend.tests;

import com.renanmuniz.backend.dto.DoctorDTO;
import com.renanmuniz.backend.dto.DoctorInsertDTO;
import com.renanmuniz.backend.entities.Address;
import com.renanmuniz.backend.entities.Doctor;
import com.renanmuniz.backend.entities.Specialty;

public class Factory {

	public static Doctor createDoctor() {
		Doctor doctor = new Doctor(1L, "José", "1234567", "2830451020", "28999000099", createAddress());
		doctor.getSpecialties().add(createSpecialty());
		doctor.getSpecialties().add(createSecondSpecialty());
		
		return doctor;		
	}
	
	public static DoctorDTO createDoctorDTO() {
		Doctor doctor = createDoctor();
		
		return new DoctorDTO(doctor, doctor.getSpecialties());
	}	
	
	public static Address createAddress() {
		return  new Address("08090284", "Rua 03 de Outubro", "(Ch Três Meninas)", "Jardim Helena", 
				"São Paulo", "SP", "", "3550308", "1004", "7107");
	}
	
	public static Specialty createSpecialty() {
		 return new Specialty(1L, "Alergologia");
	}
	public static Specialty createSecondSpecialty() {
		 return new Specialty(2L, "Angiologia");
	}

	public static DoctorInsertDTO createDoctorInsertDTO() {
		Doctor entity = createDoctor();		
		DoctorInsertDTO insertDto = new DoctorInsertDTO(entity);
		insertDto.setCep("29315736");
		insertDto.setAddress(null);
		insertDto.setId(null);
		
		return insertDto;
	}

	public static Address createValidAddress() {
		return  new Address("29315-736", "Rua Valentim Fullin", "", "Jardim Itapemirim", 
				"Cachoeiro de Itapemirim", "ES", "", "", "", "");
	}
	
}
