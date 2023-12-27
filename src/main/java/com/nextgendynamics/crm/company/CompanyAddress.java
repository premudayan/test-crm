package com.nextgendynamics.crm.company;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Data
public class CompanyAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;

    private String  addressType; // billing, shipping, home).
    private String streetAddress;
    private String city;
    private String stateProvinceCode;
    private String zip;
    private String country;

    private boolean isPrimary;
    private boolean optInStatus;
    private String verificationStatus;
    private String source; // form submission, CRM import).
    private LocalDate dateVerified;
    private LocalDate lastMailSent;
    private String mailHistory;
    private String notes;

    @CreatedDate
    @Column( nullable = false, updatable = false )
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;
    @CreatedBy
    @Column( nullable = false, updatable = false )
    private String createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;

}
