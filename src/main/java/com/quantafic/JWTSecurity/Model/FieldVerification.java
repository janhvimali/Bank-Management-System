package com.quantafic.JWTSecurity.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "field_verification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldVerification {

    @Id
    @Column(name = "verification_id", nullable = false)
    private UUID verificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by", nullable = false)
    private Users verifiedBy;

    @Column(name = "photo_url", nullable = false, columnDefinition = "text")
    private String photoUrl;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "remarks", columnDefinition = "text")
    private String remarks;

    @Builder.Default
    @Column(name = "verified_at")
    private Instant verifiedAt = Instant.now();
}

