package org.create.bankingApplication.user_service.service.implementation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

//import org.create.bankingApplication.user_service.config.KeyCloakManager;
import org.create.bankingApplication.user_service.exceptions.EmptyFieldsException;
import org.create.bankingApplication.user_service.exceptions.ResourceConflictException;
import org.create.bankingApplication.user_service.exceptions.ResourceNotFoundException;
import org.create.bankingApplication.user_service.externalService.AccountService;
import org.create.bankingApplication.user_service.model.Status;
import org.create.bankingApplication.user_service.model.dto.UserCreateDTO;
import org.create.bankingApplication.user_service.model.dto.UserDTO;
import org.create.bankingApplication.user_service.model.dto.UserUpdateDTO;
import org.create.bankingApplication.user_service.model.dto.UserUpdateStatusDTO;
import org.create.bankingApplication.user_service.model.dto.response.Response;
import org.create.bankingApplication.user_service.model.entity.User;
import org.create.bankingApplication.user_service.model.entity.UserProfile;
import org.create.bankingApplication.user_service.model.external.Account;
import org.create.bankingApplication.user_service.model.mapper.UserMapper;
import org.create.bankingApplication.user_service.repository.UserRepository;
import org.create.bankingApplication.user_service.service.interfaces.KeyCloak;
import org.create.bankingApplication.user_service.service.interfaces.UserService;
import org.create.bankingApplication.user_service.utils.FieldChecker;
import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j     //for logging 
@Transactional      //manage db transactions
@RequiredArgsConstructor  //Generates a constructor with required arguments.
							//Required arguments are final fields and fields with constraints such as @NotNull. 
public class UserServiceImplementation implements UserService{
		
	private final KeyCloak keyCloakService;
	private final UserRepository userRepository;
	private final AccountService accountService;
	@Autowired
	private UserMapper userMapper;
	
	//instead of hardcoding responses store those in applications.prop so these configurations 
	//can be injected anywhere easily
	@Value("${spring.application.success}")
    private String responseCodeSuccess;

    @Value("${spring.application.not_found}")
    private String responseCodeNotFound;
	
