package com.example.kursovoi.repositories;

import com.example.kursovoi.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contract, Long> {
    Contract findContractById(long id);


    Contract findById(Contract contract);
}
