package org.create.bankingApplication.user_service.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
 Request DTO used for creating user 
 */
public class UserCreateDTO {
	
	@NotNull(message = "User's first name is required")
	private String firstName;
	
	@NotNull(message = "User's last name is required")
	private String lastName;
	
	@Pattern(regexp="\\d{10}", message = "User's contact must have 10 digits in it")
	private String contactNo;
	
	@Email(message = "Invalid email")
	private String emailId;
	
	@NotBlank(message = "Password is required")
	@Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$",
	    message = "Password must be 8-20 chars, contain upper, lower, digit, and special character"
	)
	private String password;
}
