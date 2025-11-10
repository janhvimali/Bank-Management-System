package com.quantafic.JWTSecurity.Model;

   import com.fasterxml.jackson.annotation.JsonIgnore;
   import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
   import com.quantafic.JWTSecurity.Enum.ScoreProvider;
   import jakarta.persistence.*;
import lombok.*;
   import org.hibernate.annotations.GenericGenerator;

   import java.time.Instant;
import java.util.UUID;

    @Entity
    @Table(name = "bureau")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public class Bureau {
        @Id
        @GeneratedValue(generator = "bureau-id-gen")
        @GenericGenerator(
                name = "bureau-id-gen",
                strategy = "com.quantafic.JWTSecurity.Model.Generator.BureauIdGenerator"
        )

        @Column(name = "bureau_id", nullable = false)
        private String bureauId;

        @Column(name = "application_id")
        private String applicationId; // Make sure Application entity exists

        @Enumerated(EnumType.STRING)
        @Column(name = "provider", nullable = false)
        private ScoreProvider provider;


        @Column(name = "score")
        private Integer score; // should be between 100–900 (validated via service or DTO layer)

        @Column(name = "summary_json")
        private String summaryJson;
//
//        @OneToOne(mappedBy = "bureau", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//        @ToString.Exclude
//        @EqualsAndHashCode.Exclude
//        private CreditReport creditReport;

        @Builder.Default
        @Column(name = "pulled_at", nullable = false)
        private Instant pulledAt = Instant.now();

        @Column(name = "reference_id")
        private String referenceId;


    }


