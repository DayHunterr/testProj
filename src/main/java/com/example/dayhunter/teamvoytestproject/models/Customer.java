package com.example.dayhunter.teamvoytestproject.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends BaseUser {

    public Customer(){
        super();
        setRole(Role.ROLE_CUSTOMER);
    }
}
