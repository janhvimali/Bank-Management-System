package com.quantafic.JWTSecurity.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quantafic.JWTSecurity.Enum.CustomerDecision;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "loan_offers")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoanOffers {

    @Id
    @Column(name = "offer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @Column(name ="application_id")
    private String applicationId;

    @Column(name ="loan_product_id")
    private Long loanProductId;

    @Column(name ="offer_amount")
    private BigDecimal offerAmount;

    @Column(name ="roi")
    private BigDecimal roi;

    @Column(name ="interest")
    private BigDecimal interest;

    @Column(name ="processing_fee")
    private BigDecimal processingFee;

    @Column(name = "emi_amount")
    private BigDecimal emiAmount;

    @Column(name = "net_payable")
    private BigDecimal netPayable;

    @Builder.Default
    @Column(name ="insurance")
    private BigDecimal insurance = BigDecimal.ZERO;

    @Column(name ="tenure")
    private Integer tenure;

    @Column(name = "net_disbursal")
    private BigDecimal netDisbursal;

    @Column(name ="valid_until")
    private Instant validUntil;

    @Builder.Default
    @Column(name ="created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "is_send")
    private Boolean isSend;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_decision")
    private CustomerDecision custDecision;

    @Override
    public String toString() {
        return "LoanOffers{" +
                "offerId=" + offerId +
                ", applicationId='" + applicationId + '\'' +
                ", loanProductId=" + loanProductId +
                ", offerAmount=" + offerAmount +
                ", roi=" + roi +
                ", interest=" + interest +
                ", processingFee=" + processingFee +
                ", emiAmount=" + emiAmount +
                ", netPayable=" + netPayable +
                ", insurance=" + insurance +
                ", tenure=" + tenure +
                ", netDisbursal=" + netDisbursal +
                ", validUntil=" + validUntil +
                ", createdAt=" + createdAt +
                ", isSend=" + isSend +
                ", custDecision=" + custDecision +
                '}';
    }
}



