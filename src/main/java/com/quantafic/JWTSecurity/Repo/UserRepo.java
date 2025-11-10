package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<Users, String> {

    boolean existsByEmail(String email);
}
