package org.create.bankingApplication.account_service.externalService;

import org.create.bankingApplication.account_service.model.dto.external.SequenceGeneratorServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/*feign client {connects other service from current service}*/
@FeignClient(name= "sequence-generator-service")
public interface SequenceService {
	/*
      Generates a new account number.
     
      return the generated account number as a SequenceGeneratorServiceDTO object.
     */
	
	/*
	 Method names, method return type may not match with service method anmes and return types
	 only thing should match with them is http method, endpoint, and actual data{request response what json is sent or received}
	 */
	
	
    @PostMapping("/sequence")
    SequenceGeneratorServiceDTO generateAccountNumber();
}
