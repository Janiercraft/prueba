package com.impacto.domain.model;
import jakarta.persistence.*;
@Entity @Table(name="donors", indexes=@Index(name="idx_donors_name",columnList="name")) public class Donor extends AuditableEntity {
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",unique=true)
    private User user;
    @Column(nullable=false,length=180)
    private String name;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private DonorType type;
    @Column(nullable=false,length=120)
    private String email;
    @Column(length=30)
    private String phone;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private UserStatus status=UserStatus.ACTIVE;
    public User getUser() {
        return user;
    }
    public void setUser(User v) {
        user=v;
    }
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name=v;
    }
    public DonorType getType() {
        return type;
    }
    public void setType(DonorType v) {
        type=v;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String v) {
        email=v;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String v) {
        phone=v;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus v) {
        status=v;
    }
}
