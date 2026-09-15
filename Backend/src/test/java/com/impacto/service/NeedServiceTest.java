package com.impacto.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.impacto.dto.*;
import com.impacto.entity.*;
import com.impacto.repository.*;
import org.junit.jupiter.api.*;
import java.util.*;
class NeedServiceTest {
    @Test void rejectsFulfilledNeedWhenQuantityMissing() {
        var repo=mock(NeedRepository.class);
        var fam=mock(FamilyRepository.class);
        var child=mock(ChildRepository.class);
        var s=new NeedService(repo,fam,child);
        var f=new Family();
        f.setStatus(FamilyStatus.ACTIVE);
        var id=UUID.randomUUID();
        when(fam.findById(id)).thenReturn(Optional.of(f));
        assertThrows(RuntimeException.class,()->s.create(new NeedRequest(id,null,NeedType.COMPUTER,Priority.HIGH,"x",1,NeedStatus.FULFILLED)));
    }
}
