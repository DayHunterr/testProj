package com.example.dayhunter.teamvoytestproject.repository;

import com.example.dayhunter.teamvoytestproject.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findByIdAndPaidFalse(Long orderId);
    List<Order> findByPaidFalseAndDateOfCreatedBefore(Date timeExpired);
}
