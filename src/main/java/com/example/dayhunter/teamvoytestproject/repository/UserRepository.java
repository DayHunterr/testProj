package com.example.dayhunter.teamvoytestproject.repository;

import com.example.dayhunter.teamvoytestproject.models.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BaseUser,Long> {
    Optional<BaseUser> findByEmail(String email);
}
