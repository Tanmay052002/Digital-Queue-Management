package com.queuemgmt.model;

import jakarta.persistence.*;

// SERVICE table from the ERD.
// Named "ServiceEntity" instead of "Service" so it does not clash with
// org.springframework.stereotype.Service annotation.
@Entity
@Table(name = "service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "office_id")
    private Long officeId;

    private String name;

    public ServiceEntity() {
    }

    public ServiceEntity(Long officeId, String name) {
        this.officeId = officeId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
