package org.create.bankingApplication.user_service.controller;

import java.util.List;

import org.create.bankingApplication.user_service.model.dto.UserCreateDTO;
import org.create.bankingApplication.user_service.model.dto.UserDTO;
import org.create.bankingApplication.user_service.model.dto.UserUpdateDTO;
import org.create.bankingApplication.user_service.model.dto.UserUpdateStatusDTO;
import org.create.bankingApplication.user_service.model.dto.response.Response;
import org.create.bankingApplication.user_service.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController     //Controller + Responsebody...no need to add responsebody for methods as return type is json not views
@Slf4j        //logging
@RequestMapping("/api/users")     //base api for all apis
@RequiredArgsConstructor       //for initialising all final fields in controller 
@Validated               //enables validation for parameters also

public class UserController {
	
	/*
	 @Valid => validate the field
	 @RequestBody => binds request{json format} to dto object [i.e map json->dto with help of spring-web ] 
	 */
	
	/*
	 Request body → use @Valid on DTO + annotations inside DTO

	Path/query params → put validation annotations directly on the parameter 
	 */
	
	private final UserService userService;
	/*these method names need not manadatorily same as mentioned in UserService and its implementation
	 but for sake of readability method names are kept same*/
	
	/*
	 create new user
	 return type responseEntity
	 parameter UserDTO class' object
	 */	
	
	//public endpoint => no need of authentication
	@PostMapping("/register")     //endpoint for calling this method
	public ResponseEntity<Response> createNewUser(@Valid @RequestBody UserCreateDTO userCreateDto){
		log.info("creating user with: ", userCreateDto.toString());
        return ResponseEntity.ok(userService.createNewUser(userCreateDto));  //Return HTTP 200 OK with the userService.createNewUser(userCreateDto) as body
	}
	
	/*
	 retrieves user with user's authentication id
	 return type responseEntity of UserDTO
	 parameter authentication id 
	 */	
	@GetMapping("/auth/{authenticationId}")
	@PreAuthorize("isAuthenticated() and #authenticationId == authentication.name")   //isAuthenticated()=>Spring Expression Language (SpEL) function that evaluates the security context{whether user has logged in}
																					// #authenticationId == authentication.name => checks whether authentication id from url and token matches
	public ResponseEntity<UserDTO> readUserByAuthenticationId(@NotNull @PathVariable String authenticationId){
		log.info("Reading user's information using authenticationId");
		return ResponseEntity.ok(userService.readUserByAuthenticationId(authenticationId));
	}
	
	
	/*
	 retrieves user with user's user id
	 return type responseEntity of UserDTO
	 parameter user id 
	 */	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> readUserById(@NotNull @PathVariable Long userId){
		log.info("Reading user's information using userId");
		return ResponseEntity.ok(userService.readUserById(userId));
	}
	
	
	/*
	 retrieves user with user's account id
	 return type responseEntity of UserDTO
	 parameter user id 
	 */	
	@GetMapping("/accounts/{accountId}")
	public ResponseEntity<UserDTO> readUserByAccountId(@NotNull @PathVariable String accountId){
		log.info("Reading user's information using accountId");
		return ResponseEntity.ok(userService.readUserByAccountId(accountId));
	}
	
	
	/*
	 retrieves all users
	 return type responseEntity of UserDTO
	 */	
	@GetMapping
	public ResponseEntity<List<UserDTO>> readUsers(){
		log.info("Reading all user's information");
		return ResponseEntity.ok(userService.readUsers());
	}
	
	
	/*
      Updates the status of a user
     
     return The response entity containing the updated user and HTTP status
      param id The ID of the user to update.
      param userUpdate The updated status of the user.
     */
    @PatchMapping("/updateStatus/{id}")    //part of data is changing other data will remain same
    public ResponseEntity<Response> updateUserByStatus(@NotNull @PathVariable Long id,@Valid @RequestBody UserUpdateStatusDTO userUpdateDto) {
        log.info("Updating the user with: ", userUpdateDto.toString());
        return new ResponseEntity<>(userService.updateUserStatus(id, userUpdateDto), HttpStatus.OK);
    }
    
    
    /*
      Updates a user with the given ID.
     
      param id The ID of the user to update.
      param userUpdate The updated user information.
      return type The response with the updated user information.
     */
    
    @PutMapping("/{id}")      //whole data is changing ...in request all data should be mentioned otherwise will keep it as null
    public ResponseEntity<Response> updateUser(@NotNull @PathVariable Long id,@Valid @RequestBody UserUpdateDTO userUpdateDto) {
        return new ResponseEntity<>(userService.updateUser(id, userUpdateDto), HttpStatus.OK);
    }
    
    @GetMapping("/bank-test")
    @PreAuthorize("hasAuthority('SCOPE_email')")
    public String testMethod(){
    	return "Hello"; 
    }
	
	
}
