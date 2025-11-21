package org.create.bankingApplication.transaction_service.model.mapper;

import java.util.List;

import org.create.bankingApplication.transaction_service.model.dto.TransactionServiceRequestDTO;
import org.create.bankingApplication.transaction_service.model.entity.Transaction;
import org.create.bankingApplication.transaction_service.model.responseDTO.TransactionServiceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionServiceMapper {
	
	
	//one biggest advantage for using mapstruct is 
	//there is no need to write methods for collection conversion 
	//entity list -> dto list and dto list -> entity list
	//below methods will be used for the same if there is single object passed as argument
	//so either declare method which accepts list of use stream in service method
	//and call below methods for each object(dto/entity)
	//Here argument is passed
	
	
	//Extra mapping is mentioned below as data types differs in entity or dto
	
	@Mapping(source = "transactionType", target = "transactionType")   //source type : enum target type : string
	@Mapping(source = "transactionStatus", target = "transactionStatus") //source type : enum target type : string
	@Mapping(source = "transactionDate", target = "localDateTime")    //source name : transactionDate target name : localDateTime
	TransactionServiceResponseDTO toDTO(Transaction transactionEntity);
	
	
	@Mapping(source = "transactionType", target = "transactionType")   //source type : string target type : enum
	@Mapping(target = "transactionStatus", ignore = true)             
	@Mapping(target = "accountId", ignore = true)
	@Mapping(target = "referenceId", ignore = true)
	@Mapping(target = "transactionDate", ignore = true)
	@Mapping(target = "transactionId", ignore = true)
	@Mapping(source = "description", target = "comments")         //source name : description target name : comments
	Transaction toEntity(TransactionServiceRequestDTO transactionServiceRequestDto);
	
	/*
	 MapStruct will reuse the single-object mappings to handle lists.
	 No need to add mappings again
	 Mapstruct will reuse mappings as mentioned above for single object 
	 */
	List<TransactionServiceResponseDTO> toDTO(List<Transaction> transactionEntities);
	
	List<Transaction> toEntity(List<TransactionServiceRequestDTO> transactionServiceRequestDtos);
	
	
	
}
