package com.nextgendynamics.crm.contact;

import com.nextgendynamics.crm.contact.Contact;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> findAllContacts();
    Contact findContactById(Long id);
    boolean updateContact(Long id, Contact contact);
    Contact createContact(Contact contact);
    boolean deleteContactById(Long id);

    List<Contact> findByNameAndJobTitle(String firstName, String lastName, String jobTitle);
    List<Contact> findByNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
    List<Contact> findByName( String firstName, String lastName, Optional<String> middleName);

}
