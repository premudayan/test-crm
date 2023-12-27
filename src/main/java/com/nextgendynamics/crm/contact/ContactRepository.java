package com.nextgendynamics.crm.contact;

import com.nextgendynamics.crm.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndMiddleNameContainingIgnoreCase(
            String firstName, String lastName, Optional<String> middleName);
    List<Contact> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
            String firstName, String lastName);
    List<Contact> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndJobTitleContainingIgnoreCase(
            String firstName, String lastName, String jobTitle);

    List<Contact> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndDateOfBirth(
            String firstName, String lastName, LocalDate dateOfBirth);

}
