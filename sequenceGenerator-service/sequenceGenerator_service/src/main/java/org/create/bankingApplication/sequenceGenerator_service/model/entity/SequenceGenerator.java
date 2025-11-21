package org.create.bankingApplication.sequenceGenerator_service.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SequenceGenerator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sequenceId;
	
	private long accountNumber;
	
}
