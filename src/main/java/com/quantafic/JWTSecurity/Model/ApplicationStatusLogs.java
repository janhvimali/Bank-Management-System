package com.quantafic.JWTSecurity.Model;

import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "application_status_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationStatusLogs {

    @Id
    @Column(name = "log_id", nullable = false)
    private UUID logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", nullable = false)
    private ApplicationStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private ApplicationStatus newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", nullable = false)
    private Users changedBy;

    @Column(name = "changed_by_role", nullable = false, length = 50)
    private String changedByRole;

    @Builder.Default
    @Column(name = "changed_at", nullable = false)
    private Instant changedAt = Instant.now();
}

