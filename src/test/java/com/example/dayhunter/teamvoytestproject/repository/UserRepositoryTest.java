package com.example.dayhunter.teamvoytestproject.repository;

import com.example.dayhunter.teamvoytestproject.models.BaseUser;
import com.example.dayhunter.teamvoytestproject.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserRepositoryTest {

        @Autowired
        private TestEntityManager entityManager;

        @Autowired
        private UserRepository userRepository;

        @Test
        public void testFindByEmail() {
            // Создаем тестового пользователя и сохраняем его в базе данных
            BaseUser user = new BaseUser();
            user.setRole(Role.ROLE_MANAGER);
            user.setEmail("test@example.com");
            user.setName("Test1");
            user.setPassword("password");
            entityManager.persistAndFlush(user);

            // Ищем пользователя по email
            Optional<BaseUser> foundUser = userRepository.findByEmail("test@example.com");

            // Проверяем, что пользователь был найден
            assertTrue(foundUser.isPresent());

            // Проверяем, что найденный пользователь имеет правильный email
            assertEquals("test@example.com", foundUser.get().getEmail());
        }

        @Test
        public void testFindByEmail_NotFound() {
            // Попытка найти пользователя с несуществующим email
            Optional<BaseUser> foundUser = userRepository.findByEmail("nonexistent@example.com");

            // Проверяем, что пользователь не был найден
            assertFalse(foundUser.isPresent());
        }
}