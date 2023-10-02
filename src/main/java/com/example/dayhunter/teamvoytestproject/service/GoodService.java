package com.example.dayhunter.teamvoytestproject.service;

import com.example.dayhunter.teamvoytestproject.models.dto.GoodDto;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodRequestDto;

import java.util.List;

public interface GoodService {
    void createGood(GoodRequestDto goodRequestDto);

    List<GoodDto> allGoods();
}
