package com.esgroup.geecon2020.spock.repository

import com.esgroup.geecon2020.spock.model.Contact
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import spock.lang.Specification
import spock.lang.Subject

@DataMongoTest
class ContactRepositoryTest extends Specification {

  @Subject
  @Autowired
  ContactRepository repo

  void cleanup() {
    repo.deleteAll()
  }

  def '''FindByEmail - should find nothing'''() {
    given:
    def email = 'foo@bar.com'

    when:
    def result = repo.findByEmail(email)

    then:
    result.isEmpty()
  }

  def '''FindByEmail - should find the Contact'''() {
    given:
    def email = 'foo@bar.com'
    def contact = repo.save(new Contact(null, email, 'Foo', 'Bar'))

    when:
    def result = repo.findByEmail(email)

    then:
    result.isPresent()
    result.get().id == contact.id
  }

}
