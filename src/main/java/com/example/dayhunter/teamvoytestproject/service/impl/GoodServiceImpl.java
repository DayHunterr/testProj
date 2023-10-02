package com.example.dayhunter.teamvoytestproject.service.impl;

import com.example.dayhunter.teamvoytestproject.models.Good;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodDto;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodRequestDto;
import com.example.dayhunter.teamvoytestproject.models.mapper.GoodMapper;
import com.example.dayhunter.teamvoytestproject.repository.GoodRepository;
import com.example.dayhunter.teamvoytestproject.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {

    private final GoodRepository goodRepository;

    private final GoodMapper goodMapper;

    @Override
    public void createGood(GoodRequestDto goodRequestDto) {
        Good newGood = new Good();
        newGood.setName(goodRequestDto.getName());
        newGood.setPrice(goodRequestDto.getPrice());
        newGood.setQuantity(goodRequestDto.getQuantity());
        goodRepository.save(newGood);
    }

    @Override
    public List<GoodDto> allGoods() {
        return goodRepository.findAll().stream().map(goodMapper).collect(Collectors.toList());
    }

}
