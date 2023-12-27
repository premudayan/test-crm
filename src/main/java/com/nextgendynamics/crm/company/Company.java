package com.nextgendynamics.crm.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String companyName;
    private String shortName;
    private String industry;
    private String companyType;
    private Long addressId;

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

    @OneToMany
    @JsonIgnore
    private List<CompanyEmail> companyEmails = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<CompanyPhone> companyPhones = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<CompanyAddress> companyAddresses = new ArrayList<>();
}
