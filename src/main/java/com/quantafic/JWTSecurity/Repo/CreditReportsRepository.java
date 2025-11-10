package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.CreditReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditReportsRepository extends JpaRepository<CreditReport ,String> {
    public CreditReport findByApplicationId(String applicationId);
}
