package com.chappi3.QRGen.repository;

import com.chappi3.QRGen.model.Qr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrRepository extends JpaRepository<Qr, Long> {
    Iterable<Qr> findByUserId(Long userId);
    Optional<Qr> findByIdAndUserId(Long id, Long userId);
}
