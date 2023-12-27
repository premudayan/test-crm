package com.nextgendynamics.crm.contactemail;

import com.nextgendynamics.crm.contact.Contact;

import com.nextgendynamics.crm.contact.ContactRepository;
import com.nextgendynamics.crm.contact.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContactEmailServiceImpl implements ContactEmailService {

    @Autowired
    private ContactEmailRepository contactEmailRepository;

    @Autowired
    private ContactRepository contactRepository;

    public ContactEmailServiceImpl(ContactEmailRepository contactEmailRepository, ContactRepository contactRepository) {
        this.contactEmailRepository = contactEmailRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public List<ContactEmail> findAllContactEmailsByContactId(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + contactId));
        return  contact.getContactEmails();

    }

    @Override
    public ContactEmail findContactEmailById(Long id) {
        ContactEmail contactEmail = contactEmailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Email record not found with id: " + id));
        return contactEmail;
    }

    @Override
    public boolean updateContactEmail(Long contactId, Long id, ContactEmail updatedContactEmail) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + contactId));
        ContactEmail existingContactEmail = contactEmailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact Email record not found with id: " + id));

        if (updatedContactEmail.getEmailAddress() != null) {
            existingContactEmail.setEmailAddress(updatedContactEmail.getEmailAddress());
        }
        contactEmailRepository.save(existingContactEmail);
        return true;
    }

    @Override
    public boolean updateContactEmail(Long contactId, ContactEmail updatedContactEmail) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + contactId));
        ContactEmail existingContactEmail = null;

        if ( updatedContactEmail.getId() != null ) {
            existingContactEmail = contactEmailRepository.findById(updatedContactEmail.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contact Email record not found with id: " + updatedContactEmail.getId()));
        } else if (updatedContactEmail.getEmailType() != null) {
            existingContactEmail = contactEmailRepository.findFirstByContactIdAndEmailTypeAndIsActive(
                    contactId,
                    updatedContactEmail.getEmailType().toUpperCase(),
                    updatedContactEmail.getIsActive() == null ? true : updatedContactEmail.getIsActive());
        } else {
            //Throw error
            return false;
        }

        if ( updatedContactEmail != null ){
            if ( updatedContactEmail.getEmailAddress() != null) {
                existingContactEmail.setEmailAddress(updatedContactEmail.getEmailAddress());
            }
            if ( updatedContactEmail.getEmailType() != null) {
                existingContactEmail.setEmailType(updatedContactEmail.getEmailType());
            }
            if ( updatedContactEmail.getIsPrimary() != null) {
                existingContactEmail.setIsPrimary(updatedContactEmail.getIsPrimary());
            }
            if ( updatedContactEmail.getOptInStatus() != null) {
                existingContactEmail.setOptInStatus(updatedContactEmail.getOptInStatus());
            }
            if ( updatedContactEmail.getSource() != null) {
                existingContactEmail.setSource(updatedContactEmail.getSource());
            }
            if ( updatedContactEmail.getNotes() != null) {
                existingContactEmail.setNotes(updatedContactEmail.getNotes());
            }
            if ( updatedContactEmail.getIsActive() != null && updatedContactEmail.getIsActive() != updatedContactEmail.getIsActive()) {
                existingContactEmail.setIsActive(updatedContactEmail.getIsActive());
                existingContactEmail.setStatusDate(LocalDate.now());
            }
            contactEmailRepository.save(existingContactEmail);
        }
        return true;
    }

    @Override
    public ContactEmail createContactEmail(Long contactId, ContactEmail contactEmail) {
        ContactEmail savedContact = null;
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + contactId));
        ContactEmail existingContactEmail = null;

            existingContactEmail = contactEmailRepository.findFirstByContactIdAndEmailTypeAndIsActive(
                    contactId,
                    contactEmail.getEmailType().toUpperCase(),
                    contactEmail.getIsActive() );

        if ( existingContactEmail == null ){
            if ( contactEmail.getEmailAddress() != null && contactEmail.getEmailType() !=null ){
                contactEmail.setContact(contact);
                contactEmail.setIsActive(true);
                contactEmail.setStatusDate(LocalDate.now());
                contactEmail.setOptInStatus(true);
                if (contactEmail.getIsPrimary() == null ){
                    contactEmail.setIsPrimary(false);
                }
                savedContact = contactEmailRepository.save(contactEmail);
            }
        }
        return savedContact;
    }

    @Override
    public String deleteContactEmailById(Long id) {
        ContactEmail existingContactEmail = contactEmailRepository.findById(id).orElse(null);
        if ( existingContactEmail != null){
            contactEmailRepository.deleteById(id);
            return "success";
        }
        return "Failed to delete. Unable to find the Contact Email Id:"+ id;
    }

    @Override
    public String deleteContactEmailById(Long contactId, ContactEmail deleteContactEmail ) {
        if ( deleteContactEmail != null ){
            ContactEmail existingContactEmail = null;
            if ( deleteContactEmail.getId() != null){
                existingContactEmail = contactEmailRepository.findById(deleteContactEmail.getId()).orElse(null);
                if ( existingContactEmail != null){
                    contactRepository.deleteById(existingContactEmail.getId());
                    return "success";
                } else{
                    return "Failed to delete. Unable to find the Contact Email Id:"+ existingContactEmail.getId();
                }
            } else if ( deleteContactEmail.getEmailType() != null){
                existingContactEmail = contactEmailRepository.findFirstByContactIdAndEmailTypeAndIsActive(
                        contactId,
                        deleteContactEmail.getEmailType().toUpperCase(),
                        deleteContactEmail.getIsActive() == null ? true : deleteContactEmail.getIsActive());
                if ( existingContactEmail != null){
                    contactEmailRepository.deleteById(existingContactEmail.getId());
                    return "success";
                } else{
                    return "Failed to delete. Unable to find the Contact Email for contactId:"+ contactId + " Email Type :"+ deleteContactEmail.getEmailType();
                }
            }
            return "Failed to delete. Either ContactEmailId or EmailType must be provided";
        }
        return "Failed to delete. ContactEmail body missing";
    }
}
