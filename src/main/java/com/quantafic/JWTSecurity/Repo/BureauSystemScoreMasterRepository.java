package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.BureauSystemScoreMaster;
import com.quantafic.JWTSecurity.Model.DtiMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
@Repository
public interface BureauSystemScoreMasterRepository extends JpaRepository<BureauSystemScoreMaster , Long> {

    @Query("SELECT b FROM BureauSystemScoreMaster b " +
            "WHERE :bureauScore BETWEEN b.lowerLimit AND b.upperLimit")
    Optional<BureauSystemScoreMaster> findByScore(@Param("bureauScore") Integer bureauScore);
}
