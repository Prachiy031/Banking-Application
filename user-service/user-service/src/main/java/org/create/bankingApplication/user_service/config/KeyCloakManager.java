package org.create.bankingApplication.user_service.config;

import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KeyCloakManager {
	private final KeyCloakProperties keyCloakProperties;

    /*
      Returns the KeyCloak instance for the specified realm
      [ a helper class that gives you a ready-to-use RealmResource 
      (Keycloak Admin API entry point) based on your application’s config ] 
     */
	
    public RealmResource getKeyCloakInstanceWithRealm() {

        return keyCloakProperties.getKeycloakInstance().realm(keyCloakProperties.getRealm());
    }
}
