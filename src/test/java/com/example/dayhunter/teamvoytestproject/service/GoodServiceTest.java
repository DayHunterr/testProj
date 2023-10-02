package com.example.dayhunter.teamvoytestproject.service;

import com.example.dayhunter.teamvoytestproject.models.mapper.GoodMapper;
import com.example.dayhunter.teamvoytestproject.repository.GoodRepository;
import com.example.dayhunter.teamvoytestproject.service.impl.GoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import com.example.dayhunter.teamvoytestproject.models.Good;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodDto;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodRequestDto;

import static org.junit.jupiter.api.Assertions.*;

class GoodServiceTest {

    @InjectMocks
    private GoodServiceImpl goodService;

    @Mock
    private GoodRepository goodRepository;
    @Mock
    private GoodMapper goodMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createGood() {
        GoodRequestDto goodRequestDto = new GoodRequestDto();
        goodRequestDto.setName("Test Good");
        goodRequestDto.setPrice(10.0);
        goodRequestDto.setQuantity(5);

        // Создайте мок объект Good
        Good mockGood = new Good();
        when(goodRepository.save(any())).thenReturn(mockGood);

        // Вызов метода createGood
        goodService.createGood(goodRequestDto);

        // Проверка, что метод save был вызван один раз
        verify(goodRepository, times(1)).save(any());
    }

    @Test
    void allGoods() {
        Good good1 = new Good();
        good1.setName("Good 1");
        good1.setPrice(10.0);
        good1.setQuantity(5);

        Good good2 = new Good();
        good2.setName("Good 2");
        good2.setPrice(20.0);
        good2.setQuantity(3);

        List<Good> mockGoods = new ArrayList<>();
        mockGoods.add(good1);
        mockGoods.add(good2);

        when(goodRepository.findAll()).thenReturn(mockGoods);

        // Вызов метода allGoods
        List<GoodDto> result = goodService.allGoods();

        // Проверка, что метод findAll был вызван один раз
        verify(goodRepository, times(1)).findAll();

        // Проверка, что результат содержит два элемента
        assertEquals(2, result.size());
    }
}