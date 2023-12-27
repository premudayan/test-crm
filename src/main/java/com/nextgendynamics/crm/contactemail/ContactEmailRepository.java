package com.nextgendynamics.crm.contactemail;

import com.nextgendynamics.crm.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactEmailRepository extends JpaRepository<ContactEmail, Long> {

    ContactEmail findFirstByContactIdAndEmailTypeAndIsActive(
            Long contactId, String emailType, boolean isActive );
}
