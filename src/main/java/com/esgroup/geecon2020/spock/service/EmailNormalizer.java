package com.esgroup.geecon2020.spock.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EmailNormalizer {

  public EmailNormalizer() {
    log.info("EmailNormalizer created!");
  }

  public List<String> normalize(List<String> potentialEmails) {
    if(potentialEmails == null) {
      return List.of();
    }

    return potentialEmails.stream()
                          .filter(StringUtils::isNotBlank)
                          .map(StringUtils::trim)
                          .filter(GenericValidator::isEmail)
                          .map(StringUtils::lowerCase)
                          .distinct()
                          .collect(Collectors.toList());
  }

}
