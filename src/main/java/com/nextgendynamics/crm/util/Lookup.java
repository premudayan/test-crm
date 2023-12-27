package com.nextgendynamics.crm.util;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Lookup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lookupType; //e.g., PhoneType, EmailType, CustomerType
    private String lookupCode; //e.g., "MOB" for Mobile, "WRK" for Work).
    private String lookupValue; // "Mobile," "Work").
    private boolean isActive;
    private int sortOrder;
    private String customColumn1;
    private String customColumn2;
    private String customColumn3;

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
