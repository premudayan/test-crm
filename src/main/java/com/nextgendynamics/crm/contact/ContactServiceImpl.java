package com.nextgendynamics.crm.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private  ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact findContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public List<Contact> findByNameAndJobTitle(String firstName, String lastName, String jobTitle) {
        return contactRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndJobTitleContainingIgnoreCase(firstName, lastName, jobTitle );
    }

    @Override
    public List<Contact> findByNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth) {
        return contactRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndDateOfBirth(firstName, lastName, dateOfBirth);
    }

    @Override
    public List<Contact> findByName(String firstName, String lastName, Optional<String> middleName) {
        if (!middleName.isPresent() || middleName.get().isEmpty() ){
            return contactRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName );
        }
        else {
            return contactRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndMiddleNameContainingIgnoreCase(
                    firstName, lastName, middleName
            );
        }
        //return contactRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndMiddleNameContainingIgnoreCase(firstName, lastName, middleName);
    }

    @Override
    public boolean updateContact(Long id, Contact updatedContact) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));

        if (updatedContact.getFirstName() != null) {
            existingContact.setFirstName(updatedContact.getFirstName());
        }
        if (updatedContact.getLastName() != null) {
            existingContact.setLastName(updatedContact.getLastName());
        }
        if (updatedContact.getMiddleName() != null) {
            existingContact.setMiddleName(updatedContact.getMiddleName());
        }
        if (updatedContact.getDateOfBirth() != null) {
            existingContact.setDateOfBirth(updatedContact.getDateOfBirth());
        }
        //TODO : Set for all fields

            contactRepository.save(existingContact);
            return true;

    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public boolean deleteContactById(Long id) {
        if ( contactRepository.existsById(id)){
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
