package com.example.kursovoi;

import com.example.kursovoi.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class UserTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void TestUser() throws SQLException, ClassNotFoundException {
        Assertions.assertNotNull(usersRepository.getOne((long)29));
        Assertions.assertEquals(29, usersRepository.
                findByEmail("qwe.rostislav@mail.ru").getId());
    }
}
