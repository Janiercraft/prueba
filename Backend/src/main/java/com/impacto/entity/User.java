package com.impacto.entity;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="users", indexes=@Index(name="idx_users_email", columnList="email")) public class User extends AuditableEntity {
    @Column(nullable=false,length=120)
    private String name;
    @Column(nullable=false,unique=true,length=180)
    private String email;
    @Column(nullable=false,length=100)
    private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private Role role;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private UserStatus status=UserStatus.ACTIVE;
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name=v;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String v) {
        email=v.toLowerCase(Locale.ROOT);
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String v) {
        password=v;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role v) {
        role=v;
    }
    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus v) {
        status=v;
    }
}
