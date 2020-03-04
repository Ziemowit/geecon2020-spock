package com.esgroup.geecon2020.spock.service

import com.esgroup.geecon2020.spock.model.Contact
import com.esgroup.geecon2020.spock.model.ContactCreateDTO
import com.esgroup.geecon2020.spock.repository.ContactRepository
import spock.lang.Specification
import spock.lang.Subject

import static org.mockito.AdditionalAnswers.returnsFirstArg
import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

class ContactServiceWithMockitoTest extends Specification {

  def contactRepo = mock(ContactRepository)

  @Subject
  def contactService = new ContactService(contactRepo)

  def '''Create Contact with Mockito.
         Version 1.'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    given(contactRepo.findByEmail(request.email)).willReturn(Optional.empty())
    given(contactRepo.save(any(Contact))).will(returnsFirstArg())

    when:
    def created = contactService.createContact(request)

    then:
    created.email == 'foo@bar.com'
    created.firstName == 'Foo'
    created.lastName == 'Bar'
  }

  // Then block may only contain assertions in the form of boolean expressions.
  // A Mockito verification expression doesn't fit this contract,
  // as it will return a falsy value (null, false, 0) when it passes,
  // which is interpreted as a failed assertion by Spock.
  def '''Create Contact with Mockito.
         Version 2. Issue with Then and Mockito Verify'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    given(contactRepo.findByEmail(request.email)).willReturn(Optional.empty())
    given(contactRepo.save(any(Contact))).will(returnsFirstArg())

    when:
    def created = contactService.createContact(request)

    then:
    verify(contactRepo).findByEmail('foo@bar.com')
    verify(contactRepo).save(any(Contact))

    created.email == 'foo@bar.com'
    created.firstName == 'Foo'
    created.lastName == 'Bar'
  }

  def '''Create Contact with Mockito.
         Version 3a. Resolve issue with Then and Mockito Verify'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    given(contactRepo.findByEmail(request.email)).willReturn(Optional.empty())
    given(contactRepo.save(any(Contact))).will(returnsFirstArg())

    when:
    def created = contactService.createContact(request)

    then:
    verify(contactRepo).findByEmail('foo@bar.com') || true
    verify(contactRepo).save(any(Contact)) || true

    created.email == 'foo@bar.com'
    created.firstName == 'Foo'
    created.lastName == 'Bar'
  }

  def '''Create Contact with Mockito.
         Version 3b. Resolve issue with Then and Mockito Verify'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')

    given(contactRepo.findByEmail(request.email)).willReturn(Optional.empty())
    given(contactRepo.save(any(Contact))).will(returnsFirstArg())

    when:
    def created = contactService.createContact(request)

    then:
    verifyMocks()

    created.email == 'foo@bar.com'
    created.firstName == 'Foo'
    created.lastName == 'Bar'
  }

  private boolean verifyMocks() {
    verify(contactRepo).findByEmail('foo@bar.com')
    verify(contactRepo).save(any(Contact))
    return true
  }

}
