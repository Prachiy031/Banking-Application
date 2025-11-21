package org.create.bankingApplication.user_service.model.mapper;

import org.create.bankingApplication.user_service.model.dto.UserProfileDTO;
import org.create.bankingApplication.user_service.model.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfileDTO toDto(UserProfile userProfile);
    
    @Mapping(target = "userProfileId", ignore = true)
    UserProfile toEntity(UserProfileDTO userProfileDto);
}
