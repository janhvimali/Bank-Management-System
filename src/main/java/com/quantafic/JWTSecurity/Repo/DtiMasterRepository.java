package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.DtiMaster;
import com.quantafic.JWTSecurity.Model.FoirMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface DtiMasterRepository extends JpaRepository<DtiMaster , Long> {
    @Query("SELECT d FROM DtiMaster d " +
            "WHERE :dti BETWEEN d.lowerLimit AND d.upperLimit")
    Optional<DtiMaster> findByDti(@Param("dti") BigDecimal dti);
}
