package com.example.dayhunter.teamvoytestproject.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @Column(name = "paid")
    private boolean paid;

    public Order(){
        this.paid = false;
    }
}
