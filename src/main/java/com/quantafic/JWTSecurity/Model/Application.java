    package com.quantafic.JWTSecurity.Model;

    // File: com/quantafic/JWTSecurity/Model/Application/Application.java

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.quantafic.JWTSecurity.Enum.ApplicationStatus;
    import com.quantafic.JWTSecurity.Enum.LoanProduct;
    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.GenericGenerator;

    import java.math.BigDecimal;
    import java.time.Instant;
    import java.util.List;

    @Entity
    @Table(name = "applications")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public class Application {

        @Id
        @GeneratedValue(generator = "application-id-gen")
        @GenericGenerator(
                name = "application-id-gen",
                strategy = "com.quantafic.JWTSecurity.Model.Generator.ApplicationIdGenerator"
        )
        @Column(name = "application_id", length = 50, updatable = false)
        private String applicationId;




        @ManyToOne(fetch = FetchType.LAZY )
        @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonIgnore
        private Customers customer;

        @Enumerated(EnumType.STRING)
        @Column(name = "product_code")
        private LoanProduct loanProduct;

        @Column(name = "requested_amount")
        private BigDecimal requestedAmount;

        @Column(name = "requested_tenure")
        private Integer requestedTenure;

        @Column(name = "loan_purpose")
        private String loanPurpose;

        @Column(name = "preferred_emi_date")
        private Integer preferredEmiDate;


        @Column(name = "gross_monthly_income")
        private BigDecimal grossMonthlyIncome;

        @Column(name = "net_monthly_income")
        private BigDecimal netMonthlyIncome;

        //not always req
        @Column(name = "existing_emi_obligation")
        private BigDecimal existingEmiObligation;

        @Enumerated(EnumType.STRING)
        @Builder.Default
        @Column(name = "status", nullable = false)
        private ApplicationStatus status = ApplicationStatus.DRAFT;

        @Column(name = "branch_code", length = 10)
        private String branchCode;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "assigned_to", nullable = true , referencedColumnName = "")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        @JsonIgnore
        private Users assignedTo;

        @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        @JsonIgnore
        private List<ApplicationStatusLogs> statusLogs;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "bureau_id", nullable = true , referencedColumnName = "")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        @JsonIgnore
        private Bureau bureau;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "creditReportId", nullable = true , referencedColumnName = "")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        @JsonIgnore
        private CreditReport creditReport;

        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "offer_id", nullable = true , referencedColumnName = "")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        @JsonIgnore
        private LoanOffers loanOffer;



        @Column(name = "created_at", nullable = false, updatable = false)
        private Instant createdAt;

        @Column(name = "updated_at", nullable = false)
        private Instant updatedAt;

        @Column(name = "e_signed")
        private Boolean eSigned;

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "updated_by", nullable = false)
    //    @ToString.Exclude
    //    @EqualsAndHashCode.Exclude
    //    private Users updatedBy;

        @PrePersist
        public void prePersist() {
            Instant now = Instant.now();
            this.createdAt = now;
            this.updatedAt = now;
            this.eSigned = false;
        }

        @PreUpdate
        public void preUpdate() {
            this.updatedAt = Instant.now();
        }

        @Override
        public String toString() {
            return "Application{" +
                    "applicationId='" + applicationId + '\'' +
                    ", customer=" + customer +
                    ", loanProduct=" + loanProduct +
                    ", requestedAmount=" + requestedAmount +
                    ", requestedTenure=" + requestedTenure +
                    ", loanPurpose='" + loanPurpose + '\'' +
                    ", preferredEmiDate=" + preferredEmiDate +
                    ", existingEmiObligation=" + existingEmiObligation +
                    ", status=" + status +
                    ", branchCode='" + branchCode + '\'' +
                    ", assignedTo=" + assignedTo +
                    ", statusLogs=" + statusLogs +
                    ", createdAt=" + createdAt +
                    ", updatedAt=" + updatedAt +
                    '}';
        }
    }
