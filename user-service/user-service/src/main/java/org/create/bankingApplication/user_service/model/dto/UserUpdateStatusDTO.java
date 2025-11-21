package org.create.bankingApplication.user_service.model.dto;

import org.create.bankingApplication.user_service.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/*
 Request DTO for updating userStatus
 */
public class UserUpdateStatusDTO {
	
	@NotNull(message = "Status is required")
	private Status status;
	
}
