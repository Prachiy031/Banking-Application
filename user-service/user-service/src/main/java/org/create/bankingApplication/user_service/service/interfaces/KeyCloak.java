package org.create.bankingApplication.user_service.service.interfaces;

import java.util.List;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeyCloak {
	/*this interface will be used for authentication/authorization purpose
	e.g. user login/logout, password management, roles and premissions, token management, etc.
	there are normal crud operations available for database
	but crud operations also needed for keycloak to for authenticated data storing
	
	normal crud and keycloak crud will happen parallely*/
	
	/*UserDTO is app’s object 
	While userRepresentation is keycloak’s object

	Keycloak APIs expect UserRepresentation objects{ UserRepresentation  class name cant be changed ..it is what accepted by keycloak ….also you don’t have to create UserRepresentation  class ..it is built by keycloak automatically
	Its built in class of keycloak under org.keycloak.representations.idm  package}. 
	It’s like a translator between your app and Keycloak
	 */
	
	//keycloak crud methods available for autheticated data in keycloak db
	
	/*
	 create new user return status indicating success or failure
	 */
	Integer createNewUser(UserRepresentation user);
	
	/*
	 retrieves list of users using list of authentication id
	 */
	List<UserRepresentation> readUsersByAuthenticationId(List<String> authenticationIds);
	
	/*
	 retrieves list of users representations using email id
	 by default retrives list only
	 */
	List<UserRepresentation> readUsersByEmailId(String emailId);
	
	/*
	 retrives single user representation using authentication id
	 */
	UserRepresentation readUserByAuthenticationId(String authenticationId);
	
	/*
	 Update user using provided user representation
	 */
	void updateUserByRepresentation(UserRepresentation user);
	
}
