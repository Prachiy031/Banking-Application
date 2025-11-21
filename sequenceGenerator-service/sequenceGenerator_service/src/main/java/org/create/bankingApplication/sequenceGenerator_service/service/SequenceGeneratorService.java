package org.create.bankingApplication.sequenceGenerator_service.service;

import org.create.bankingApplication.sequenceGenerator_service.model.entity.SequenceGenerator;

public interface SequenceGeneratorService {
	
	/*return type SequenceGenerator entity 
	 as this service is internal service 
	 and not exposed as an  api ..so that’s why dtos aren’t require and exposing entity is ok
	 */
	SequenceGenerator createSequence();
}
