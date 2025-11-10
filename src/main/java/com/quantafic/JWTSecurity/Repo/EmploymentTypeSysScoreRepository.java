package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Enum.EmploymentType;
import com.quantafic.JWTSecurity.Model.EmploymentTypeSystemScoreMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentTypeSysScoreRepository extends JpaRepository< EmploymentTypeSystemScoreMaster , Long > {
    EmploymentTypeSystemScoreMaster findByemploymentType(String type);
}
