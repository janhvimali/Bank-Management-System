package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.LoanOffers;
import org.springframework.data.jpa.repository.JpaRepository;




public interface LoanOffersRepository extends JpaRepository<LoanOffers, Long> {
    LoanOffers findByApplicationId(String applicationId);
}