	/*	createNewUser method for creating new user
	 	{parameter object of UserCreateDTO containing user data came from controller}
		{return type Reponse of user creation}
	*/
	@Override
    public Response createNewUser(UserCreateDTO userCreateDto) {
		
		//check for user already created for given email id
		List<UserRepresentation> userRepresenatations  = keyCloakService.readUsersByEmailId(userCreateDto.getEmailId());
		//if list contain any email id registered then
		if(userRepresenatations.size()>0) {
			log.error("This Email Id is already registered as a user");
			throw new ResourceConflictException("This Email Id is already registered as a user");
		}
		
		/*Prepare a Keycloak UserRepresentation 
		 object with the user's details
		 */
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setFirstName(userCreateDto.getFirstName());
		userRepresentation.setLastName(userCreateDto.getLastName());
		userRepresentation.setUsername(userCreateDto.getEmailId());
		userRepresentation.setEmail(userCreateDto.getEmailId());
		userRepresentation.setEmailVerified(false);
		userRepresentation.setEnabled(false);	
		
		/*
		 Prepare a Keycloak CredentialRepresentation 
		 object with the user's credentials details
		 */
		CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userCreateDto.getPassword());   //set password's value
        credentialRepresentation.setTemporary(false);              //set password as temporary as initially its no permanent
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation)); //add credentials to userrepresenation class by adding credentials into single element immuatable list  
        
        /*call keyCloak's API here our method name and apis method name are same 
         don't confuse that its calling same method, both methods are from different classes*/
        Integer userCreationResponse = keyCloakService.createNewUser(userRepresentation);
        
        
        
        
        
        //if user is created in keycloak {that is user data get create into keycloak}
        //then build UserProfile and User entity class
        //all fields are not set here while creating those entity 
        //due to minimal info required for registration , other info will get update later 
        if(userCreationResponse.equals(201)) {        	
        	//create userprofile and user => 
        	//list of UserRepresenation class' objects
        	List<UserRepresentation> representations = keyCloakService.readUsersByEmailId(userCreateDto.getEmailId());
        	
        	//create UserProfile Entity class' obj
        	UserProfile userProfile = UserProfile.builder()
        								.firstName(userCreateDto.getFirstName())
        								.lastName(userCreateDto.getLastName())
        								.build();
        	
        	//Create User Entity class' obj
        	User user = User.builder()
        			.emailId(userCreateDto.getEmailId())
        			.contactNo(userCreateDto.getContactNo())
        			.status(Status.PENDING)
        			.userProfile(userProfile)
        			.authenticationId(representations.get(0).getId())
        			.identificationNo(UUID.randomUUID().toString())
        			.build();
        	
        	//save entities in UserRepository that is add data into db
        	userRepository.save(user);
        	
        	//return reponse of user creation {customize}
        	return Response.builder()
        			.responseMessage("User Created Successfully")
        			.responseCode(responseCodeSuccess)
        			.build();
        			
        }
        //else throw exception
        throw new RuntimeException("User with identification number not found");
	}
	
	/*
	 for reading any sort of data 
	 you need to
	 1.retrieve data from entity {local db}
	 2.retrieve data from userrepresentation {authenticated data}
	 3.convert entity to dto
	 4.merge{set} data from userrepresentation into dto
	 5.return that dto
	 */
	
	/*readUsers method 
	{return type list of userDto}*/
    @Override
	public List<UserDTO> readUsers(){
		
		/*we need single response containing 
		 data from db and keycloak db as both contain some different fields and some same fields
		 */
		
		List<User> usersList = userRepository.findAll();
		
		/*for linking keycloak users with
		 local db users easily and fast use map*/
		
		Map<String, UserRepresentation> userRepresentationMap = keyCloakService.readUsersByAuthenticationId(usersList.stream()         //retrieve users's data using authentication id
																						.map(user -> user.getAuthenticationId())       //map them using authentication id
																						.collect(Collectors.toList()))                 //create list 
                																		.stream()
                																		.collect(Collectors.toMap(UserRepresentation::getId, Function.identity()));    //convert into map having key=>keycloak's id, value=>userRepresentation class object
		
		/*return reponse by 
		 merging user entity and userRepresentation
		 1.create userDto for each entity
		 2.merge data from userrepresenation into userDto*/
		
		return usersList.stream().map(user -> {
            UserDTO userDto = userMapper.toDto(user);             //convert each entity to DTO
            UserRepresentation userRepresentation = userRepresentationMap.get(user.getAuthenticationId());   //get userRepresenattion class' object using authentication id
            userDto.setUserId(user.getUserId());            //set userId using user object {entity}
            userDto.setEmailId(userRepresentation.getEmail());   //set emailId using userRepresentation obj
            userDto.setIdentificationNo(user.getIdentificationNo());   //set identificationNumber
            return userDto;                                    //return userDto object
        }).collect(Collectors.toList());           			   //store userDto object into list
	}
	
	
		/*	readUser by authentication id 
    	{return type UserDto}*/
    
    public UserDTO readUserByAuthenticationId(String authenticationId) {
		
		User user = userRepository.findUserByAuthenticationId(authenticationId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found on the server"));
		
		UserRepresentation userRepresentation = keyCloakService.readUserByAuthenticationId(authenticationId);
		UserDTO userDto = userMapper.toDto(user);
		userDto.setEmailId(userRepresentation.getEmail());     //set email from userRepresentation object data into dto object
		return userDto;		
	}
	
	/*	
	 read user by id 
	 {return type userDto}
	 {parameter userId}
	 here read only db data not keycloak data
	*/
    public UserDTO readUserById(Long userId) {
		
		UserDTO userDto = userRepository.findById(userId)
				.map(user->userMapper.toDto(user))
				.orElseThrow(() -> new ResourceNotFoundException("User not found on the server"));
		return userDto;
		
	}
	
	/*	
	 read user by acc id {userDto}
	 */
	public UserDTO readUserByAccountId(String accountId) {
		
		//ReponseEntity<Account> response representing HTTP response with header body code parts 
		ResponseEntity<Account> response = accountService.readByAccountNumber(accountId);
		if(Objects.isNull(response.getBody())) {
			throw new ResourceNotFoundException("Account not found on server");
		}
		Long userId = response.getBody().getUserId();
		return userRepository.findById(userId)
							.map(user->userMapper.toDto(user))
							.orElseThrow(() -> new ResourceNotFoundException("User not found on the server"));
	}
	
	/*	update User Status 
	 	{return type Response}
	 	{Parameter UserUpdateStatusDTO's object containing status}
	 */
	
	@Override
	public Response updateUserStatus(Long userId, UserUpdateStatusDTO userUpdateStatusDto) {
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on the server"));
		
		//check all manadatory fields are filled by user
		if(FieldChecker.hasEmptyFields(user)) {
			//if yes then log error message
			log.error("User has not filled every mandatory fields");
			throw new EmptyFieldsException("Please fill all mandatory details ",responseCodeNotFound);
		}
		
		//if all fields are filled 
		//then check status is approved for user or not
		if(userUpdateStatusDto.getStatus().equals(Status.APPROVED)) {
			//update data in keycloak db
			UserRepresentation userRepresentation = keyCloakService.readUserByAuthenticationId(user.getAuthenticationId());
			userRepresentation.setEnabled(true);          //enabled user
			userRepresentation.setEmailVerified(true);		//verified email
			keyCloakService.updateUserByRepresentation(userRepresentation);		 //update in keycloak	
		}else if (userUpdateStatusDto.getStatus().equals(Status.DISABLED)) {
			UserRepresentation userRepresentation = keyCloakService.readUserByAuthenticationId(user.getAuthenticationId());
		    userRepresentation.setEnabled(false);           //disabled user
		    userRepresentation.setEmailVerified(true);		//previously it was verified email
		    keyCloakService.updateUserByRepresentation(userRepresentation);
		    user.setStatus(Status.DISABLED);
		}else if (userUpdateStatusDto.getStatus().equals(Status.REJECTED)) {
			UserRepresentation userRepresentation = keyCloakService.readUserByAuthenticationId(user.getAuthenticationId());
		    userRepresentation.setEnabled(false);          //disabled user
		    userRepresentation.setEmailVerified(false);	  //unverify email
		    keyCloakService.updateUserByRepresentation(userRepresentation);
		    user.setStatus(Status.REJECTED);
		}
		
		//update status in db
		user.setStatus(userUpdateStatusDto.getStatus());
		
		//save in db
		userRepository.save(user);
		
		return Response.builder()
				.responseMessage("User updated successfully")
				.responseCode(responseCodeSuccess)
				.build();		
	}
	
	/* Update User using user id
	   {return type Response}
	   {parameter user id, UserUpdateDTO's object conatining updated data}
	 */
	@Override
	public Response updateUser(Long userId, UserUpdateDTO userUpdateDto) {
		
		User user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User Not found on the server"));
		
		/*any update in fields will automatically reflect in user using userUpdateDTO fields
			source -> userUpdateDto
			target -> user
		  also nested objects get update automatically
		*/
		userMapper.updateUserFromDto(userUpdateDto, user);
		userRepository.save(user);
		return Response.builder()
				.responseMessage("User Updated successfully")
				.responseCode(responseCodeSuccess)
				.build();
	}
	
	
}
