package com.esgroup.geecon2020.spock.service

import com.esgroup.geecon2020.spock.model.Contact
import com.esgroup.geecon2020.spock.model.ContactCreateDTO
import com.esgroup.geecon2020.spock.model.ContactExistsException
import com.esgroup.geecon2020.spock.repository.ContactRepository
import spock.lang.Specification
import spock.lang.Subject

import static org.assertj.core.api.Assertions.assertThat

class ContactServiceTest extends Specification {

  def contactRepo = Mock(ContactRepository)

  @Subject
  def contactService = new ContactService(contactRepo)

  def '''Create Contact.
         Version 1.'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when:
    def created = contactService.createContact(request)

    then:
    contactRepo.findByEmail(request.email) >> Optional.empty()
    contactRepo.save(_ as Contact) >> { Contact it -> it }

    created.email == 'foo@bar.com'
    created.firstName == 'Foo'
    created.lastName == 'Bar'
  }

  def '''Create Contact.
         Version 2. With clause'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when:
    def created = contactService.createContact(request)

    then:
    contactRepo.findByEmail(request.email) >> Optional.empty()
    contactRepo.save(_ as Contact) >> { Contact it -> it }

    with(created) {
      email == 'foo@bar.com'
      firstName == 'Foo'
      lastName == 'Bar'
    }
  }

  def '''Create Contact.
         Version 2a. Check number of mocks interactions.'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when:
    def created = contactService.createContact(request)

    then:
    1 * contactRepo.findByEmail(request.email) >> Optional.empty()
    1 * contactRepo.save(_ as Contact) >> { Contact it -> it }
    0 * _

    with(created) {
      email == 'foo@bar.com'
      firstName == 'Foo'
      lastName == 'Bar'
    }
  }

  def '''Create Contact.
         Version 2b. Execute some action on Mock.'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when:
    def created = contactService.createContact(request)

    then:
    1 * contactRepo.findByEmail(request.email) >> Optional.empty()
    1 * contactRepo.save(_ as Contact) >> { Contact it -> it.id = 'mock-id'; return it }
    0 * _

    with(created) {
      id == 'mock-id'
      email == 'foo@bar.com'
      firstName == 'Foo'
      lastName == 'Bar'
    }
  }

  def '''Create Contact.
         Version 2c. Order of Mock is important.'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when:
    def created = contactService.createContact(request)

    then:
    1 * contactRepo.findByEmail(request.email) >> Optional.empty()    // change the order of then - it will fail

    then:
    1 * contactRepo.save(_ as Contact) >> { Contact it -> it.id = 'mock-id'; return it }
    0 * _

    with(created) {
      id == 'mock-id'
      email == 'foo@bar.com'
      firstName == 'Foo'
      lastName == 'Bar'
    }
  }

  def '''Create Contact.
         Version 3. With AssertJ.'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    when:
    def created = contactService.createContact(request)

    then:
    1 * contactRepo.findByEmail(request.email) >> Optional.empty()
    1 * contactRepo.save(_ as Contact) >> { Contact it -> it.id = 'mock-id'; return it }
    0 * _

    assertThat(created).isEqualToComparingFieldByField(new Contact('mock-id', 'foo@bar.com', 'Foo', 'Bar'))
  }

  def '''Create Contact.
         Version 4. should fail as other Contact with same email already exists'''() {
    given:
    def email = 'foo@bar.com'
    def request = new ContactCreateDTO(email, 'Foo', 'Bar')

    when:
    contactService.createContact(request)

    then:
    1 * contactRepo.findByEmail(request.email) >> Optional.of(new Contact('mock-id', email, 'Foo', 'Bar'))
    0 * _

    def exception = thrown(ContactExistsException)

    exception.message == "Contact with given email already exists: ${email}"
  }

}
