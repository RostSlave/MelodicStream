package com.example.kursovoi;

import com.example.kursovoi.repositories.RolesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class RoleTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Test
    void TestRole() throws SQLException, ClassNotFoundException {
        Assertions.assertNotNull(rolesRepository.findAll());
    }
}
