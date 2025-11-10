package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Enum.LoanProduct;
import com.quantafic.JWTSecurity.Model.LoanProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductsRepository extends JpaRepository<LoanProductMaster, Long> {

    LoanProductMaster findByLoanProduct(LoanProduct loanProduct);
}
