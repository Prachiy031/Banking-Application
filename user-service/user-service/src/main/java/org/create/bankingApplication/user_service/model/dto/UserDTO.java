package org.create.bankingApplication.user_service.model.dto;

import org.create.bankingApplication.user_service.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 Respose DTO which gives reponse to api having these fields
 */
public class UserDTO {
	
	private Long userId;
	
	private String emailId;
	
	private String identificationNo;
	
	private String authenticationId;
	
	private Status status;
	
	/*
	 Nested object => extra info about user
	 */
	
	private UserProfileDTO userProfileDto;  
	
}
