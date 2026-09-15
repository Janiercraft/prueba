package com.impacto.entity;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="deliveries", indexes= {
    @Index(name="idx_deliveries_assignment",columnList="assignment_id"),@Index(name="idx_deliveries_volunteer",columnList="volunteer_id"),@Index(name="idx_deliveries_status",
        columnList="status")
}
) public class Delivery extends AuditableEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="assignment_id",nullable=false)
    private Assignment assignment;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="volunteer_id",nullable=false)
    private User volunteer;
    @Column(nullable=false)
    private Instant deliveryDate;
    @Column(length=1000)
    private String observations;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private DeliveryStatus status=DeliveryStatus.SCHEDULED;
    @Column(length=500)
    private String evidencePath;
    public Assignment getAssignment() {
        return assignment;
    }
    public void setAssignment(Assignment v) {
        assignment=v;
    }
    public User getVolunteer() {
        return volunteer;
    }
    public void setVolunteer(User v) {
        volunteer=v;
    }
    public Instant getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(Instant v) {
        deliveryDate=v;
    }
    public String getObservations() {
        return observations;
    }
    public void setObservations(String v) {
        observations=v;
    }
    public DeliveryStatus getStatus() {
        return status;
    }
    public void setStatus(DeliveryStatus v) {
        status=v;
    }
    public String getEvidencePath() {
        return evidencePath;
    }
    public void setEvidencePath(String v) {
        evidencePath=v;
    }
}
