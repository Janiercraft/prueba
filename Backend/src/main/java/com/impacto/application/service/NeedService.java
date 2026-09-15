package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.application.exception.*;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class NeedService {
    private final NeedRepository repo;
    private final FamilyRepository families;
    private final ChildRepository children;
    public NeedService(NeedRepository r,FamilyRepository f,ChildRepository c) {
        repo=r;
        families=f;
        children=c;
    }
    public NeedResponse create(NeedRequest r) {
        return save(new Need(),r);
    }
    public PageResponse<NeedResponse> find(NeedStatus status,Priority priority,NeedType type,UUID familyId,int page,int size) {
        Pageable p=PageRequest.of(page,Math.min(size,100),Sort.by(Sort.Direction.DESC,"createdAt"));
        Specification<Need> spec=Specification.where(null);
        if(status!=null)spec=spec.and((r,q,cb)->cb.equal(r.get("status"),status));
        if(priority!=null)spec=spec.and((r,q,cb)->cb.equal(r.get("priority"),priority));
        if(type!=null)spec=spec.and((r,q,cb)->cb.equal(r.get("type"),type));
        if(familyId!=null)spec=spec.and((r,q,cb)->cb.equal(r.get("family").get("id"),familyId));
        Page<Need> x=repo.findAll(spec,p);
        return PageResponse.from(x.map(this::to));
    }
    public NeedResponse get(UUID id) {
        return to(repo.findById(id).orElseThrow(()->new NotFoundException("Necesidad no encontrada")));
    }
    public NeedResponse update(UUID id,NeedRequest r) {
        var n=repo.findById(id).orElseThrow(()->new NotFoundException("Necesidad no encontrada"));
        return save(n,r);
    }
    @Transactional
    public NeedResponse changeStatus(UUID id,NeedStatusRequest r) {
        var n=repo.findById(id).orElseThrow(()->new NotFoundException("Necesidad no encontrada"));
        if(n.getStatus()==NeedStatus.CANCELLED && r.status()!=NeedStatus.CANCELLED)throw new DomainException("Una necesidad cancelada no puede reabrirse");
        if(r.status()==NeedStatus.FULFILLED && n.remainingQuantity()>0)throw new DomainException("La necesidad no está completamente atendida");
        n.setStatus(r.status());
        return to(repo.save(n));
    }
    private NeedResponse save(Need n,NeedRequest r) {
        var f=families.findById(r.familyId()).orElseThrow(()->new NotFoundException("Familia no encontrada"));
        Child c=null;
        if(r.childId()!=null) {
            c=children.findById(r.childId()).orElseThrow(()->new NotFoundException("Niño no encontrado"));
            if(!c.getFamily().getId().equals(f.getId()))throw new ConflictException("El niño no pertenece a la familia");
        }
        n.setFamily(f);
        n.setChild(c);
        n.setType(r.type());
        n.setPriority(r.priority());
        n.setDescription(r.description());
        n.setRequestedQuantity(r.requestedQuantity());
        n.setStatus(r.status());
        if(n.getFulfilledQuantity()==null)n.setFulfilledQuantity(0);
        if(n.getStatus()==NeedStatus.FULFILLED && n.remainingQuantity()>0)throw new DomainException("No se puede marcar como FULFILLED sin cubrir la cantidad requerida");
        return to(repo.save(n));
    }
    private NeedResponse to(Need n) {
        return new NeedResponse(n.getId(),n.getFamily().getId(),n.getChild()==null?null:n.getChild().getId(),n.getType(),n.getPriority(),n.getStatus(),n
            .getDescription(),n.getRequestedQuantity(),n.getFulfilledQuantity(),n.remainingQuantity(),n.getCreatedAt(),n.getUpdatedAt());
    }
}
