package com.example.backend.Repository;


import com.example.backend.Entity.Tab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TabRepository extends JpaRepository<Tab, Long> {
    boolean existsByNumber(int number);
    Optional<Tab> findById(Long idTab);

}
