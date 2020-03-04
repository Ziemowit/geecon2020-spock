package com.esgroup.geecon2020.spock.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
@TypeAlias("contact")
@AllArgsConstructor
public class Contact {

  @Id
  private String id;
  private String email;
  private String firstName;
  private String lastName;

}
