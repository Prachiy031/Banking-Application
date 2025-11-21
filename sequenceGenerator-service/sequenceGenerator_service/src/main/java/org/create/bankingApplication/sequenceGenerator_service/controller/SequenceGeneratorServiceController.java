package org.create.bankingApplication.sequenceGenerator_service.controller;

import org.create.bankingApplication.sequenceGenerator_service.model.entity.SequenceGenerator;
import org.create.bankingApplication.sequenceGenerator_service.service.SequenceGeneratorService;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sequence")
public class SequenceGeneratorServiceController {
	
	private final SequenceGeneratorService sequenceGeneratorService;
	
	/*
	 Create account number in sequence manner
	 return SequenceGenerator entity
	 */
	
	@PostMapping
	public SequenceGenerator createSequence() {
		return sequenceGeneratorService.createSequence();
	}
}
