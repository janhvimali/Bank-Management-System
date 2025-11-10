//package com.quantafic.JWTSecurity.Model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "roles")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Roles {
//
//    @Id
//    private UUID roleId;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false,unique = true)
//    private com.quantafic.JWTSecurity.Enum.Roles Roles;
//
//    @Column(length = 100)
//    private String roleDesc;
//}
