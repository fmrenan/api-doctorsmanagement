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
		
		return doctor;		
	}
	
	public static DoctorDTO createDoctorDTO() {
		Doctor doctor = createDoctor();
		
		return new DoctorDTO(doctor, doctor.getSpecialties());
	}	
	
	public static Address createAddress() {
		return  new Address("29315736", "Valentim Fullin", "", "Jardim Itapemirim", 
				"Cachoeiro de Itapemirim", "ES", "", "", "", "");
	}
	
	public static Specialty createSpecialty() {
		 return new Specialty(1L, "Alergologia");
	}

	public static DoctorInsertDTO createDoctorInsertDTO() {
		DoctorInsertDTO insertDto = new DoctorInsertDTO(createDoctor());
		insertDto.setCep("29315736");
		
		return insertDto;
	}
	
}
