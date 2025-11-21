package org.create.bankingApplication.fund_transfer_service.model.dto.mapper;

import org.create.bankingApplication.fund_transfer_service.model.dto.FundTransferDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.requestDTO.FundTransferRequestDTO;
import org.create.bankingApplication.fund_transfer_service.model.dto.responseDTO.FundTransferResponseDTO;
import org.create.bankingApplication.fund_transfer_service.model.entity.FundTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FundTransferMapper {
	
	@Mapping(target = "transactionMessage" , ignore = true)      //will be set in service layer
	FundTransferResponseDTO toDTO(FundTransfer fundTransfer);
	
	
	@Mapping(target = "transactionReferenceId" , ignore = true)		//will be set in service layer
	@Mapping(target = "fundTransferId" , ignore = true)			//will be set in db automatically
	@Mapping(target = "transactionStatus" , ignore = true)		//will be set in service layer
	@Mapping(target = "transferType", ignore = true)			//will be set in service layer
	@Mapping(target = "transferredOn" , ignore = true)			//will be set in db automatically
	FundTransfer toEntity(FundTransferRequestDTO fundTransferRequestDto);
	
	
	//for internal use only
	@Mapping(target = "status", source = "transactionStatus")
	FundTransferDTO toFullDTO(FundTransfer fundTransfer);
}
