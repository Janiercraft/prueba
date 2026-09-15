package com.impacto.application.service;
import com.impacto.api.dto.*;
import com.impacto.application.exception.NotFoundException;
import com.impacto.domain.model.*;
import com.impacto.infrastructure.persistence.CampaignRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CampaignService {
    private final CampaignRepository repo;
    public CampaignService(CampaignRepository r) {
        repo=r;
    }
    public CampaignResponse create(CampaignRequest r) {
        var c=new Campaign();
        apply(c,r);
        return to(repo.save(c));
    }
    public PageResponse<CampaignResponse> find(CampaignStatus status,int page,int size) {
        var p=PageRequest.of(page,Math.min(size,100),Sort.by(Sort.Direction.DESC,"startDate"));
        var x=status==null?repo.findAll(p):repo.findByStatus(status,p);
        return PageResponse.from(x.map(this::to));
    }
    public CampaignResponse get(UUID id) {
        return to(repo.findById(id).orElseThrow(()->new NotFoundException("Campaña no encontrada")));
    }
    public CampaignResponse update(UUID id,CampaignRequest r) {
        var c=repo.findById(id).orElseThrow(()->new NotFoundException("Campaña no encontrada"));
        apply(c,r);
        return to(repo.save(c));
    }
    public void delete(UUID id) {
        repo.delete(repo.findById(id).orElseThrow(()->new NotFoundException("Campaña no encontrada")));
    }
    private void apply(Campaign c,CampaignRequest r) {
        if(r.endDate().isBefore(r.startDate()))throw new com.impacto.application.exception
            .DomainException("La fecha final no puede ser anterior a la inicial");
        c.setName(r.name());
        c.setDescription(r.description());
        c.setGoal(r.goal());
        c.setStartDate(r.startDate());
        c.setEndDate(r.endDate());
        c.setStatus(r.status());
    }
    private CampaignResponse to(Campaign c) {
        return new CampaignResponse(c.getId(),c.getName(),c.getDescription(),c.getGoal(),c.getStartDate(),c.getEndDate(),c.getStatus());
    }
}
