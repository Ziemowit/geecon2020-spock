package com.esgroup.geecon2020.spock.repository;

import com.esgroup.geecon2020.spock.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContactRepository extends MongoRepository<Contact, String> {

  Optional<Contact> findByEmail(String email);

}
