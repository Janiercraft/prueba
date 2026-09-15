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
public class DonationService {
    private final DonationRepository repo;
    private final DonorRepository donors;
    private final CampaignRepository campaigns;
    private final ResourceRepository resources;
    private final ResourceCodeService codes;
    private final CurrentUserService current;
    public DonationService(DonationRepository r,DonorRepository d,CampaignRepository c,ResourceRepository re,ResourceCodeService co,
        CurrentUserService cu) {
        repo=r;
        donors=d;
        campaigns=c;
        resources=re;
        codes=co;
        current=cu;
    }
    @Transactional
    public DonationResponse create(DonationRequest r) {
        var donor=donors.findById(r.donorId()).orElseThrow(()->new NotFoundException("Donante no encontrado"));
        if(!isAdmin() && (donor.getUser()==null||!donor.getUser().getId().equals(current
            .id())))throw new ForbiddenException("Solo puedes registrar donaciones a tu nombre");
        validate(r);
        var d=new Donation();
        d.setDonor(donor);
        d.setCampaign(r.campaignId()==null?null:campaigns.findById(r.campaignId()).orElseThrow(()->new NotFoundException("Campaña no encontrada")));
        d.setType(r.type());
        d.setAmount(r.amount());
        d.setDescription(r.description());
        d.setQuantity(r.quantity());
        d.setMaterialType(r.materialType());
        d.setStatus(r.status());
        d=repo.save(d);
        if(r.type()==DonationType.MATERIAL) {
            var res=new Resource();
            res.setCode(codes.next());
            res.setType(r.materialType());
            res.setDescription(r.description());
            res.setQuantity(r.quantity());
            res.setAvailableQuantity(r.quantity());
            res.setDonation(d);
            resources.save(res);
        }
        return to(d);
    }
    public PageResponse<DonationResponse> find(UUID donorId,DonationStatus status,int page,int size) {
        UUID target=donorId;
        if(!isAdmin())target=donors.findByUserId(current.id()).map(Donor::getId).orElseThrow(()->new NotFoundException("Perfil de donante no encontrado"));
        var p=PageRequest.of(page,Math.min(size,100),Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<Donation> x=target!=null?repo.findByDonorId(target,p):status!=null?repo.findByStatus(status,p):repo.findAll(p);
        return PageResponse.from(x.map(this::to));
    }
    public com.impacto.api.dto.DonorImpactResponse impact() {
        UUID id=donors.findByUserId(current.id()).map(Donor::getId).orElseThrow(()->new NotFoundException("Perfil de donante no encontrado"));
        long total=repo.countByDonorIdAndTypeAndStatus(id,DonationType.MONEY,DonationStatus.RECEIVED)+repo.countByDonorIdAndTypeAndStatus(id,DonationType
            .MATERIAL,DonationStatus.RECEIVED);
        long material=repo.countByDonorIdAndTypeAndStatus(id,DonationType.MATERIAL,DonationStatus.RECEIVED);
        return new com.impacto.api.dto.DonorImpactResponse(total,repo.sumAmountByDonorId(id,DonationType.MONEY,DonationStatus.RECEIVED),material);
    }
    public DonationResponse get(UUID id) {
        var d=repo.findById(id).orElseThrow(()->new NotFoundException("Donación no encontrada"));
        if(!isAdmin()&&(d.getDonor().getUser()==null||!d.getDonor().getUser().getId().equals(current
            .id())))throw new ForbiddenException("No puedes consultar esta donación");
        return to(d);
    }
    private void validate(DonationRequest r) {
        if(r.type()==DonationType.MONEY) {
            if(r.amount()==null||r.amount().signum()<=0)throw new DomainException("Una donación MONEY requiere amount > 0");
        } else {
            if(r.description()==null||r.description().isBlank()||r.quantity()==null||r.quantity()<1||r
                .materialType()==null)throw new DomainException("Una donación MATERIAL requiere descripción, cantidad y materialType");
        }
    }
    private DonationResponse to(Donation d) {
        return new DonationResponse(d.getId(),d.getDonor().getId(),d.getDonor().getName(),d.getCampaign()==null?null:d.getCampaign().getId(),d.getType(),d
            .getAmount(),d.getDescription(),d.getQuantity(),d.getMaterialType(),d.getStatus(),d.getCreatedAt());
    }
    private boolean isAdmin() {
        return current.get().getRole()==Role.ADMIN;
    }
}
