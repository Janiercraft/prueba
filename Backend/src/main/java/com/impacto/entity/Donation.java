package com.impacto.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="donations", indexes= {
    @Index(name="idx_donations_donor",columnList="donor_id"),@Index(name="idx_donations_campaign",columnList="campaign_id"),@Index(name="idx_donations_type",
        columnList="type")
}
) public class Donation extends AuditableEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="donor_id",nullable=false)
    private Donor donor;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="campaign_id")
    private Campaign campaign;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private DonationType type;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private DonationStatus status=DonationStatus.RECEIVED;
    @Column(precision=14,scale=2)
    private BigDecimal amount;
    @Column(length=500)
    private String description;
    private Integer quantity;
    @Enumerated(EnumType.STRING) @Column(name="material_type",length=30)
    private ResourceType materialType;
    public Donor getDonor() {
        return donor;
    }
    public void setDonor(Donor v) {
        donor=v;
    }
    public Campaign getCampaign() {
        return campaign;
    }
    public void setCampaign(Campaign v) {
        campaign=v;
    }
    public DonationType getType() {
        return type;
    }
    public void setType(DonationType v) {
        type=v;
    }
    public DonationStatus getStatus() {
        return status;
    }
    public void setStatus(DonationStatus v) {
        status=v;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal v) {
        amount=v;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String v) {
        description=v;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer v) {
        quantity=v;
    }
    public ResourceType getMaterialType() {
        return materialType;
    }
    public void setMaterialType(ResourceType v) {
        materialType=v;
    }
}
