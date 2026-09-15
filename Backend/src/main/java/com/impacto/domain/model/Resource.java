package com.impacto.domain.model;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="resources", uniqueConstraints=@UniqueConstraint(name="uk_resources_code",columnNames="code"), indexes= {
    @Index(name="idx_resources_status",columnList="status"),@Index(name="idx_resources_donation",columnList="donation_id")
}
) public class Resource extends AuditableEntity {
    @Column(nullable=false,length=30)
    private String code;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=30)
    private ResourceType type;
    @Column(nullable=false,length=500)
    private String description;
    @Column(nullable=false)
    private Integer quantity;
    @Column(nullable=false)
    private Integer availableQuantity;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private ResourceStatus status=ResourceStatus.AVAILABLE;
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="donation_id",nullable=false)
    private Donation donation;
    public String getCode() {
        return code;
    }
    public void setCode(String v) {
        code=v;
    }
    public ResourceType getType() {
        return type;
    }
    public void setType(ResourceType v) {
        type=v;
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
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }
    public void setAvailableQuantity(Integer v) {
        availableQuantity=v;
    }
    public ResourceStatus getStatus() {
        return status;
    }
    public void setStatus(ResourceStatus v) {
        status=v;
    }
    public Donation getDonation() {
        return donation;
    }
    public void setDonation(Donation v) {
        donation=v;
    }
}
