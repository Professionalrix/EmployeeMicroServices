package com.contact.service;

import java.util.List;

import com.contact.model.Contact;

public interface ContactService {


	public Contact createContact(Contact contact);
	public List<Contact> getAllContact();
	public Contact getContactById(Long id);
	public Contact updateContact(Contact contact,Long id);
	public Contact deleteContactById(Long id);
	
	
	
}
