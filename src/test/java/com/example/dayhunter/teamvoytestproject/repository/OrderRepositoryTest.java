package com.example.dayhunter.teamvoytestproject.repository;

import com.example.dayhunter.teamvoytestproject.models.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        // Создаем и сохраняем несколько заказов для тестирования
        Order order1 = new Order();
        order1.setId(1L);
        order1.setPaid(false);
        order1.setDateOfCreated(new Date());

        Order order2 = new Order();
        order2.setId(2L);
        order2.setPaid(true);
        order2.setDateOfCreated(new Date());

        Order order3 = new Order();
        order3.setId(3L);
        order3.setPaid(false);
        order3.setDateOfCreated(new Date(System.currentTimeMillis() - 3600000)); // Заказ создан час назад

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
    }

    @Test
    public void testFindByPaidFalseAndDateOfCreatedBefore() {
        // Поиск заказов с неоплаченным статусом и датой создания до текущего момента
        Date timeExpired = new Date();
        List<Order> foundOrders = orderRepository.findByPaidFalseAndDateOfCreatedBefore(timeExpired);
        assertThat(foundOrders.size()).isEqualTo(2); // Ожидаем, что найдется два заказа
        for (Order order : foundOrders) {
            assertThat(order.isPaid()).isFalse();
            assertThat(order.getDateOfCreated()).isBeforeOrEqualTo(timeExpired);
        }
    }

    @Test()
    public void testFindByIdAndPaidFalse() {
        // Поиск заказа с определенным ID и неоплаченным статусом
        Order order4 = new Order();
        order4.setId(4L);
        order4.setPaid(false);
        order4.setDateOfCreated(new Date());
        orderRepository.save(order4);

        Long orderId = 4L;
        orderRepository.findById(4L);
        Optional<Order> foundOrder = orderRepository.findByIdAndPaidFalse(orderId);

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(orderId);
        assertThat(foundOrder.get().isPaid()).isFalse();
    }
}