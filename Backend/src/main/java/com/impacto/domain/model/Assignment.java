package com.impacto.domain.model;
import jakarta.persistence.*;
@Entity @Table(name="assignments", indexes= {
    @Index(name="idx_assignments_need",columnList="need_id"),@Index(name="idx_assignments_resource",columnList="resource_id")
}
) public class Assignment extends AuditableEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="need_id",nullable=false)
    private Need need;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="resource_id",nullable=false)
    private Resource resource;
    @Column(nullable=false)
    private Integer quantity;
    public Need getNeed() {
        return need;
    }
    public void setNeed(Need v) {
        need=v;
    }
    public Resource getResource() {
        return resource;
    }
    public void setResource(Resource v) {
        resource=v;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer v) {
        quantity=v;
    }
}
