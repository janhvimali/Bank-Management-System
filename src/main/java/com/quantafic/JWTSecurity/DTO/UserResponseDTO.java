package com.quantafic.JWTSecurity.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quantafic.JWTSecurity.Enum.Department;
import com.quantafic.JWTSecurity.Enum.Roles;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserResponseDTO {
    private String staffId;
    private String username;
    private String email;
    private Department department;
    private Roles role;
    private String branchCode;
    @JsonProperty("isActive")
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;


    public UserResponseDTO(String staffId, String username, String email, Department department,
                           Roles role, String branchCode , Boolean isActive , Instant createdAt , Instant updatedAt) {
        this.staffId = staffId;
        this.username = username;
        this.email = email;
        this.department = department;
        this.role = role;
        this.branchCode = branchCode;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }



    // Getters (no setters needed if it's just response)
    public String getStaffId() {
        return staffId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Department getDepartment() {
        return department;
    }

    public Roles getRole() {
        return role;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "staffId='" + staffId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", department=" + department +
                ", role=" + role +
                ", branchCode='" + branchCode + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
