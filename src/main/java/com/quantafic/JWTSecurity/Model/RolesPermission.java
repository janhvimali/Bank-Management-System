//package com.quantafic.JWTSecurity.Model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.Instant;
//import java.util.UUID;
//
//@Entity
//@Table(name = "roles_permission")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class RolesPermission {
//
//    @Id
//    private UUID rolePermissionId;
//
//    @OneToOne
//    @JoinColumn(name = "roles", nullable = false)
//    private Roles role;
//
//    @ManyToOne
//    @JoinColumn(name = "permission", nullable = false)
//    private Permission permission;
//
//    @Column(nullable = false)
//    private Instant createdAt = Instant.now();
//}
//
