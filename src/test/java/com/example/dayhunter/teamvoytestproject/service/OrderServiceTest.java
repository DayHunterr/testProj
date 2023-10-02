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
        // Arrange
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

        // Act
        String result = orderService.placeOrder(orderDto);

        // Assert
        assertEquals("Order placed", result);
        assertEquals(2, good.getQuantity()); // Quantity of the good should be reduced by 3
        verify(goodRepository, times(1)).saveAll(any());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void placeOrder_InvalidOrder_ShouldThrowException() {
        // Arrange
        OrderRequestDto orderDto = new OrderRequestDto();
        List<OrderItemDto> orderItemListDto = new ArrayList<>();
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setGoodId(1L);
        orderItemDto.setQuantity(6); // More than available quantity
        orderItemListDto.add(orderItemDto);
        orderDto.setOrderItemList(orderItemListDto);

        Good good = new Good();
        good.setId(1L);
        good.setQuantity(5);

        when(goodRepository.findById(1L)).thenReturn(Optional.of(good));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> orderService.placeOrder(orderDto));
        assertEquals(5, good.getQuantity()); // Quantity of the good should remain the same
        verify(goodRepository, never()).saveAll(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void pay_ValidOrderId_ShouldMarkOrderAsPaid() {
        // Arrange
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        orderService.pay(orderId);

        // Assert
        assertTrue(order.isPaid());
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void pay_InvalidOrderId_ShouldThrowException() {
        // Arrange
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> orderService.pay(orderId));
    }

//    @Test
//    public void deleteUnpaidOrders_ShouldDeleteUnpaidOrdersAndRestoreGoodQuantities() {
//        // Создаем список неплатежных заказов
//        List<Order> unpaidOrders = new ArrayList<>();
//        // Создаем неплатежный заказ с одним OrderItem
//        Order order = new Order();
//        OrderItem orderItem = new OrderItem();
//        orderItem.setGoodId(1L); // Здесь установите ID товара по вашему усмотрению
//        orderItem.setQuantity(2); // Количество товаров в заказе
//        List<OrderItem> orderItems = new ArrayList<>();
//        orderItems.add(orderItem);
//        order.setOrderItems(orderItems);
//        unpaidOrders.add(order);
//
//        // Ожидаем, что метод findByPaidFalseAndDateOfCreatedBefore вернет список неплатежных заказов
//        when(orderRepository.findByPaidFalseAndDateOfCreatedBefore(any())).thenReturn(unpaidOrders);
//
//        // Создаем список товаров, которые будут восстановлены
//        List<Good> restoredGoods = new ArrayList<>();
//        Good good = new Good();
//        good.setId(1L); // Установите ID товара, который соответствует вашему заказу
//        good.setQuantity(5); // Установите начальное количество товаров
//        restoredGoods.add(good);
//
//        // Ожидаем, что метод findById вернет товар по ID
//        when(goodRepository.findById(anyLong())).thenReturn(Optional.of(good));
//
//        // Вызываем метод, который мы хотим протестировать
//        orderService.deleteUnpaidOrders();
//
//        // Проверяем, что товар был восстановлен (количество увеличено на количество в заказе)
//        verify(goodRepository).save(good);
//        assertThat(good.getQuantity()).isEqualTo(7); // Ожидаемое количество после восстановления
//
//        // Проверяем, что неплатежные заказы были удалены
//        verify(orderRepository).deleteAll(unpaidOrders);
//    }

}