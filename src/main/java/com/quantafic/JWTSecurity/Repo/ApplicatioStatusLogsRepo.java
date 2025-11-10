package com.quantafic.JWTSecurity.Repo;

import com.quantafic.JWTSecurity.Model.ApplicationStatusLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicatioStatusLogsRepo extends JpaRepository<ApplicationStatusLogs, UUID> {
}
