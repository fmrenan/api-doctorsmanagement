package com.renanmuniz.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.renanmuniz.backend.entities.Address;
import com.renanmuniz.backend.repositories.AddressRepository;
import com.renanmuniz.backend.services.exceptions.AddressNotFoundException;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;
	
	private RestTemplate restTemplate = new RestTemplate();
	

    private Address getAddressFromApi(String cep) {

        ResponseEntity<Address> resp = restTemplate.getForEntity("https://viacep.com.br/ws/"+cep+"/json/" ,
          Address.class);
        
        if(resp.getBody().getCep() == null) {
        	throw new AddressNotFoundException("CEP não encontrado");
        }
        
        return resp.getBody();
    }
    
    public Address findAddressByCep(String cep) {
    	Address obj = repository.findByCep(cep);
		
    	if(obj == null ) {
    		obj = getAddressFromApi(cep);
    		obj.setCep(formataCep(obj.getCep()));
    		obj = insert(obj);
    	}    	
    	
    	return obj;
    }
    
    private Address insert(Address address) {
    	return repository.save(address);
    }
    
    private String formataCep(String cep){
    	   cep = cep.replaceAll("-", "");
    	   return cep;
    	}
}