package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.application.exception.*;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;
@Service
public class DeliveryService {
    private final DeliveryRepository repo;
    private final AssignmentRepository assignments;
    private final UserRepository users;
    private final CurrentUserService current;
    private final ResourceRepository resources;
    private final NeedRepository needs;
    private final StorageService storage;
    public DeliveryService(DeliveryRepository r,AssignmentRepository a,UserRepository u,CurrentUserService c,ResourceRepository re,NeedRepository n,
        StorageService s) {
        repo=r;
        assignments=a;
        users=u;
        current=c;
        resources=re;
        needs=n;
        storage=s;
    }
    @Transactional
    public DeliveryResponse create(DeliveryRequest r) {
        var a=assignments.findById(r.assignmentId()).orElseThrow(()->new NotFoundException("Asignación no encontrada"));
        var v=users.findById(r.volunteerId()).orElseThrow(()->new NotFoundException("Voluntario no encontrado"));
        if(v.getRole()!=Role.VOLUNTEER)throw new ConflictException("El usuario no es VOLUNTEER");
        if(current.get().getRole()==Role.VOLUNTEER&&!v.getId().equals(current.id()))throw new ForbiddenException("Un voluntario solo puede operar sobre sí mismo");
        if(repo.existsByAssignmentIdAndStatus(a.getId(),DeliveryStatus.COMPLETED))throw new ConflictException("La asignación ya tiene una entrega completada");
        var d=new Delivery();
        d.setAssignment(a);
        d.setVolunteer(v);
        d.setDeliveryDate(r.deliveryDate()==null?Instant.now():r.deliveryDate());
        d.setObservations(r.observations());
        d.setStatus(DeliveryStatus.SCHEDULED);
        return to(repo.save(d));
    }
    public PageResponse<DeliveryResponse> find(int page,int size) {
        Pageable p=PageRequest.of(page,Math.min(size,100),Sort.by(Sort.Direction.DESC,"deliveryDate"));
        Page<Delivery> x=current.get().getRole()==Role.VOLUNTEER?repo.findByVolunteerId(current.id(),p):repo.findAll(p);
        return PageResponse.from(x.map(this::to));
    }
    public DeliveryResponse get(UUID id) {
        var d=repo.findById(id).orElseThrow(()->new NotFoundException("Entrega no encontrada"));
        if(current.get().getRole()==Role.VOLUNTEER&&!d.getVolunteer().getId().equals(current
            .id()))throw new ForbiddenException("No puedes consultar esta entrega");
        return to(d);
    }
    @Transactional
    public DeliveryResponse changeStatus(UUID id,DeliveryStatusRequest r) {
        var d=repo.findById(id).orElseThrow(()->new NotFoundException("Entrega no encontrada"));
        if(current.get().getRole()==Role.VOLUNTEER&&!d.getVolunteer().getId().equals(current
            .id()))throw new ForbiddenException("No puedes modificar esta entrega");
        if(!valid(d.getStatus(),r.status()))throw new DomainException("Cambio de estado de entrega no permitido");
        d.setStatus(r.status());
        if(r.status()==DeliveryStatus.COMPLETED)complete(d);
        return to(repo.save(d));
    }
    public DeliveryResponse addEvidence(UUID id,org.springframework.web.multipart.MultipartFile file) {
        var d=repo.findById(id).orElseThrow(()->new NotFoundException("Entrega no encontrada"));
        if(current.get().getRole()==Role.VOLUNTEER&&!d.getVolunteer().getId().equals(current
            .id()))throw new ForbiddenException("No puedes modificar esta entrega");
        try {
            d.setEvidencePath(storage.store(file));
            return to(repo.save(d));
        } catch(Exception e) {
            throw new DomainException("No se pudo almacenar la evidencia");
        }
    }
    private void complete(Delivery d) {
        var a=d.getAssignment();
        var x=a.getResource();
        x.setStatus(x.getAvailableQuantity()==0?ResourceStatus.DELIVERED:ResourceStatus.AVAILABLE);
        resources.save(x);
        var n=a.getNeed();
        if(n.remainingQuantity()==0)n.setStatus(NeedStatus.FULFILLED);
        else n.setStatus(NeedStatus.IN_PROGRESS);
        needs.save(n);
    }
    private boolean valid(DeliveryStatus a,DeliveryStatus b) {
        if(a==b)return true;
        if(a==DeliveryStatus.SCHEDULED)return EnumSet.of(DeliveryStatus.COMPLETED,DeliveryStatus.FAILED,DeliveryStatus.CANCELLED).contains(b);
        return false;
    }
    private DeliveryResponse to(Delivery d) {
        return new DeliveryResponse(d.getId(),d.getAssignment().getId(),d.getVolunteer().getId(),d.getVolunteer().getName(),d.getDeliveryDate(),d
            .getObservations(),d.getStatus(),d.getEvidencePath(),d.getCreatedAt());
    }
}
