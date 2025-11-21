package org.create.bankingApplication.user_service.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data                 //generates getters and setters
@NoArgsConstructor    //generates constructor with no fields
@AllArgsConstructor   //generates constructor with all fields
@Builder   			  //build object
public class UserProfile {
	
	/* primary key */
	@Id     
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long userProfileId;
	
	private String firstName;
	
	private String lastName;
	
	private String gender;
	
	private String address;
	
	private String occupation;
	
	private String maritalStatus;
	
	private String nationality;
}
