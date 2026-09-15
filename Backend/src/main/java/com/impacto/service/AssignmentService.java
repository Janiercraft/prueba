package com.impacto.service;
import com.impacto.dto.*;
import com.impacto.exception.*;
import com.impacto.entity.*;
import com.impacto.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class AssignmentService {
    private final AssignmentRepository repo;
    private final NeedRepository needs;
    private final ResourceRepository resources;
    public AssignmentService(AssignmentRepository r,NeedRepository n,ResourceRepository re) {
        repo=r;
        needs=n;
        resources=re;
    }
    @Transactional
    public AssignmentResponse create(AssignmentRequest r) {
        var n=needs.findById(r.needId()).orElseThrow(()->new NotFoundException("Necesidad no encontrada"));
        var x=resources.findById(r.resourceId()).orElseThrow(()->new NotFoundException("Recurso no encontrado"));
        if(x.getStatus()!=ResourceStatus.AVAILABLE||x.getAvailableQuantity()<r.quantity())throw new ConflictException("El recurso no está disponible en cantidad suficiente");
        if(n.getStatus()==NeedStatus.CANCELLED||n.getStatus()==NeedStatus.FULFILLED)throw new ConflictException("La necesidad no acepta nuevas asignaciones");
        if(r.quantity()>n.remainingQuantity())throw new ConflictException("La cantidad supera la necesidad pendiente");
        x.setAvailableQuantity(x.getAvailableQuantity()-r.quantity());
        x.setStatus(x.getAvailableQuantity()==0?ResourceStatus.ASSIGNED:ResourceStatus.RESERVED);
        n.setStatus(NeedStatus.IN_PROGRESS);
        n.setFulfilledQuantity(n.getFulfilledQuantity()+r.quantity());
        var a=new Assignment();
        a.setNeed(n);
        a.setResource(x);
        a.setQuantity(r.quantity());
        var saved=repo.save(a);
        needs.save(n);
        resources.save(x);
        return to(saved);
    }
    public PageResponse<AssignmentResponse> find(int page,int size) {
        return PageResponse.from(repo.findAll(PageRequest.of(page,Math.min(size,100),Sort.by(Sort.Direction.DESC,"createdAt"))).map(this::to));
    }
    public AssignmentResponse get(UUID id) {
        return to(repo.findById(id).orElseThrow(()->new NotFoundException("Asignación no encontrada")));
    }
    private AssignmentResponse to(Assignment a) {
        return new AssignmentResponse(a.getId(),a.getNeed().getId(),a.getResource().getId(),a.getResource().getCode(),a.getQuantity(),a.getCreatedAt());
    }
}
