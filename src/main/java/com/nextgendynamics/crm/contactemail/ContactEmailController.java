package com.nextgendynamics.crm.contactemail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("v1/contactemails")
public class ContactEmailController {

    @Autowired
    private ContactEmailService contactEmailService;

    public ContactEmailController(ContactEmailService contactEmailService) {
        this.contactEmailService = contactEmailService;
    }

    @GetMapping("/emailbyid")
    public ResponseEntity<ContactEmail> getContactEmailById(
            @RequestParam(name = "contactEmailId") Long id ){
        ContactEmail contactEmail = contactEmailService.findContactEmailById(id);
        if ( contactEmail!= null){
            return new ResponseEntity<>(contactEmail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/emailbycontactid")
    public ResponseEntity<List<ContactEmail>> getAllContactEmailsByContact(
            @RequestParam(name = "contactId") Long contactId) {
        List<ContactEmail> contactEmails = contactEmailService.findAllContactEmailsByContactId(contactId);
        if (contactEmails.stream().count() > 0) {
            return new ResponseEntity<>(contactEmails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addEmail")
    public ResponseEntity<String> addContactEmail(
            @RequestParam(name = "contactId") Long contactId,
            @RequestBody ContactEmail contactEmail){
        try {
            ContactEmail savedContactEmail = contactEmailService.createContactEmail(contactId, contactEmail);
            RequestMapping classMapping = this.getClass().getAnnotation(RequestMapping.class);
            String basePath = classMapping.value()[0];
            System.out.println("Base Path: " + basePath);

            URI currentUri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
            // Build the new URI with the desired path and query parameter
            URI location;
            location = UriComponentsBuilder.fromUri(currentUri)
                    .replacePath("/v1/contactemails/emailbyid")
                    .replaceQuery("contactEmailId=" + savedContactEmail.getId())
                    .build().toUri();
            return ResponseEntity.created(location).build();

        } catch (Exception e){
            System.out.println("Calling addContactEmail **************- Error:" + e.getMessage());
            return new ResponseEntity<>("Unable to add contact email", HttpStatus.EXPECTATION_FAILED );
        }
    }

    @PutMapping("/updateContactEmail")
    public ResponseEntity<String> updateContactEmail(
            @RequestParam(name = "contactId") Long contactId,
            @RequestBody ContactEmail contactEmail){
        boolean updated = contactEmailService.updateContactEmail(contactId, contactEmail);
        if(updated)
            return new ResponseEntity<>("Contact Email updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Contact Email update failed!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateEmail")
    public ResponseEntity<String> updateContactEmail(
            @RequestParam(name = "contactId") Long contactId,
            @RequestParam(name = "id") Long id,
            @RequestBody ContactEmail contactEmail){
        boolean updated = contactEmailService.updateContactEmail(contactId, id, contactEmail);
        if(updated)
            return new ResponseEntity<>("Contact Email updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Contact Email update failed!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteEmail")
    public ResponseEntity<String> deleteContactEmail(@RequestParam(name = "id") Long id){
        String deleted = contactEmailService.deleteContactEmailById(id);
        if ( deleted.equals("success")){
            return new ResponseEntity<>("Contact Email deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleted, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteContactEmail")
    public ResponseEntity<String> deleteContactEmail(
            @RequestParam(name = "contactId") Long contactId,
            @RequestBody ContactEmail deleteContactEmail){

        String deleted = contactEmailService.deleteContactEmailById(contactId, deleteContactEmail);
        if ( deleted.equals("success") ){
            return new ResponseEntity<>("Contact Email deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleted, HttpStatus.NOT_FOUND);
        }
    }
}
