package com.impacto.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="children", indexes=@Index(name="idx_children_family",columnList="family_id")) public class Child extends AuditableEntity {
    @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="family_id",nullable=false)
    private Family family;
    @Column(nullable=false,length=160)
    private String name;
    @Column(nullable=false)
    private LocalDate birthDate;
    @Column(length=180)
    private String school;
    @Column(length=80)
    private String grade;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private ChildStatus status=ChildStatus.ACTIVE;
    public Family getFamily() {
        return family;
    }
    public void setFamily(Family v) {
        family=v;
    }
    public String getName() {
        return name;
    }
    public void setName(String v) {
        name=v;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate v) {
        birthDate=v;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String v) {
        school=v;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String v) {
        grade=v;
    }
    public ChildStatus getStatus() {
        return status;
    }
    public void setStatus(ChildStatus v) {
        status=v;
    }
}
