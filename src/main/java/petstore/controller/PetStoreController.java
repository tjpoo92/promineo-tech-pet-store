package petstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import petstore.controller.model.PetStoreCustomer;
import petstore.controller.model.PetStoreData;
import petstore.controller.model.PetStoreEmployee;
import petstore.service.PetStoreService;

@RestController
@RequestMapping("/pet_store_api")
@Slf4j
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping("/pet_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData postPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PutMapping("/pet_store/{petStoreId}")
	@ResponseStatus(code = HttpStatus.OK)
	public PetStoreData putPetStore(@RequestBody PetStoreData petStoreData, @PathVariable Long petStoreId) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating Pet Store {} with {}", petStoreId, petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PostMapping("/pet_store/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee postEmployee(@RequestBody PetStoreEmployee petStoreEmployee, @PathVariable Long petStoreId) {
		log.info("Creating employee: {} for Pet Store {}", petStoreEmployee, petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	@PostMapping("/pet_store/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer postCustomer(@RequestBody PetStoreCustomer petStoreCustomer, @PathVariable Long petStoreId) {
		log.info("Creating customer: {} for Pet Store {}", petStoreCustomer, petStoreId);
		return petStoreService.saveCustomer(petStoreId,petStoreCustomer);
	}
	
}
