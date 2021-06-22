package com.renanmuniz.backend.services;

import java.util.HashMap;
import java.util.Map;

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
	
	@Autowired
	private RestTemplate restTemplate;	
	
	private String host = "https://viacep.com.br/ws/";
	private String hostFormat = "/json/";

    private Address getAddressFromApi(String cep) {
    	Map<String, String> uriVariables = new HashMap<>();
    	uriVariables.put("cep", cep);    	

        ResponseEntity<Address> resp = restTemplate.getForEntity(host + "{cep}" + hostFormat ,
          Address.class, uriVariables);
        
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
    		obj.setId(null);
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