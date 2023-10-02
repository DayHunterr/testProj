package com.example.dayhunter.teamvoytestproject.repository;

import com.example.dayhunter.teamvoytestproject.models.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodRepository extends JpaRepository<Good,Long> {

    @Override
    List<Good> findAll();

}
