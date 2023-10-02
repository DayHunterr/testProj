package com.example.dayhunter.teamvoytestproject.repository;

import com.example.dayhunter.teamvoytestproject.models.Good;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class GoodRepositoryTest {

    @Autowired
    private GoodRepository goodRepository;

    @Test
    void findAll() {
        // Создаем и сохраняем тестовые объекты с явными идентификаторами
        Good good1 = new Good();
        good1.setId(1L);
        good1.setName("Good 1");
        good1.setPrice(10.0);
        good1.setQuantity(5);
        goodRepository.save(good1);

        Good good2 = new Good();
        good2.setId(2L);
        good2.setName("Good 2");
        good2.setPrice(20.0);
        good2.setQuantity(3);
        goodRepository.save(good2);

        // Вызов метода findAll
        List<Good> result = goodRepository.findAll();

        // Проверка, что результат содержит два элемента в любом порядке
        assertThat(result.contains(good1));
        assertThat(result.contains(good2));
    }
}