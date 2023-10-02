package com.example.dayhunter.teamvoytestproject.service;

import com.example.dayhunter.teamvoytestproject.models.dto.OrderRequestDto;

public interface OrderService {
    String placeOrder(OrderRequestDto order);
    void pay(Long orderId);
    void deleteUnpaidOrders();
}
