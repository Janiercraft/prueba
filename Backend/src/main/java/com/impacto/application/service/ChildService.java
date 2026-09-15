package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.application.exception.NotFoundException;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class ChildService {
    private final ChildRepository repo;
    private final FamilyRepository families;
    public ChildService(ChildRepository r,FamilyRepository f) {
        repo=r;
        families=f;
    }
    public ChildResponse create(ChildRequest r) {
        var f=families.findById(r.familyId()).orElseThrow(()->new NotFoundException("Familia no encontrada"));
        var c=new Child();
        apply(c,r,f);
        return to(c=repo.save(c));
    }
    public PageResponse<ChildResponse> find(UUID familyId,int page,int size) {
        Pageable p=PageRequest.of(page,Math.min(size,100),Sort.by("name"));
        Page<Child> x=familyId==null?repo.findAll(p):repo.findByFamilyId(familyId,p);
        return PageResponse.from(x.map(this::to));
    }
    public ChildResponse get(UUID id) {
        return to(repo.findById(id).orElseThrow(()->new NotFoundException("Niño no encontrado")));
    }
    public ChildResponse update(UUID id,ChildRequest r) {
        var c=repo.findById(id).orElseThrow(()->new NotFoundException("Niño no encontrado"));
        var f=families.findById(r.familyId()).orElseThrow(()->new NotFoundException("Familia no encontrada"));
        apply(c,r,f);
        return to(repo.save(c));
    }
    public void delete(UUID id) {
        repo.delete(repo.findById(id).orElseThrow(()->new NotFoundException("Niño no encontrado")));
    }
    private void apply(Child c,ChildRequest r,Family f) {
        c.setFamily(f);
        c.setName(r.name());
        c.setBirthDate(r.birthDate());
        c.setSchool(r.school());
        c.setGrade(r.grade());
        c.setStatus(r.status());
    }
    private ChildResponse to(Child c) {
        return new ChildResponse(c.getId(),c.getFamily().getId(),c.getFamily().getContactName(),c.getName(),c.getBirthDate(),c.getSchool(),c.getGrade(),c
            .getStatus(),c.getCreatedAt(),c.getUpdatedAt());
    }
}
