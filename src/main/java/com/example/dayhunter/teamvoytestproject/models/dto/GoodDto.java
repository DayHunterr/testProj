package com.example.dayhunter.teamvoytestproject.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodDto {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String created;

}
