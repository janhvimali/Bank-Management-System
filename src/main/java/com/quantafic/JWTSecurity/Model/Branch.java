package com.quantafic.JWTSecurity.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
@Table(name = "BranchMaster")
@Data
@Entity
@Builder
public class Branch {
    @Id
    @Column(name = "Branch_code")
    private int branchCode;

    @Column(name = "Branch_name" , nullable = false)
    private String branchName;
}
