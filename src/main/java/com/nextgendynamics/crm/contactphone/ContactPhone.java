package com.nextgendynamics.crm.contactphone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nextgendynamics.crm.contact.Contact;
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
public class ContactPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Contact contact;

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
