package com.example.kursovoi.repositories;

import com.example.kursovoi.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongsRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByUserId(long id);
}
