package com.example.dayhunter.teamvoytestproject.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends BaseUser {

    public Manager(){
        super();
        setRole(Role.ROLE_MANAGER);
    }
}
