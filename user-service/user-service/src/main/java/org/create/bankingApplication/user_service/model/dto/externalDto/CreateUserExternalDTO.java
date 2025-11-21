package org.create.bankingApplication.user_service.model.dto.externalDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 This DTO is used for external service Account service{for creating account for user}
 These fields are required from user-service 
 * */
public class CreateUserExternalDTO {
	
	private Long userId;
	
	private String firstName;
	
	private String lastName;
	
	private String identificationNumber;
}
