package com.impacto.mapper;
import org.mapstruct.Mapper;
import com.impacto.entity.*;
import com.impacto.dto.*;
@Mapper(componentModel="spring")
public interface EntityMapper {
    UserResponse toUserResponse(User u);
    FamilyResponse toFamilyResponse(Family f);
    CampaignResponse toCampaignResponse(Campaign c);
    ResourceResponse toResourceResponse(Resource r);
    AssignmentResponse toAssignmentResponse(Assignment a);
}
