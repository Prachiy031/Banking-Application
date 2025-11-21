package org.create.bankingApplication.account_service.model.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Response DTO from User Service*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserServiceDTO {
	
	private Long userId;

    private String firstName;

    private String lastName;

    private String emailId;

    private String password;

    private String identificationNumber;

    private String authenticationId;
}
