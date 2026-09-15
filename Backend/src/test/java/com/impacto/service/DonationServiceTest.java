package com.impacto.service;
import com.impacto.dto.DonationRequest;
import com.impacto.entity.*;
import com.impacto.repository.*;
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
        var s=new DonationService(d,donors,c,r,codes);
        var donor=new Donor();
        donor.setUser(new User());
        when(donors.findById(any())).thenReturn(Optional.of(donor));
        var req=new DonationRequest(UUID.randomUUID(),null,DonationType.MONEY,BigDecimal.ZERO,null,null,null,DonationStatus.RECEIVED);
        assertThrows(RuntimeException.class,()->s.create(req));
    }
}
