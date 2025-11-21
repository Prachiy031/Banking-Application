package org.create.bankingApplication.user_service.service.interfaces;

import java.util.List;

import org.create.bankingApplication.user_service.model.dto.UserCreateDTO;
import org.create.bankingApplication.user_service.model.dto.UserDTO;
import org.create.bankingApplication.user_service.model.dto.UserUpdateDTO;
import org.create.bankingApplication.user_service.model.dto.UserUpdateStatusDTO;
import org.create.bankingApplication.user_service.model.dto.response.Response;

public interface UserService {
	
//	createUser method declaration {return type Reponse}
//	readAllUsers method declaration {return type list of userDto}
//	readUser by authentication id {return type UserDto}
//	update User Status {Response}
//	update user {reposne}
//	read user by id {userDto}
//	readuser by acc id {userDto}
	
	/*
	 Create new User using UserCreateDTO class and send back response{custom class already created}
	 */
	Response createNewUser(UserCreateDTO userCreateDto);
	
	/*
	 Read all Users data and return list of objects of UserDTO
	 */
	List<UserDTO> readUsers();
	
	/*
	 Read User By userId {primary key}
	 */
	UserDTO readUserById(Long userId);
	
	/*
	 Read single User by authentication Id and return UserDTO class' object
	 */
	UserDTO readUserByAuthenticationId(String authId);
	
	/*
	 Read User by his/her accountId
	 */
	UserDTO readUserByAccountId(String accountId);
	
	/*
	 Update User Status using UserUpdateStatusDTO class' object and return the 
	 response object indicating the success or failure of the operation 
	 */
	Response updateUserStatus(Long id, UserUpdateStatusDTO userUpdateStatusDto);
	
	/*
	 Update user data using id UserUpdateDTO class' object and and return Response 
	 */
	Response updateUser(Long id, UserUpdateDTO userUpdateDto); 
}
