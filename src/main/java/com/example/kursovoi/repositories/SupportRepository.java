package com.example.kursovoi.repositories;

import com.example.kursovoi.models.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
}
