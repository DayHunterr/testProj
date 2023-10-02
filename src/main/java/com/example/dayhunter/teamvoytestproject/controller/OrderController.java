package com.example.dayhunter.teamvoytestproject.controller;

import com.example.dayhunter.teamvoytestproject.models.dto.OrderRequestDto;
import com.example.dayhunter.teamvoytestproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderRequestDto orderRequestDto){
        orderService.placeOrder(orderRequestDto);
    }

    @PostMapping("/{orderId}/pay")
    @ResponseStatus(HttpStatus.OK)
    public void pay(@PathVariable Long orderId){
        orderService.pay(orderId);
    }

}
