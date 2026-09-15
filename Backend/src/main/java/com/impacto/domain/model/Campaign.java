package com.impacto.domain.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity @Table(name="campaigns", indexes=@Index(name="idx_campaigns_status",columnList="status")) public class Campaign extends AuditableEntity {
    @Column(nullable=false,length=180)
    private String name;
    @Column(length=1000)
    private String description;
    @Column(nullable=false,precision=14,scale=2)
    private BigDecimal goal;
    @Column(nullable=false)
    private LocalDate startDate;
    @Column(nullable=false)
    private LocalDate endDate;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private CampaignStatus status=CampaignStatus.DRAFT;
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name=v;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String v) {
        description=v;
    }
    public BigDecimal getGoal() {
        return goal;
    }
    public void setGoal(BigDecimal v) {
        goal=v;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate v) {
        startDate=v;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate v) {
        endDate=v;
    }
    public CampaignStatus getStatus() {
        return status;
    }
    public void setStatus(CampaignStatus v) {
        status=v;
    }
}
