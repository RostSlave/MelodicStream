package com.example.kursovoi;

import com.example.kursovoi.repositories.SupportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class SupportTest {

    @Autowired
    private SupportRepository supportRepository;

    @Test
    void TestSupport() throws SQLException, ClassNotFoundException {
        Assertions.assertNotNull(supportRepository.findAll());
    }
}