package org.create.bankingApplication.sequenceGenerator_service.service.implementation;

import org.create.bankingApplication.sequenceGenerator_service.model.entity.SequenceGenerator;
import org.create.bankingApplication.sequenceGenerator_service.repository.SequenceGeneratorRepository;
import org.create.bankingApplication.sequenceGenerator_service.service.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SequenceGeneratorServiceImplementation implements SequenceGeneratorService{
	
	
	/*
	 sequence generation table includes single row only 
	 with two columns as sequence id and account number,
	 everytime when new account number needed, for sequence id 1
	 account number is incremented
	 
	 Sequence id never change only account number changes , thats why this is called as "SINGLETON SEQUNCE ROW PATTERN"
	 */
	
	private final SequenceGeneratorRepository sequenceRepository;
	
	@Override
	public SequenceGenerator createSequence() {
		
		log.info("Creating Account Number");
		
		return sequenceRepository.findById(1L)          //check for sequence id 1
				.map(
						sequence -> {sequence.setAccountNumber(sequence.getAccountNumber()+1);  //if found for that sequence increment account number
						return sequenceRepository.save(sequence);         //save in repo
						
					}).orElseGet(
							() -> sequenceRepository.save(SequenceGenerator.            //if sequence id doesent include then create it
												builder().
												accountNumber(1L).
												build()));
	}
}
