package com.esgroup.geecon2020.spock.web

import com.esgroup.geecon2020.spock.model.Contact
import com.esgroup.geecon2020.spock.model.ContactCreateDTO
import com.esgroup.geecon2020.spock.service.ContactService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo
import static org.mockito.BDDMockito.given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(controllers = ContactController)
class ContactControllerMockitoTest extends Specification {

  @MockBean
  ContactService contactService

  @Autowired
  MockMvc mvc
  @Autowired
  ObjectMapper mapper

  def '''Create'''() {
    given:
    def request = new ContactCreateDTO('foo@bar.com', 'Foo', 'Bar')
    def contact = new Contact('id', request.email, request.firstName, request.lastName)

    given(contactService.createContact(request)).willReturn(contact)

    when:
    def response = mvc.perform(post('/contacts')
                                   .content(mapper.writeValueAsString(request))
                                   .contentType(MediaType.APPLICATION_JSON_VALUE)
                                   .accept(MediaType.APPLICATION_JSON))

    then:
    response.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath('id', equalTo('id')))
            .andExpect(MockMvcResultMatchers.jsonPath('email', equalTo('foo@bar.com')))
            .andExpect(MockMvcResultMatchers.jsonPath('firstName', equalTo('Foo')))
            .andExpect(MockMvcResultMatchers.jsonPath('lastName', equalTo('Bar')))
  }

}
