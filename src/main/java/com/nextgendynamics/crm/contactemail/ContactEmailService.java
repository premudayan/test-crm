package com.nextgendynamics.crm.contactemail;

import com.nextgendynamics.crm.contact.Contact;


import java.util.List;

public interface ContactEmailService {

    List<ContactEmail> findAllContactEmailsByContactId(Long contactId);
    ContactEmail findContactEmailById(Long id);
    boolean updateContactEmail(Long contactId, Long id, ContactEmail contactEmail);
    boolean updateContactEmail(Long contactId, ContactEmail contactEmail);
    ContactEmail createContactEmail(Long contactId, ContactEmail contactEmail);
    String deleteContactEmailById(Long id);
    String deleteContactEmailById(Long contactId, ContactEmail contactEmail );
}
