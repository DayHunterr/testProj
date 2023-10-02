package com.example.dayhunter.teamvoytestproject.controller;

import com.example.dayhunter.teamvoytestproject.models.dto.GoodDto;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodRequestDto;
import com.example.dayhunter.teamvoytestproject.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGood(@RequestBody GoodRequestDto goodDto){
        goodService.createGood(goodDto);
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<GoodDto> goodList(){
        return goodService.allGoods();
    }
}
