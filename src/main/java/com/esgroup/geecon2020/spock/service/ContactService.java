package com.esgroup.geecon2020.spock.service;

import com.esgroup.geecon2020.spock.model.Contact;
import com.esgroup.geecon2020.spock.model.ContactCreateDTO;
import com.esgroup.geecon2020.spock.model.ContactExistsException;
import com.esgroup.geecon2020.spock.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

  private final ContactRepository contactRepo;

  public ContactService(ContactRepository contactRepo) {
    this.contactRepo = contactRepo;
  }

  public Contact createContact(ContactCreateDTO request) throws ContactExistsException {
    var existingContactMaybe = contactRepo.findByEmail(request.getEmail());

    if(existingContactMaybe.isPresent()) {
      throw new ContactExistsException("Contact with given email already exists: " + request.getEmail());
    }

    var newContact = new Contact(null,
                                 request.getEmail(),
                                 request.getFirstName(),
                                 request.getLastName());
    return contactRepo.save(newContact);
  }

  public void deleteContact(String id) {
    contactRepo.deleteById(id);
  }

}
