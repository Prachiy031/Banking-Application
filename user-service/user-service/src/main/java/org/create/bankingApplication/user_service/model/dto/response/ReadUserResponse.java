package org.create.bankingApplication.user_service.model.dto.response;

import org.create.bankingApplication.user_service.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

/*
Customized response will be visible to clients..that's why kept in package other that normal DTOs
*/

public class ReadUserResponse {
	
	private String firstName;
	
	private String lastName;
	
	private String contactNo;
	
	private String emailId;
	
	private Status status;
}
