package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.application.exception.*;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
public class ResourceService {
    private final ResourceRepository repo;
    private final DonationRepository donations;
    private final ResourceCodeService codes;
    private final AssignmentRepository assignments;
    public ResourceService(ResourceRepository r,DonationRepository d,ResourceCodeService c,AssignmentRepository a) {
        repo=r;
        donations=d;
        codes=c;
        assignments=a;
    }
    @Transactional
    public ResourceResponse create(ResourceRequest r) {
        var d=donations.findById(r.donationId()).orElseThrow(()->new NotFoundException("Donación no encontrada"));
        if(d.getType()!=DonationType.MATERIAL)throw new DomainException("Solo las donaciones MATERIAL pueden generar recursos");
        var x=new Resource();
        x.setCode(codes.next());
        x.setType(r.type());
        x.setDescription(r.description());
        x.setQuantity(r.quantity());
        x.setAvailableQuantity(r.quantity());
        x.setDonation(d);
        return to(repo.save(x));
    }
    public PageResponse<ResourceResponse> find(ResourceStatus status,int page,int size) {
        var p=PageRequest.of(page,Math.min(size,100),Sort.by(Sort.Direction.DESC,"createdAt"));
        var x=status==null?repo.findAll(p):repo.findByStatus(status,p);
        return PageResponse.from(x.map(this::to));
    }
    public ResourceResponse get(UUID id) {
        return to(repo.findById(id).orElseThrow(()->new NotFoundException("Recurso no encontrado")));
    }
    @Transactional
    public ResourceResponse changeStatus(UUID id,ResourceStatusRequest r) {
        var x=repo.findById(id).orElseThrow(()->new NotFoundException("Recurso no encontrado"));
        if(!allowed(x.getStatus(),r.status()))throw new DomainException("Cambio de estado de recurso no permitido");
        x.setStatus(r.status());
        return to(repo.save(x));
    }
    public TraceabilityResponse trace(UUID id) {
        var x=repo.findById(id).orElseThrow(()->new NotFoundException("Recurso no encontrado"));
        var d=x.getDonation();
        var timeline=new ArrayList<TraceEvent>();
        timeline.add(new TraceEvent("DONATION","RECEIVED",d.getCreatedAt(),d.getDonor().getName(),d.getDescription()));
        timeline.add(new TraceEvent("RESOURCE",x.getStatus().name(),x.getCreatedAt(),"system",x.getCode()));
        for(var a:assignments.findByResourceId(id)) {
            timeline.add(new TraceEvent("ASSIGNMENT","ASSIGNED",a.getCreatedAt(),a.getCreatedBy()==null?"system":a.getCreatedBy().toString(),"Need="+a
                .getNeed().getId()+", quantity="+a.getQuantity()));
        }
        return new TraceabilityResponse(x.getId(),x.getCode(),x.getType(),d.getId(),d.getDonor().getName(),d.getCampaign()==null?null:d
            .getCampaign().getId(),d.getCampaign()==null?null:d.getCampaign().getName(),timeline);
    }
    private boolean allowed(ResourceStatus a,ResourceStatus b) {
        if(a==b)return true;
        if(a==ResourceStatus.AVAILABLE)return EnumSet.of(ResourceStatus.RESERVED,ResourceStatus.ASSIGNED,ResourceStatus.DAMAGED,ResourceStatus
            .LOST).contains(b);
        if(a==ResourceStatus.RESERVED)return EnumSet.of(ResourceStatus.AVAILABLE,ResourceStatus.ASSIGNED,ResourceStatus.DAMAGED,ResourceStatus
            .LOST).contains(b);
        if(a==ResourceStatus.ASSIGNED)return EnumSet.of(ResourceStatus.DELIVERED,ResourceStatus.DAMAGED,ResourceStatus.LOST).contains(b);
        return false;
    }
    private ResourceResponse to(Resource x) {
        return new ResourceResponse(x.getId(),x.getCode(),x.getType(),x.getDescription(),x.getQuantity(),x.getAvailableQuantity(),x.getStatus(),x
            .getDonation().getId(),x.getCreatedAt(),x.getUpdatedAt());
    }
}
