package com.impacto.domain.model;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="needs", indexes= {
    @Index(name="idx_needs_family",columnList="family_id"),@Index(name="idx_needs_status",columnList="status"),@Index(name="idx_needs_priority",columnList="priority"),@Index(name="idx_needs_type",
        columnList="type")
}
) public class Need extends AuditableEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="family_id",nullable=false)
    private Family family;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="child_id")
    private Child child;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=30)
    private NeedType type;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private Priority priority;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private NeedStatus status=NeedStatus.PENDING;
    @Column(nullable=false,length=500)
    private String description;
    @Column(nullable=false)
    private Integer requestedQuantity;
    @Column(nullable=false)
    private Integer fulfilledQuantity=0;
    public Family getFamily() {
        return family;
    }
    public void setFamily(Family v) {
        family=v;
    }
    public Child getChild() {
        return child;
    }
    public void setChild(Child v) {
        child=v;
    }
    public NeedType getType() {
        return type;
    }
    public void setType(NeedType v) {
        type=v;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority v) {
        priority=v;
    }
    public NeedStatus getStatus() {
        return status;
    }
    public void setStatus(NeedStatus v) {
        status=v;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String v) {
        description=v;
    }
    public Integer getRequestedQuantity() {
        return requestedQuantity;
    }
    public void setRequestedQuantity(Integer v) {
        requestedQuantity=v;
    }
    public Integer getFulfilledQuantity() {
        return fulfilledQuantity;
    }
    public void setFulfilledQuantity(Integer v) {
        fulfilledQuantity=v;
    }
    public int remainingQuantity() {
        return Math.max(0,requestedQuantity-fulfilledQuantity);
    }
}
