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
public class CompanyEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;

    private String emailAddress;
    private String emailType; // work, personal).
    private boolean isPrimary;
    private boolean optInStatus;
    private boolean isVerified;
    private LocalDate dateVerified;
    private String source;
    private LocalDate lastEmailSent;
    private String emailCampaignHistory;
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
