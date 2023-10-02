package com.example.dayhunter.teamvoytestproject.models;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    private Long goodId;
    private Integer quantity;
}
