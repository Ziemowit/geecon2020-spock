package com.esgroup.geecon2020.spock.service

import com.esgroup.geecon2020.spock.model.Contact
import com.esgroup.geecon2020.spock.model.ContactCreateDTO
import com.esgroup.geecon2020.spock.repository.ContactRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Subject

import static org.assertj.core.api.Assertions.assertThat

@SpringBootTest
class ContactServiceIntegrationTest extends Specification {

  @Autowired
  ContactRepository contactRepo

  @Subject
  @Autowired
  ContactService contactService

  void cleanup() {
    contactRepo.deleteAll()
  }

  def '''Create Contact - Integration Test'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when:
    def result = contactService.createContact(request)

    then:
    assertThat(result).isEqualToIgnoringGivenFields(new Contact(null, 'foo@bar.com', 'Foo', 'Bar'), 'id')
    assertThat(result.id).isNotBlank()
  }

  def '''Create Contact - Integration Test
         Multi-Flow case'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when: 'Creation of Contact'
    def result = contactService.createContact(request)

    then:
    assertThat(result).isEqualToIgnoringGivenFields(new Contact(null, 'foo@bar.com', 'Foo', 'Bar'), 'id')
    assertThat(result.id).isNotBlank()

    when: 'Deletion of Contact'
    contactService.deleteContact(result.id)

    then:
    contactRepo.count() == 0
  }

}
