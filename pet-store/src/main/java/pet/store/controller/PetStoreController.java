package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

/*
 * This will allow Spring to map HTTP requests to specific methods. The URI for 
 * every request that is mapped to the controller must start with "/pet_store". You 
 * can control the class-level mapping by specifying "/pet_store" as the value inside 
 * the @RequestMapping annotation. 
 * 
 * @RestController – This tells Spring that this class is a REST controller. As such 
 * it expects and returns JSON in the request/response bodies. The default response 
 * status code is 200 (OK) if you don't specify something different. And finally, 
 * this annotation tells Spring to map HTTP requests to class methods. 
 * 
 * @RequestMapping("/pet_store") – This tells Spring that the URI for every HTTP 
 * request that is mapped to a method in this controller class must start with 
 * "/pet_store". 
 * 
 * @Slf4j – This is a Lombok annotation that creates an SLF4J logger. It adds the 
 * logger as an instance variable named log. Use it like this: 
 * 
 * log.info("This is a log line"):
 *  
 * Autowire (inject) the PetStoreService as an instance variable. 
 * 
 * Create a public method that maps an HTTP POST request to "/pet_store". The 
 * response status should be 201 (Created). Pass the contents of the request body as 
 * a parameter (type PetStoreData) to the method. (Use @RequestBody.) The method 
 * should return a PetStoreData object. Log the request. Call a method in the service 
 * class (savePetStore) that will insert or modify the pet store data. 
 * 
 * */

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	@Autowired
	private PetStoreService petStoreService;
		
	@PostMapping 
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore(
	@RequestBody PetStoreData petStoreData) {
		log.info("Creating pet store {}", petStoreData); 
		return petStoreService.savePetStore(petStoreData);
	} 
	
	/*
	 *     In the pet store controller, add a public method to update the pet 
	 *     store data. This method should: 
	 *     
	 *     Have an @PutMapping annotation to map PUT requests to this method. The 
	 *     annotation should specify that a pet store ID is present in the HTTP 
	 *     request URI. 
	 *     
	 *     Return a PetStoreData object. 
	 *     
	 *     The method parameters are the pet store ID and the pet store data. 
	 *     Don't forget the appropriate annotations to read the store ID from the 
	 *     request URI and the store data from the request body. 
	 *     
	 *     Set the pet store ID in the pet store data from the ID parameter. 
	 *     Log the method call. 
	 *     
	 *     Call the savePetStore method in the service class. 
	 *     
	 *     */
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(
			@PathVariable Long petStoreId, 
			@RequestBody PetStoreData petStoreData) 	{
		
		petStoreData.setPetStoreId(petStoreId);
		
		log.info("Updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	/*    Create a method in the controller that will add an employee to the 
	 * employee table. The method should be annotated with @PostMapping and 
	 * @ResponseStatus.  
	 * 
	 * @PostMapping: This must be configured to allow the caller to send an HTTP 
	 * POST request to "/pet_store/{petStoreId}/employee", where {petStoreId}  is 
	 * the ID of the pet store in which to add the employee (for example, 
	 * "/pet_store/1/employee"). Remember that the "/pet_store" part of the HTTP 
	 * URI is defined at the class level in the @RequestMapping annotation. 
	 * 
	 * @ResponseStatus: This should be configured to return a 201 (Created) 
	 * status code. 
	 * 
	 * The method should be public and return a PetStoreEmployee object. 
	 * 
	 * The method should accept the pet store ID and the PetStoreEmployee object 
	 * as parameters. The pet store ID is passed in the URI and the 
	 * PetStoreEmployee object is passed as JSON in the request body. 
	 * 
	 * Log the request. 
	 * 
	 * The method should call the saveEmployee method in the pet store service 
	 * and should return the results of that method call. 
	 * 
	 * */
	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee addEmployee (
			@PathVariable Long petStoreId, 
			@RequestBody PetStoreEmployee petStoreEmployee) {
		
		log.info("Adding employee {}", petStoreEmployee);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
		
	}

	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer addCustomer (
		@PathVariable Long petStoreId, 
		@RequestBody PetStoreCustomer petStoreCustomer) {
	
	log.info("Adding customer {}", petStoreCustomer);
	return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	
}
	/* Add a method to the controller that returns List<PetStoreData>. 
	 * Remember to add the @GetMapping annotation. This annotation does not take 
	 * a value (i.e., no ID) and the method takes no parameters. Create/call the 
	 * retrieveAllPetStores() method in the service class. 
	 * 
	 * */
	
	@GetMapping
	public List<PetStoreData> retrieveAllPetStores() {
		 return petStoreService.retrieveAllPetStores();
	}
	
	/*   
	 * Add a controller method to retrieve a single pet store given the pet store 
	 * ID. It will be very similar to the retrieve all pet stores method except 
	 * that the @GetMapping annotation will take the pet store ID that is passed 
	 * to the method as a parameter. Create/call the method in the service class.
	 *  
	 * */
	
	@GetMapping("/{petStoreId}")
	public PetStoreData retrievePetStoreById(
			@PathVariable Long petStoreId){
		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
	/* Add the deletePetStoreById() method in the controller. 
	 * It should take the pet store ID as a parameter (remember to use 
	 * @PathVariable). 
	 * 
	 * Add the @DeleteMapping annotation. This should specify that the pet store 
	 * ID is part of the URI. For example, the URI should be 
	 * http://localhost:8080/pet_store/{ID}, where {ID} is the ID of the pet 
	 * store to delete. 
	 * 
	 * Log the request. 
	 * 
	 * Call the deletePetStoreById() method in the service, passing the pet store 
	 * ID as a parameter. 
	 * 
	 * Return a Map<String, String> where the key is "message" and the value is a 
	 * deletion successful message. 
	 * 
	 * */
	
	@DeleteMapping("/pet_store/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId){
		log.info("Deleting pet store {}", petStoreId);
		
		petStoreService.deletePetStoreById(petStoreId);
		
		return Map.of("message", "Deletion of pet store with ID =" 
				+ petStoreId 
				+ " was successful!");
	}
}

