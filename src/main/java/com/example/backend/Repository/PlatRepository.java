package com.example.backend.Repository;


import com.example.backend.Entity.Plat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatRepository extends JpaRepository<Plat,Long> {
    Plat findByName(String name);
    Optional<Plat> findById(Long idPlat);
}
