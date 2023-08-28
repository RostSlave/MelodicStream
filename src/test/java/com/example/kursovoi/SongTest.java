package com.example.kursovoi;

import com.example.kursovoi.repositories.SongsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
public class SongTest {

    @Autowired
    private SongsRepository songsRepository;

    @Test
    void TestSong() throws SQLException, ClassNotFoundException {
        Assertions.assertNotNull(songsRepository.findAll());
    }
}
