package com.esgroup.geecon2020.spock.model;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class ContactCreateDTO {

  @Email
  String email;
  @NotBlank
  @Length(min = 3)
  String firstName;
  @NotBlank
  @Length(min = 3)
  String lastName;

}
