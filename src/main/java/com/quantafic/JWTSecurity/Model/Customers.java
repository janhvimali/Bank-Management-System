package com.quantafic.JWTSecurity.Model;

import com.quantafic.JWTSecurity.Enum.CustomerStatus;
import com.quantafic.JWTSecurity.Enum.EmploymentType;
import com.quantafic.JWTSecurity.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.time.LocalDate;


@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customers {

    @Id
    @Column(name = "customer_id", nullable = false, updatable = false)
    private String customerId;

    @Column(name="email" , length = 40,unique = true)
    private String email;

    @Column(name="alt_email" , length = 40,unique = true)
    private String altEmail;

    @Builder.Default
    @Column(name = "is_verified")
    private boolean emailVerified=false;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "alt_phone", length = 15)
    private String altPhone;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name", length = 40)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", length = 40)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

//    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "yearly_income")
    private String yearlyIncome;

    // Current Address
    @Column(name = "curr_address_line1")
    private String currAddressLine1;

    @Column(name = "curr_address_line2")
    private String currAddressLine2;

    @Column(name = "curr_city")
    private String currCity;

    @Column(name = "curr_taluka")
    private String currTaluka;

    @Column(name = "curr_district")
    private String currDistrict;

    @Column(name = "curr_state")
    private String currState;

    @Column(name = "curr_postal_code", nullable = false)
    private String currPostalCode;

    // Permanent Address
    @Column(name = "perm_address_line1")
    private String permAddressLine1;

    @Column(name = "perm_address_line2")
    private String permAddressLine2;

    @Column(name = "perm_city")
    private String permCity;

    @Column(name = "perm_taluka")
    private String permTaluka;

    @Column(name = "perm_district")
    private String permDistrict;

    @Column(name = "perm_state")
    private String permState;

    @Column(name = "perm_postal_code", nullable = false)
    private String permPostalCode;

    @Column(name = "national_id_type", nullable = false)
    private String nationalIdType;

    @Column(name = "national_id_number", nullable = false, unique = true)
    private String nationalIdNumber;

//    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private String employmentType;

    @Column(name = "consent")
    private Boolean consent;

//    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private String Customerstatus;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Builder.Default
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();
}

