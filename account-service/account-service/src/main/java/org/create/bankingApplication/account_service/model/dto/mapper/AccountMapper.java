package org.create.bankingApplication.account_service.model.dto.mapper;

import org.create.bankingApplication.account_service.model.dto.AccountDTO;
import org.create.bankingApplication.account_service.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/*we are using mapstruct for mapping entity->dto and dto->entity*/
/*
@Mapper(componentModel = "spring") .. makes your mapper a Spring-managed bean
*/

@Mapper(componentModel = "spring")
public interface AccountMapper {
	
	/// Entity -> DTO
	//as entity store these values as enum but dto store them as string
	//customised mapping
    @Mapping(target = "accountStatus", expression = "java(account.getAccountStatus().name())")
    @Mapping(target = "accountType", expression = "java(account.getAccountType().name())")
    AccountDTO toDto(Account account);

    // DTO -> Entity
    //take these value from services not from client{dto}
    //as same dto we are using for request and response 
    @Mapping(target = "accountNumber", ignore = true)  // system sets later ...[don't set from dto{client} which might be null]
    @Mapping(target = "accountStatus", ignore = true)  // system sets later  ...[don't set from dto{client} which might be null]
    @Mapping(target = "accountId", ignore = true)  // system sets later ...[don't set from dto{client} which might be null]
    //string value from dto to enum value in entity
    @Mapping(target = "accountType", expression = "java(AccountType.valueOf(accountDto.getAccountType()))")
    @Mapping(target = "accountOpeningDate", ignore = true)
    Account toEntity(AccountDTO accountDto);
    
    //to update every field from accountDTO to entity
    //as enum->string mapping and vice versa is already handled above while converting so no need to mention those here again
    @Mapping(target = "accountOpeningDate", ignore = true)
    void updateEntityFromDto(AccountDTO dto, @MappingTarget Account entity);
}
