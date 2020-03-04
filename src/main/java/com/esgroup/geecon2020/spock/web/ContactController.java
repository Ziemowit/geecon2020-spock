package com.esgroup.geecon2020.spock.web;

import com.esgroup.geecon2020.spock.model.ContactCreateDTO;
import com.esgroup.geecon2020.spock.model.ContactExistsException;
import com.esgroup.geecon2020.spock.model.ContactResponseDTO;
import com.esgroup.geecon2020.spock.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

  private final ContactService contactService;

  @PostMapping("")
  public ContactResponseDTO create(@RequestBody ContactCreateDTO request) throws ContactExistsException {
    var createdContact = contactService.createContact(request);

    return new ContactResponseDTO(createdContact.getId(),
                                  createdContact.getEmail(),
                                  createdContact.getFirstName(),
                                  createdContact.getLastName());
  }

}
