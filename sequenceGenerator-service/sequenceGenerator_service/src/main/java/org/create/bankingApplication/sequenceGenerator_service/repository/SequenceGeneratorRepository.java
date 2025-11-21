package org.create.bankingApplication.sequenceGenerator_service.repository;

import org.create.bankingApplication.sequenceGenerator_service.model.entity.SequenceGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SequenceGeneratorRepository extends JpaRepository<SequenceGenerator, Long>{
	
	//explicit query ...as count want in integer not in long{by default}
	//written JPQL {java persistence query language} ..spring jpa will executed this query
	@Query("SELECT COUNT(s) from SequenceGenerator s")
    int countAll();
	
	//derived query
	//check method name it should follow naming convention defined by spring data jpa {based upon name it will generate query}
	//fetch the record with the highest sequenceId, i.e., the latest sequence.
    SequenceGenerator findFirstByOrderBySequenceIdDesc();
}
