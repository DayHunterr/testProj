package com.example.dayhunter.teamvoytestproject.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class BaseUser extends BaseEntity {

    @Enumerated(EnumType.STRING )
    @Column(name = "role",nullable = false)
    private Role role;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

}
