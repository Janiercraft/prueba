package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.application.exception.*;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class DonorService {
    private final DonorRepository repo;
    private final UserRepository users;
    private final CurrentUserService current;
    public DonorService(DonorRepository r,UserRepository u,CurrentUserService c) {
        repo=r;
        users=u;
        current=c;
    }
    public DonorResponse create(DonorRequest r) {
        User u=r.userId()==null?null:users.findById(r.userId()).orElseThrow(()->new NotFoundException("Usuario no encontrado"));
        var d=new Donor();
        d.setUser(u);
        apply(d,r);
        return to(repo.save(d));
    }
    public PageResponse<DonorResponse> find(int page,int size) {
        return PageResponse.from(repo.findAll(PageRequest.of(page,Math.min(size,100),Sort.by("name"))).map(this::to));
    }
    public DonorResponse get(UUID id) {
        if(!isAdmin()&&!owns(id))throw new ForbiddenException("No puedes consultar otro donante");
        return to(repo.findById(id).orElseThrow(()->new NotFoundException("Donante no encontrado")));
    }
    public DonorResponse update(UUID id,DonorRequest r) {
        if(!isAdmin()&&!owns(id))throw new ForbiddenException("No puedes modificar otro donante");
        var d=repo.findById(id).orElseThrow(()->new NotFoundException("Donante no encontrado"));
        apply(d,r);
        return to(repo.save(d));
    }
    private boolean owns(UUID id) {
        return repo.findByUserId(current.id()).map(d->d.getId().equals(id)).orElse(false);
    }
    private boolean isAdmin() {
        return current.get().getRole()==Role.ADMIN;
    }
    private void apply(Donor d,DonorRequest r) {
        if(r.userId()!=null)d.setUser(users.findById(r.userId()).orElseThrow(()->new NotFoundException("Usuario no encontrado")));
        d.setName(r.name());
        d.setType(r.type());
        d.setEmail(r.email());
        d.setPhone(r.phone());
        d.setStatus(r.status());
    }
    private DonorResponse to(Donor d) {
        return new DonorResponse(d.getId(),d.getUser()==null?null:d.getUser().getId(),d.getName(),d.getType(),d.getEmail(),d.getPhone(),d.getStatus());
    }
}
