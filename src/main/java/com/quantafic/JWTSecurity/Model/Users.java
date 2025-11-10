package com.quantafic.JWTSecurity.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.quantafic.JWTSecurity.Enum.Department;
import com.quantafic.JWTSecurity.Enum.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import java.time.Instant;
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
        }
)
@Data
public class Users {
    @Id
    @GeneratedValue(generator = "staff-id-gen")
    @GenericGenerator(
            name = "staff-id-gen",
            strategy = "com.quantafic.JWTSecurity.Model.Generator.StaffIdGenerator"
    )
    @Column(name = "staff_id", updatable = false, nullable = false, length = 50)
    private String staffId;

    @NotBlank(message = "Name is required")
    @Column(name = "staff_name", nullable = false)
    private String staff_name;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(name = "password",nullable=false)
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", nullable = false)
    private String email;


    @NotNull(message = "Department is required")
    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;


    @NotNull(message = "Role is required")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @NotBlank(message = "branch code is required")
    @Column(name = "branch_code", nullable = false)
    private String branch_code;


    @Column(name="is_active",nullable = true)
    private Boolean isActive;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false, updatable = true)
    private Instant updatedAt;

}