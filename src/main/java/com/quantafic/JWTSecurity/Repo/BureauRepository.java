package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.Bureau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BureauRepository extends JpaRepository<Bureau, String> {
    Bureau findByApplicationId(String applicationId);
}
