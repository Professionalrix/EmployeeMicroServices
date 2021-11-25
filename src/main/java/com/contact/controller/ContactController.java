package com.contact.controller;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.contact.exception.ContactNotFoundException;
import com.contact.model.Contact;
import com.contact.service.ContactService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/contacts")
public class ContactController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ContactService contactService;
	
	
	@GetMapping("/hello-world-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language",required = false) Locale locale)  {
		
		
		return messageSource.getMessage("good.morning.message", null,"Default Message", locale);
	}
	

	@PostMapping("") 
	public ResponseEntity<Contact>addEmployee(@Valid @RequestBody Contact contact) { 
		Contact con =  null;
	try {
		con = contactService.createContact(contact);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(con.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	catch (Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
 }
	
	
	@GetMapping("")
	public ResponseEntity<List<Contact>>  getAllContacts()
	{
			List<Contact> allContact = contactService.getAllContact();
			if(allContact.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
			}
			
			return ResponseEntity.of(Optional.of(allContact));
	}
	
	
	// get Contact by id
	@GetMapping("/{id}")
	public EntityModel<Contact> getContact(@PathVariable("id")Long id)
	{
		Contact contact = contactService.getContactById(id);
			if(contact==null) {
				throw new ContactNotFoundException("id-"+id);	
			}
			
			EntityModel<Contact> model = EntityModel.of(contact);
			WebMvcLinkBuilder linkToContact = linkTo(methodOn(this.getClass()).getAllContacts());
			model.add(linkToContact.withRel("all-contacts"));
			
			return model;
	
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Contact> updateContact(@RequestBody Contact contact, @PathVariable("id")Long id)
	{
		try {
			Contact updateContact = this.contactService.updateContact(contact, id);
			if(updateContact!=null) {
				return ResponseEntity.ok().body(updateContact);
			}
		
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
			}
		}catch (Exception e) {
			e.printStackTrace( );
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	
	}
	
	
	//delete employee
	
	@DeleteMapping("/{id}")
	public void deleteContact(@PathVariable("id")Long id)
	{
		Contact contact = contactService.deleteContactById(id);
		if(contact==null) 
			throw new ContactNotFoundException("id-"+id);
			
		
			
	}
	
	//Implementation of Dynamic Filtering for Restful Service
	@GetMapping("/filter")
	public ResponseEntity<MappingJacksonValue>  getAllContactsByDyanmicFilter()
	{
			List<Contact> allContact = contactService.getAllContact();
			if(allContact.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
			}
			
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","add1");
			SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("someBean", filter);
			MappingJacksonValue mapping = new MappingJacksonValue(allContact);
			mapping.setFilters(filters);
			
			return ResponseEntity.of(Optional.of(mapping));
	}
	
	
	@GetMapping(value="/filter/param",params = "version=1")
	public ResponseEntity<MappingJacksonValue>  getAllContactsDyanmicFilterByParam()
	{
			List<Contact> allContact = contactService.getAllContact();
			if(allContact.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
			}
			
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","add1");
			SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("someBean", filter);
			MappingJacksonValue mapping = new MappingJacksonValue(allContact);
			mapping.setFilters(filters);
			
			return ResponseEntity.of(Optional.of(mapping));
	}
	

	@GetMapping(value="/filter/header",headers = "X-API-VERSION=1")
	public ResponseEntity<MappingJacksonValue>  getAllContactsDyanmicFilterByHeader()
	{
			List<Contact> allContact = contactService.getAllContact();
			if(allContact.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
			}
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","add1");
			SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("someBean", filter);
			MappingJacksonValue mapping = new MappingJacksonValue(allContact);
			mapping.setFilters(filters);
			
			return ResponseEntity.of(Optional.of(mapping));
	}
	

	@GetMapping(value="/filter/produces",produces = "application/vnd.company.app-v1+json")
	public ResponseEntity<MappingJacksonValue>  getAllContactsDyanmicFilterByProduces()
	{
			List<Contact> allContact = contactService.getAllContact();
			if(allContact.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
			}
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","add1");
			SimpleFilterProvider filters = new SimpleFilterProvider().addFilter("someBean", filter);
			MappingJacksonValue mapping = new MappingJacksonValue(allContact);
			mapping.setFilters(filters);
			
			return ResponseEntity.of(Optional.of(mapping));
	}
	
	
	
}
