package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.FoirMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface FoirMasterRepository extends JpaRepository<FoirMaster , Long> {
    @Query("SELECT f FROM FoirMaster f " +
            "WHERE :foir BETWEEN f.lowerLimit AND f.upperLimit")
    Optional<FoirMaster> findByFoir(@Param("foir") BigDecimal foir);
}
