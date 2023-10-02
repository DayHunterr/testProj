package com.example.dayhunter.teamvoytestproject.models.mapper;

import com.example.dayhunter.teamvoytestproject.models.Good;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GoodMapper implements Function<Good, GoodDto> {


    @Override
    public GoodDto apply(Good good) {
        return new GoodDto(
                good.getId(),
                good.getName(),
                good.getPrice(),
                good.getQuantity(),
                String.valueOf(good.getDateOfCreated())
        );
    }
}
