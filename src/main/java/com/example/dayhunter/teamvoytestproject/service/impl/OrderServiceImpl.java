package com.example.dayhunter.teamvoytestproject.service.impl;

import com.example.dayhunter.teamvoytestproject.models.Good;
import com.example.dayhunter.teamvoytestproject.models.Order;
import com.example.dayhunter.teamvoytestproject.models.OrderItem;
import com.example.dayhunter.teamvoytestproject.models.dto.OrderItemDto;
import com.example.dayhunter.teamvoytestproject.models.dto.OrderRequestDto;
import com.example.dayhunter.teamvoytestproject.repository.GoodRepository;
import com.example.dayhunter.teamvoytestproject.repository.OrderRepository;
import com.example.dayhunter.teamvoytestproject.service.OrderService;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GoodRepository goodRepository;

    @Override
    public String placeOrder(OrderRequestDto orderDto) {
        Order order = new Order();
        List<Good> goodList = new ArrayList<>();
        List<OrderItemDto> orderItemListDto = orderDto.getOrderItemList();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItemListDto.forEach(orderItemDto -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setGoodId(orderItemDto.getGoodId());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItems.add(orderItem);
        });
        orderItems.forEach(orderItem -> {
            Good good = goodRepository.findById(orderItem.getGoodId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
            if (good.getQuantity() < orderItem.getQuantity()) {
                throw new RuntimeException("Not enough items");
            }
            good.setQuantity(good.getQuantity() - orderItem.getQuantity());
            goodList.add(good);
        });
        goodRepository.saveAll(goodList);
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        return "Order placed";
    }

    @Override
    public void pay(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        if (!order.isPaid()) {
            order.setPaid(true);
            orderRepository.save(order);
        }
    }

    @Override
    @Scheduled(fixedRate = 300000L)
    public void deleteUnpaidOrders() {
        Date localDateTime = new Date(System.currentTimeMillis()-600000L);
        List<Order> unpaidOrders = orderRepository.findByPaidFalseAndDateOfCreatedBefore(localDateTime);
        List<Good> goodList = new ArrayList<>();
        unpaidOrders.forEach(order -> {
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(orderItem -> {
                Good good = goodRepository.findById(orderItem.getGoodId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
                good.setQuantity(good.getQuantity() + orderItem.getQuantity());
                goodList.add(good);
            });
        });
        goodRepository.saveAll(goodList);
        orderRepository.deleteAll(unpaidOrders);
    }
}
