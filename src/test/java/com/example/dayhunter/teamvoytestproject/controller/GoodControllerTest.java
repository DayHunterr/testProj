package com.example.dayhunter.teamvoytestproject.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.example.dayhunter.teamvoytestproject.models.dto.GoodDto;
import com.example.dayhunter.teamvoytestproject.models.dto.GoodRequestDto;
import com.example.dayhunter.teamvoytestproject.service.GoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GoodController.class)
class GoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoodService goodService;

    @Test
    public void testGoodList() throws Exception {
        GoodDto goodDto1 = new GoodDto();
        GoodDto goodDto2 = new GoodDto();
        goodDto1.setName("good1");
        goodDto1.setPrice(10.0);
        goodDto1.setQuantity(5);
        goodDto2.setName("good2");
        goodDto2.setPrice(20.0);
        goodDto2.setQuantity(3);

        // Создаем список тестовых товаров
        List<GoodDto> goodList = new ArrayList<>();
        goodList.add(goodDto1);
        goodList.add(goodDto2);

        // Когда goodService вызывает метод allGoods(), вернуть наш список
        when(goodService.allGoods()).thenReturn(goodList);

        // Выполняем GET-запрос к /goods/all и ожидаем HTTP-статус 200
        mockMvc.perform(get("/goods/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Good 1"))
                .andExpect(jsonPath("$[0].price").value(10.0))
                .andExpect(jsonPath("$[0].quantity").value(5))
                .andExpect(jsonPath("$[1].name").value("Good 2"))
                .andExpect(jsonPath("$[1].price").value(20.0))
                .andExpect(jsonPath("$[1].quantity").value(3));

        // Проверяем, что метод allGoods() был вызван один раз
        verify(goodService, times(1)).allGoods();
    }

    @Test
    public void testAddGood() throws Exception {
        // Создаем тестовый DTO для товара
        GoodRequestDto goodDto = new GoodRequestDto();
        goodDto.setName("New Good");
        goodDto.setPrice(15.0);
        goodDto.setQuantity(7);

        // Выполняем POST-запрос к /goods/add с тестовым DTO и ожидаем HTTP-статус 201 (CREATED)
        mockMvc.perform(post("/goods/add")
                        .contentType("application/json")
                        .content("{\"name\":\"New Good\",\"price\":15.0,\"quantity\":7}"))
                .andExpect(status().isCreated());

        // Проверяем, что метод createGood() был вызван один раз с соответствующим DTO
        verify(goodService, times(1)).createGood(goodDto);
    }
}