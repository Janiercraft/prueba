package com.impacto.api.mapper;

import com.impacto.api.dto.AssignmentResponse;
import com.impacto.api.dto.CampaignResponse;
import com.impacto.api.dto.FamilyResponse;
import com.impacto.api.dto.ResourceResponse;
import com.impacto.api.dto.UserResponse;
import com.impacto.domain.model.Assignment;
import com.impacto.domain.model.Campaign;
import com.impacto.domain.model.CampaignStatus;
import com.impacto.domain.model.Family;
import com.impacto.domain.model.FamilyStatus;
import com.impacto.domain.model.Resource;
import com.impacto.domain.model.ResourceStatus;
import com.impacto.domain.model.ResourceType;
import com.impacto.domain.model.Role;
import com.impacto.domain.model.User;
import com.impacto.domain.model.UserStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-15T17:25:14-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.100.v20260826-1225, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
@Component
public class EntityMapperImpl implements EntityMapper {

    @Override
    public UserResponse toUserResponse(User u) {
        if ( u == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String email = null;
        Role role = null;
        UserStatus status = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = u.getId();
        name = u.getName();
        email = u.getEmail();
        role = u.getRole();
        status = u.getStatus();
        createdAt = u.getCreatedAt();
        updatedAt = u.getUpdatedAt();

        UserResponse userResponse = new UserResponse( id, name, email, role, status, createdAt, updatedAt );

        return userResponse;
    }

    @Override
    public FamilyResponse toFamilyResponse(Family f) {
        if ( f == null ) {
            return null;
        }

        UUID id = null;
        String contactName = null;
        String phone = null;
        String address = null;
        String municipality = null;
        FamilyStatus status = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = f.getId();
        contactName = f.getContactName();
        phone = f.getPhone();
        address = f.getAddress();
        municipality = f.getMunicipality();
        status = f.getStatus();
        createdAt = f.getCreatedAt();
        updatedAt = f.getUpdatedAt();

        FamilyResponse familyResponse = new FamilyResponse( id, contactName, phone, address, municipality, status, createdAt, updatedAt );

        return familyResponse;
    }

    @Override
    public CampaignResponse toCampaignResponse(Campaign c) {
        if ( c == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        String description = null;
        BigDecimal goal = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        CampaignStatus status = null;

        id = c.getId();
        name = c.getName();
        description = c.getDescription();
        goal = c.getGoal();
        startDate = c.getStartDate();
        endDate = c.getEndDate();
        status = c.getStatus();

        CampaignResponse campaignResponse = new CampaignResponse( id, name, description, goal, startDate, endDate, status );

        return campaignResponse;
    }

    @Override
    public ResourceResponse toResourceResponse(Resource r) {
        if ( r == null ) {
            return null;
        }

        UUID id = null;
        String code = null;
        ResourceType type = null;
        String description = null;
        Integer quantity = null;
        Integer availableQuantity = null;
        ResourceStatus status = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = r.getId();
        code = r.getCode();
        type = r.getType();
        description = r.getDescription();
        quantity = r.getQuantity();
        availableQuantity = r.getAvailableQuantity();
        status = r.getStatus();
        createdAt = r.getCreatedAt();
        updatedAt = r.getUpdatedAt();

        UUID donationId = null;

        ResourceResponse resourceResponse = new ResourceResponse( id, code, type, description, quantity, availableQuantity, status, donationId, createdAt, updatedAt );

        return resourceResponse;
    }

    @Override
    public AssignmentResponse toAssignmentResponse(Assignment a) {
        if ( a == null ) {
            return null;
        }

        UUID id = null;
        Integer quantity = null;
        Instant createdAt = null;

        id = a.getId();
        quantity = a.getQuantity();
        createdAt = a.getCreatedAt();

        UUID needId = null;
        UUID resourceId = null;
        String resourceCode = null;

        AssignmentResponse assignmentResponse = new AssignmentResponse( id, needId, resourceId, resourceCode, quantity, createdAt );

        return assignmentResponse;
    }
}
