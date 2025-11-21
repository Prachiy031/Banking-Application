package org.create.bankingApplication.account_service.model.dto;

import org.create.bankingApplication.account_service.model.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountStatusUpdateDTO {

	private AccountStatus accountStatus;
	
}
