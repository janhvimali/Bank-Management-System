package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.Application;
import com.quantafic.JWTSecurity.Model.Customers;
import com.quantafic.JWTSecurity.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,String> {
    List<Application> findByAssignedTo(Users user);
    List<Application> findByCustomer (Customers customer);
}
