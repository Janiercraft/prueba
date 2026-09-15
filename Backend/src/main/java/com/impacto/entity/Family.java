package com.impacto.entity;
import jakarta.persistence.*;
@Entity @Table(name="families", indexes= {
    @Index(name="idx_families_municipality",columnList="municipality"),@Index(name="idx_families_status",columnList="status")
}
) public class Family extends AuditableEntity {
    @Column(nullable=false,length=160)
    private String contactName;
    @Column(nullable=false,length=30)
    private String phone;
    @Column(nullable=false,length=250)
    private String address;
    @Column(nullable=false,length=80)
    private String municipality;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private FamilyStatus status=FamilyStatus.ACTIVE;
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String v) {
        contactName=v;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String v) {
        phone=v;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String v) {
        address=v;
    }
    public String getMunicipality() {
        return municipality;
    }
    public void setMunicipality(String v) {
        municipality=v;
    }
    public FamilyStatus getStatus() {
        return status;
    }
    public void setStatus(FamilyStatus v) {
        status=v;
    }
}
