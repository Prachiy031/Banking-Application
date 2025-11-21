package org.create.bankingApplication.user_service.model.mapper;

import org.create.bankingApplication.user_service.model.dto.UserDTO;
import org.create.bankingApplication.user_service.model.dto.UserUpdateDTO;
import org.create.bankingApplication.user_service.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface UserMapper {

    // Convert DTO -> Entity
    @Mappings({
        @Mapping(source = "userProfileDto", target = "userProfile"),
        @Mapping(target = "createdOnDate", ignore = true), // auto-set by DB
        @Mapping(target = "contactNo", ignore = true)      // not in UserDTO
    })
    User toEntity(UserDTO userDto);

    // Convert Entity -> DTO
    @Mappings({
        @Mapping(source = "userProfile", target = "userProfileDto")
    })
    UserDTO toDto(User user);

    // Update User from UpdateDTO
    @Mappings({
        @Mapping(target = "userProfile.firstName", source = "firstName"),
        @Mapping(target = "userProfile.lastName", source = "lastName"),
        @Mapping(target = "userProfile.gender", source = "gender"),
        @Mapping(target = "userProfile.address", source = "address"),
        @Mapping(target = "userProfile.occupation", source = "occupation"),
        @Mapping(target = "userProfile.maritalStatus", source = "maritalStatus"),
        @Mapping(target = "userProfile.nationality", source = "nationality"),
        @Mapping(target = "contactNo", source = "contactNo"),
        
     // IGNORE unmapped fields that shouldn't be updated
        @Mapping(target = "authenticationId", ignore = true),
        @Mapping(target = "createdOnDate", ignore = true),
        @Mapping(target = "emailId", ignore = true),
        @Mapping(target = "identificationNo", ignore = true),
        @Mapping(target = "status", ignore = true),
        @Mapping(target = "userId", ignore = true)
    })
    void updateUserFromDto(UserUpdateDTO dto, @MappingTarget User user);
}

