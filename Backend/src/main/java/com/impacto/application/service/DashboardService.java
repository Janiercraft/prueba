package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
@Service
public class DashboardService {
    private final FamilyRepository families;
    private final ChildRepository children;
    private final NeedRepository needs;
    private final ResourceRepository resources;
    private final DonationRepository donations;
    private final DeliveryRepository deliveries;
    private final UserRepository users;
    public DashboardService(FamilyRepository f,ChildRepository c,NeedRepository n,ResourceRepository r,DonationRepository d,DeliveryRepository de,
        UserRepository u) {
        families=f;
        children=c;
        needs=n;
        resources=r;
        donations=d;
        deliveries=de;
        users=u;
    }
    public DashboardResponse get() {
        long totalFamilies=families.count(), totalChildren=children.count(), pending=needs.countByStatus(NeedStatus.PENDING), fulfilled=needs
            .countByStatus(NeedStatus.FULFILLED), available=resources.countByStatus(ResourceStatus.AVAILABLE), delivered=resources
                .countByStatus(ResourceStatus.DELIVERED), totalDon=donations.count(), completed=deliveries.countByStatus(DeliveryStatus
                    .COMPLETED), volunteers=users.findAll().stream().filter(u->u.getRole()==Role.VOLUNTEER).count();
        BigDecimal money=donations.sumAmount(DonationType.MONEY,DonationStatus.RECEIVED);
        return new DashboardResponse(totalFamilies,totalChildren,pending,fulfilled,available,delivered,money,totalDon,completed,volunteers,List.of());
    }
}
