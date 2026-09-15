package com.impacto.application.service;
import com.impacto.api.dto.DonationRequest;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.*;
import org.junit.jupiter.api.*;
import java.math.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class DonationServiceTest {
    @Test void rejectsNonPositiveMoney() {
        var d=mock(DonationRepository.class);
        var donors=mock(DonorRepository.class);
        var c=mock(CampaignRepository.class);
        var r=mock(ResourceRepository.class);
        var codes=mock(ResourceCodeService.class);
        var current=mock(CurrentUserService.class);
        var s=new DonationService(d,donors,c,r,codes,current);
        var donor=new Donor();
        donor.setUser(new User());
        when(current.get()).thenReturn(new User());
        when(donors.findById(any())).thenReturn(Optional.of(donor));
        var req=new DonationRequest(UUID.randomUUID(),null,DonationType.MONEY,BigDecimal.ZERO,null,null,null,DonationStatus.RECEIVED);
        assertThrows(RuntimeException.class,()->s.create(req));
    }
}
