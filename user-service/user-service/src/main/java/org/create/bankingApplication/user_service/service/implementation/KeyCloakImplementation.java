package org.create.bankingApplication.user_service.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.create.bankingApplication.user_service.config.KeyCloakManager;
import org.create.bankingApplication.user_service.service.interfaces.KeyCloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
/*
	Provides implementation of methods to do crud operations on keycloak
	{wrapped inbuilt methods to access keycloak apis
	e.g. .users(), .create(), .getStatus()...}
	Keycloak (KeycloakManager + KeycloakImplementation) → CRUD for Keycloak’s internal UserRepresentation.
 */
public class KeyCloakImplementation implements KeyCloak{
	
	private final KeyCloakManager keyCloakManager;
	
	/*create new User new keycloak system
	 return status indicating success or failure
	 parameter UserRepresentation object containing all info about user to store in keycloak
	 */
	
	@Override
    public Integer createNewUser(UserRepresentation userRepresentation) {
        return keyCloakManager.getKeyCloakInstanceWithRealm().users().create(userRepresentation).getStatus();
    }
	
	/*
	 retrieves list of users using list of authentication id
	 return type is list of UserRepresentation
	 parameter is list of authentication ids
	 
	 UserResource class is inbuilt in KeyCloak also .users is inbuilt...provided methods are built init
	 */
	@Override
	public List<UserRepresentation> readUsersByAuthenticationId(List<String> authenticationIds){
		return authenticationIds.stream()
						.map(authId -> { //for every authentication id
							UserResource userResource = keyCloakManager.getKeyCloakInstanceWithRealm().users().get(authId); //returns a UserResource (specific user from Keycloak identified by authId inside users section within keycloak realm).
							return userResource.toRepresentation();    //return useRepresentation object
						}).collect(Collectors.toList());    //convert into list
	}
	
	/*
	 retrieves list of users representations objects using email id
	 by default retrives list only
	 */
	@Override
	public List<UserRepresentation> readUsersByEmailId(String emailId){
		return keyCloakManager.getKeyCloakInstanceWithRealm().users().search(emailId);
	}
	
	/*
	 retrives single user representation using authentication id
	 */
	@Override
	public UserRepresentation readUserByAuthenticationId(String authenticationId) {
		return keyCloakManager.getKeyCloakInstanceWithRealm().users().get(authenticationId).toRepresentation();
	}
	
	/*
	 Update user using provided user representation
	 */
	@Override
	public void updateUserByRepresentation(UserRepresentation user) {
		keyCloakManager.getKeyCloakInstanceWithRealm().users()
						.get(user.getId()).update(user);
	}
	
	
	
}
