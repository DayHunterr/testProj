package com.example.dayhunter.teamvoytestproject.controller;

import com.example.dayhunter.teamvoytestproject.models.dto.OrderItemDto;
import com.example.dayhunter.teamvoytestproject.models.dto.OrderRequestDto;
import com.example.dayhunter.teamvoytestproject.repository.OrderRepository;
import com.example.dayhunter.teamvoytestproject.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringJUnitWebConfig
@AutoConfigureMockMvc
class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    public void testPay() throws Exception {
        Long orderId = 1L; // Замените на существующий orderId

        mockMvc.perform(post("/orders/" + orderId + "/pay")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Проверяем, что сервис вызывается с правильным orderId
        verify(orderService).pay(orderId);
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        List<OrderItemDto> expectedItems = new ArrayList<>();
        expectedItems.add(new OrderItemDto(1L, 3));
        expectedItems.add(new OrderItemDto(2L, 4));
        orderRequestDto.setOrderItemList(expectedItems);
        orderRequestDto.setOrderItemList(new ArrayList<>(List.of(new OrderItemDto(1L,3),new OrderItemDto(2L,4))));
        String requestJson = "{\"orderItemList\":[{\"id\":1,\"quantity\":3},{\"id\":2,\"quantity\":4}]}";

        // Здесь устанавливайте необходимые поля в orderRequestDto

//        doReturn("1").when(orderService).placeOrder(eq(orderRequestDto)); // Используйте eq(orderRequestDto)

        MvcResult result = mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)) // Замените JSON на валидный OrderRequestDto JSON
                .andExpect(status().isCreated())
                .andReturn();

        // Проверяем, что сервис вызывается с правильными данными
        verify(orderService.placeOrder(orderRequestDto)); // Используйте eq(orderRequestDto)
    }
}