package com.example.dayhunter.teamvoytestproject.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreated;

    @PrePersist
    protected void init() {
        dateOfCreated = new Date();
    }
}
