package com.chappi3.QRGen.repository;

import com.chappi3.QRGen.model.ERole;
import com.chappi3.QRGen.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
