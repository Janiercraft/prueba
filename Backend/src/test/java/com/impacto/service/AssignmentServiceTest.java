package com.impacto.service;
import com.impacto.dto.AssignmentRequest;
import com.impacto.entity.*;
import com.impacto.repository.*;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AssignmentServiceTest {
    @Test void rejectsUnavailableResource() {
        var a=mock(AssignmentRepository.class);
        var n=mock(NeedRepository.class);
        var r=mock(ResourceRepository.class);
        var s=new AssignmentService(a,n,r);
        var need=new Need();
        need.setStatus(NeedStatus.PENDING);
        need.setRequestedQuantity(1);
        need.setFulfilledQuantity(0);
        var res=new Resource();
        res.setStatus(ResourceStatus.ASSIGNED);
        res.setAvailableQuantity(0);
        when(n.findById(any())).thenReturn(Optional.of(need));
        when(r.findById(any())).thenReturn(Optional.of(res));
        assertThrows(RuntimeException.class,()->s.create(new AssignmentRequest(UUID.randomUUID(),UUID.randomUUID(),1)));
    }
}
