package com.nextgendynamics.crm.contactemail;

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
import java.util.Date;

@Entity
@Data
public class ContactEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false)
    private String emailAddress;
    @Column( nullable = false)
    private String emailType; // work, personal).
    private Boolean isPrimary;
    private Boolean optInStatus;
    private Boolean isVerified;
    private LocalDate dateVerified;
    private String source;
    private LocalDate lastEmailSent;
    private String emailCampaignHistory;
    private String notes;
    private Boolean isActive;
    private LocalDate statusDate;

//    @CreatedDate
//    @Column( nullable = false, updatable = false )
    private LocalDateTime createdDate;
//    @LastModifiedDate
//    @Column(insertable = false)
    private LocalDateTime lastModified;
//    @CreatedBy
//    @Column( nullable = false, updatable = false )
    private String createdBy;
//    @LastModifiedBy
//    @Column(insertable = false)
    private String lastModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Contact contact;

    public ContactEmail() {
        this.isActive = true;
        this.statusDate = LocalDate.now();
        this.optInStatus = true;
    }
}
