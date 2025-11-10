package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.Disbursement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DisbursementRepo extends JpaRepository<Disbursement, Long> {
}
