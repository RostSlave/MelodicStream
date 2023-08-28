package com.example.kursovoi.repositories;

import com.example.kursovoi.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

//    @Query("SELECT r FROM Role r WHERE r.name LIKE %?1% ")
//    Role findByName(String name);
}
