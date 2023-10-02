package com.example.dayhunter.teamvoytestproject.service;

import com.example.dayhunter.teamvoytestproject.models.Good;
import com.example.dayhunter.teamvoytestproject.models.Order;
import com.example.dayhunter.teamvoytestproject.models.dto.OrderItemDto;
import com.example.dayhunter.teamvoytestproject.models.dto.OrderRequestDto;
import com.example.dayhunter.teamvoytestproject.repository.GoodRepository;
import com.example.dayhunter.teamvoytestproject.repository.OrderRepository;
import com.example.dayhunter.teamvoytestproject.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private GoodRepository goodRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void placeOrder_ValidOrder_ShouldPlaceOrder() {

        OrderRequestDto orderDto = new OrderRequestDto();
        List<OrderItemDto> orderItemListDto = new ArrayList<>();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setGoodId(1L);
        orderItemDto.setQuantity(3);
        orderItemListDto.add(orderItemDto);
        orderDto.setOrderItemList(orderItemListDto);
        Good good = new Good();
        good.setId(1L);
        good.setQuantity(5);
        when(goodRepository.findById(1L)).thenReturn(Optional.of(good));
        String result = orderService.placeOrder(orderDto);
        assertEquals("Order placed", result);
        assertEquals(2, good.getQuantity());
        verify(goodRepository, times(1)).saveAll(any());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void placeOrder_InvalidOrder_ShouldThrowException() {
        OrderRequestDto orderDto = new OrderRequestDto();
        List<OrderItemDto> orderItemListDto = new ArrayList<>();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setGoodId(1L);
        orderItemDto.setQuantity(6);
        orderItemListDto.add(orderItemDto);
        orderDto.setOrderItemList(orderItemListDto);
        Good good = new Good();
        good.setId(1L);
        good.setQuantity(5);
        when(goodRepository.findById(1L)).thenReturn(Optional.of(good));
        assertThrows(RuntimeException.class, () -> orderService.placeOrder(orderDto));
        assertEquals(5, good.getQuantity());
        verify(goodRepository, never()).saveAll(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void pay_ValidOrderId_ShouldMarkOrderAsPaid() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        orderService.pay(orderId);
        assertTrue(order.isPaid());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void pay_InvalidOrderId_ShouldThrowException() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderService.pay(orderId));
    }
}