package com.example.kursovoi.repositories;

import com.example.kursovoi.models.Contract;
import com.example.kursovoi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("SELECT p FROM User p WHERE p.name LIKE %?1%"
            + " OR p.surname LIKE %?1%"
            + " OR p.email LIKE %?1%")
    List<User> findByEmailAndFirstNameAndLastName(String keyword);

    @Query("SELECT t.contract FROM User t WHERE t.id =?1 ")
    Contract findByQuery(long id);

    User deleteById(long id);
    List<User> findByIdNot(long id);

}
