package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepo extends JpaRepository<Branch,String> {
}
