package com.renanmuniz.backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.renanmuniz.backend.dto.AddressDTO;

@Service
public class AddressService {

	private RestTemplate restTemplate = new RestTemplate();

    public AddressDTO getAddressByCep(String cep) {

        ResponseEntity<AddressDTO> resp = restTemplate.getForEntity("https://viacep.com.br/ws/"+cep+"/json/" ,
          AddressDTO.class);
        return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
    }    
}