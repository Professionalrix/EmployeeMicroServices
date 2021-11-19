package com.contact.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.model.Contact;
import com.contact.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public Contact createContact(Contact contact) {
		
		return contactRepository.save(contact);
	}

	@Override
	public List<Contact> getAllContact() {

		return contactRepository.findAll();
	}

	@Override
	public Contact getContactById(Long id) {

	Contact contact= null;
		try {
			List<Contact> list = getAllContact();
			contact = list.stream().filter(e->e.getId()==id).findFirst().get();
				
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			return contact;
	}

	@Override
	public Contact updateContact(Contact contact, Long id) {

		 if(contactRepository.findById(id).isPresent()) {
			 Contact con= contactRepository.findById(id).get();
			 con.setAdd1(contact.getAdd1());
			 con.setAdd2(contact.getAdd2());
			 con.setCountry(contact.getCountry());
			 con.setLandMark(contact.getLandMark());
			 con.setMobileNo(contact.getMobileNo());
			 con.setPinCode(contact.getPinCode());
			 
			 contactRepository.save(con);
			 return con;
		 }
		return null;
	}

	@Override
	public Contact deleteContactById(Long id) {
		 List<Contact> contacts = contactRepository.findAll();
		 Iterator<Contact> iterator = contacts.iterator();
		 while(iterator.hasNext()) {
			 Contact contact = iterator.next();
			 if(contact.getId()==id) {
				 iterator.remove();
				 return contact;
			 }
		 }
		 return null;

		
	}

}
