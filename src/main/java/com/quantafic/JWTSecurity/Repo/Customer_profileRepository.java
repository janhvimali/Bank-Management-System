package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Customer_profileRepository extends JpaRepository<Customers,String> {
}
