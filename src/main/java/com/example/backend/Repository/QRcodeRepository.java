package com.example.backend.Repository;

import com.example.backend.Entity.QRcode;
import com.example.backend.Entity.Tab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QRcodeRepository extends JpaRepository<QRcode, String> {
    Optional<QRcode> findByTab(Tab tab);
}

