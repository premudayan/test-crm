package com.nextgendynamics.crm.contact;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nextgendynamics.crm.contactaddress.ContactAddress;
import com.nextgendynamics.crm.contactemail.ContactEmail;
import com.nextgendynamics.crm.contactphone.ContactPhone;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@JsonFilter("ContactFilter")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customContactId;
    @Column(nullable = false)
    @Size(min = 2, message = "First name must have at least 2 characters")
    private String firstName;
    private String middleName;
    @Column(nullable = false)
    @Size(min = 2, message = "Last name must have at least 2 characters")
    private String lastName;
    private String namePrefix;
    private String nameSuffix;
    @JsonIgnore
    private String passcode;
    private Long primaryEmailId;
    private Long primaryPhoneId;
    private Long primaryAddressId;
    private String jobTitle;
    private String industry;
    @Past(message = "Birth date cannot be a future date")
    private LocalDate dateOfBirth;
    private String contactType;
    private String preferredContactMethod;
    private String notes;
    private Boolean isActive;
    private LocalDate statusDate;
    private LocalDate customerSince;
    private String preferredLanguage;
    private String referralSource;
    private String customerInteractions;
    private String customerRating;
    private String socialMediaProfiles;
    private String socialMediaHandles;
    private String customerFeedback;
    private String customerPreferences;
    private String preferredCommunicationTime;
    private String legalEntityType;
    private String subscriptionDetails;
    private LocalDate renewalDate;
    private String customerPortalAccess;
    private String industryChallenges;
    private String contactRole;
    private String linkedContacts;
    private String contactRating;
    private String educationQualifications;
    private LocalDate workAnniversary;
    private String interestsHobbies;
    private String languagesSpoken;
    private String meetingPreferences;
    private String projectInvolvement;
    private String industryCertifications;
    private String travelPreferences;
    private String linkedInRecommendations;
    private String eventAttendanceHistory;
    private String contactImage;
    private String emergencyContactInformation;
    private String customFields;

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

    @OneToMany(mappedBy = "contact")
    @JsonIgnore
    private List<ContactEmail> contactEmails;

    @OneToMany
    @JsonIgnore
    private List<ContactPhone> contactPhones = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    private List<ContactAddress> contactAddresses = new ArrayList<>();

}
