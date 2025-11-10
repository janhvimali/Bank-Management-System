package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.FieldVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FieldVerificationRepo extends JpaRepository<FieldVerification, UUID> {
}
