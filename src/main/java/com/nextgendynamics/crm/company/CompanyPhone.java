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
public class CompanyPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long companyId;
    private String countryCode;
    private String phoneNumber;
    private String formattedPhoneNumber;
    private String phoneType;
    private Boolean isPrimary;
    private String callOptInoutStatus;  //Opt-In , Opt-Out
    private String verificationStatus;
    private String source;
    private LocalDate lastCallDate;
    private String lastCallOutcome; //Specify the outcome of the last phone call (e.g., answered, voicemail).
    private String callHistory;
    private String smsOptInOutStatus;  //Opt-In , Opt-Out
    private String lastSMSSent;
    private String smsHistory;
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
