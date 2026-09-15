package com.impacto.service;
import com.impacto.dto.*;
import com.impacto.mapper.EntityMapper;
import com.impacto.exception.NotFoundException;
import com.impacto.entity.*;
import com.impacto.repository.FamilyRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class FamilyService {
    private final FamilyRepository repo;
    private final EntityMapper mapper;
    public FamilyService(FamilyRepository r,EntityMapper m) {
        repo=r;
        mapper=m;
    }
    public FamilyResponse create(FamilyRequest r) {
        var f=new Family();
        apply(f,r);
        return mapper.toFamilyResponse(repo.save(f));
    }
    public PageResponse<FamilyResponse> find(String municipality,FamilyStatus status,int page,int size,String sort,String dir) {
        Pageable p=PageRequest.of(page,Math.min(size,100),Sort.by("asc".equalsIgnoreCase(dir)?Sort.Direction.ASC:Sort.Direction.DESC,sort));
        Page<Family> x=municipality!=null&&status!=null?repo.findByMunicipalityContainingIgnoreCaseAndStatus(municipality,status,p):municipality!=null?repo
            .findByMunicipalityContainingIgnoreCase(municipality,p):status!=null?repo.findByStatus(status,p):repo.findAll(p);
        return new PageResponse<>(x.map(mapper::toFamilyResponse).getContent(),x.getNumber(),x.getSize(),x.getTotalElements(),x.getTotalPages(),x.isFirst(),x
            .isLast());
    }
    public FamilyResponse get(UUID id) {
        return mapper.toFamilyResponse(repo.findById(id).orElseThrow(()->new NotFoundException("Familia no encontrada")));
    }
    public FamilyResponse update(UUID id,FamilyRequest r) {
        var f=repo.findById(id).orElseThrow(()->new NotFoundException("Familia no encontrada"));
        apply(f,r);
        return mapper.toFamilyResponse(repo.save(f));
    }
    public void delete(UUID id) {
        if(!repo.existsById(id))throw new NotFoundException("Familia no encontrada");
        repo.deleteById(id);
    }
    private void apply(Family f,FamilyRequest r) {
        f.setContactName(r.contactName());
        f.setPhone(r.phone());
        f.setAddress(r.address());
        f.setMunicipality(r.municipality());
        f.setStatus(r.status());
    }
}
