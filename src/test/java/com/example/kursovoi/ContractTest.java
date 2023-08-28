package com.example.kursovoi;

import com.example.kursovoi.repositories.ContractsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class ContractTest {

    @Autowired
    private ContractsRepository contractsRepository;

    @Test
    void TestContract() throws SQLException, ClassNotFoundException {
        Assertions.assertNotNull(contractsRepository.findAll());
    }
}
