//package com.quantafic.JWTSecurity.Model;
//
//import jakarta.persistence.Column;
//
//import java.time.Instant;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "staff_roles")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class StaffRoles {
//
//    @Id
//    private UUID staffRoleId;
//
//    @ManyToOne
//    @JoinColumn(name = "staff_id", nullable = false)
//    private Users users;
//
//    @ManyToOne
//    @JoinColumn(name = "role", nullable = false)
//    private RolesPermission rolePermission;
//
//    @Column(nullable = false)
//    private Instant assignedAt = Instant.now();
//}
