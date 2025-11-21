package org.create.bankingApplication.user_service.model.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data                 
@NoArgsConstructor    
@AllArgsConstructor   
@Builder  
/*
 	Request DTO for updating user profile
 */
public class UserUpdateDTO {
	
	@NotNull(message = "User's first name is required")
	private String firstName;
	
	@NotNull(message = "User's last name is required")
	private String lastName;
	
	private String gender;
	
	@Pattern(regexp="\\d{10}", message = "User's contact must have 10 digits in it")	
	private String contactNo;
	
	@NotNull(message = "User's address required")
	private String address;
	
	private String occupation;
	
	private String maritalStatus;
	
	@NotNull(message = "User's nationality required")
	private String nationality;
}
