package com.nextgendynamics.crm.contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("v1/contacts")
public class ContactController {
    //for internationalization of messages

    @Autowired
    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public MappingJacksonValue getAllContacts(){
        System.out.println("calling getAllContacts **************");
//        return new ResponseEntity<>(contactService.findAllContacts(), HttpStatus.OK);
        return excludeFields(contactService.findAllContacts(), new String[] {""} );
    }

    @GetMapping("/{id}")
    public MappingJacksonValue getContactById(@PathVariable Long id) {
        //Predicate<? super Contact> predicate = contact -> contact.getId().equals(id);
        Contact contact = contactService.findContactById(id);
        if (contact == null ){
            throw new ContactNotFoundException("id:"+id);
        }
        EntityModel<Contact> entityModel = EntityModel.of(contact);

        WebMvcLinkBuilder link1 = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(this.getClass())
                        .searchContactsByName(
                                contact.getFirstName(),
                                contact.getLastName(),
                                Optional.ofNullable(contact.getMiddleName())));
        entityModel.add(link1.withRel("search by name"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        WebMvcLinkBuilder link2 = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(this.getClass())
                        .searchContactsByNameAndDOB(
                                contact.getFirstName(),
                                contact.getLastName(),
                                contact.getDateOfBirth() == null ? null: contact.getDateOfBirth().format(formatter)
                        ));

        entityModel.add(link2.withRel("search by DOB"));
        return excludeFields(entityModel, new String[] {""} );
    }

    @GetMapping("/limited/{id}")
    public MappingJacksonValue getContactByIdLimited(@PathVariable Long id) {
        //Predicate<? super Contact> predicate = contact -> contact.getId().equals(id);
        Contact contact = contactService.findContactById(id);

        if (contact == null ){
            throw new ContactNotFoundException("id:"+id);
        }
        String[] incfields = {"id", "firstName", "middleName", "lastName", "namePrefix", "nameSuffix"};

        return includeFields(contact, incfields );
    }

    private MappingJacksonValue includeFields(Contact contact, String[] incFields){
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(contact);
        SimpleBeanPropertyFilter filter;

        filter = SimpleBeanPropertyFilter.filterOutAllExcept(incFields);
        FilterProvider filters = new SimpleFilterProvider().addFilter("ContactFilter", filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    private MappingJacksonValue excludeFields(Contact contact, String[] exFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("ContactFilter",
                SimpleBeanPropertyFilter.serializeAllExcept(exFields));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(contact);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    private MappingJacksonValue excludeFields(EntityModel em, String[] exFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("ContactFilter",
                SimpleBeanPropertyFilter.serializeAllExcept(exFields));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(em);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }


    private MappingJacksonValue excludeFields(List<Contact> contacts, String[] exFields) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("ContactFilter",
                SimpleBeanPropertyFilter.serializeAllExcept(exFields));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(contacts);
        mappingJacksonValue.setFilters(filterProvider);
        return mappingJacksonValue;
    }

    @GetMapping("/searchbyname")
    public MappingJacksonValue searchContactsByName(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) Optional<String> middleName) {

        List<Contact> contacts = contactService.findByName(firstName, lastName, middleName);
        return excludeFields(contacts, new String[] {""} );
//        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/searchbydob")
    public MappingJacksonValue searchContactsByNameAndDOB(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String dateOfBirth) {

        LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Contact> contacts = contactService.findByNameAndDateOfBirth(firstName, lastName, dob);
        return excludeFields(contacts, new String[] {""} );
    }

    @GetMapping("/searchbyjobtitle")
    public MappingJacksonValue searchContactsByNameAndJobTitle(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String jobTitle) {

        List<Contact> contacts = contactService.findByNameAndJobTitle(firstName, lastName, jobTitle);
        return excludeFields(contacts, new String[] {""} );
    }

    @PostMapping
    public ResponseEntity<String> addContact(@Valid @RequestBody Contact contact){
        try{
            Contact savedContact = contactService.createContact(contact);
            URI location = ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(savedContact.getId()).toUri();
            return ResponseEntity.created(location).build();
            //return new ResponseEntity<>("Contact added successfully", HttpStatus.CREATED);

        } catch ( Exception e){
            System.out.println("calling addContact **************- Error:" + e.getMessage());
            return new ResponseEntity<>("Unable to add contact ", HttpStatus.EXPECTATION_FAILED );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        if(  contactService.updateContact(id, updatedContact) ) {
            return new ResponseEntity<>("Contact updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to update contact ", HttpStatus.EXPECTATION_FAILED );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id){
        boolean isDeleted = contactService.deleteContactById(id);
        if(isDeleted)
            return new ResponseEntity<>("Contact deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Contact record not found", HttpStatus.NOT_FOUND);
    }

}
