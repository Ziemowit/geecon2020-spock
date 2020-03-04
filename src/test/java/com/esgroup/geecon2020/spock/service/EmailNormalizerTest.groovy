package com.esgroup.geecon2020.spock.service

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

/**
 * Covering:
 *
 * 1. Given/When/Then/Where
 * 2. @Unroll
 * 3. @Subject
 * 4. @Shared
 */
class EmailNormalizerTest extends Specification {

  @Shared
  @Subject
  def normalizer = new EmailNormalizer()

  def '''Emails normalization.
         Version 1a.
         Most condensed.'''() {
    expect:
    new EmailNormalizer().normalize(['foo1@bar.com', 'FOO1@bar.com', ' foo2@bar.com  ']) == ['foo1@bar.com', 'foo2@bar.com']
  }

  def '''Emails normalization.
         Version 1b.
         Extract Normalizer to class level.'''() {
    expect:
    normalizer.normalize(['foo1@bar.com', 'FOO1@bar.com', ' foo2@bar.com  ']) == ['foo1@bar.com', 'foo2@bar.com']
  }

  def '''Emails normalization.
         Version 2.
         With Given/When/Then labels.'''() {
    given:
    def input = ['foo1@bar.com', 'FOO1@bar.com', '', null, ' foo2@bar.com  ', 'invalid email']

    when:
    def result = normalizer.normalize(input)

    then:
    result == ['foo1@bar.com', 'foo2@bar.com']
  }

  def '''Emails normalization.
         Version 2.
         With Given/When/Then/Where labels.'''() {
    when:
    def result = normalizer.normalize(input)

    then:
    result == expectedResult

    where:
    input                                            || expectedResult
    null                                             || []                                               // null handling
    []                                               || []                                               // no data handling
    ['foo1@bar.com', 'foo2@bar.com', 'foo3@bar.com'] || ['foo1@bar.com', 'foo2@bar.com', 'foo3@bar.com'] // sunny case
    ['foo1@bar.com', 'FOO1@bar.com', 'FoO1@bAr.CoM'] || ['foo1@bar.com']                                 // duplications
    ['', null, '              ']                     || []                                               // empty elements
    ['foo  1@bar.com', 'foo1bar.com', 'foo1@bar']    || []                                               // invalid format of emails

  }

  def '''Emails normalization.
         Version 3a.
         Some test is failing. Issue with spotting failing test.'''() {
    when:
    def result = normalizer.normalize(input)

    then:
    result == expectedResult

    where:
    input                                            || expectedResult
    null                                             || []                                               // null handling
    []                                               || []                                               // no data handling
    ['foo1@bar.com', 'foo2@bar.com', 'foo4@bar.com'] || ['foo1@bar.com', 'foo2@bar.com', 'foo3@bar.com'] // sunny case
    ['foo1@bar.com', 'FOO1@bar.com', 'FoO1@bAr.CoM'] || ['foo1@bar.com']                                 // duplications
    ['', null, '              ']                     || []                                               // empty elements
    ['foo  1@bar.com', 'foo1bar.com', 'foo1@bar']    || []                                               // invalid format of emails
  }

  @Unroll
  def '''Emails normalization.
         Version 3b.
         Some test is failing. Solving issue with spotting failing test.'''() {
    when:
    def result = normalizer.normalize(input)

    then:
    result == expectedResult

    where:
    input                                            || expectedResult
    null                                             || []                                               // null handling
    []                                               || []                                               // no data handling
    ['foo1@bar.com', 'foo2@bar.com', 'foo4@bar.com'] || ['foo1@bar.com', 'foo2@bar.com', 'foo3@bar.com'] // sunny case
    ['foo1@bar.com', 'FOO1@bar.com', 'FoO1@bAr.CoM'] || ['foo1@bar.com']                                 // duplications
    ['', null, '              ']                     || []                                               // empty elements
    ['foo  1@bar.com', 'foo1bar.com', 'foo1@bar']    || []                                               // invalid format of emails
  }

  @Unroll
  def '''Emails normalization.
         Version 4.
         Make tests more verbose.'''(List<String> input, List<String> expectedResult) {
    when:
    def result = normalizer.normalize(input)

    then:
    result == expectedResult

    where:
    input                                            || expectedResult
    null                                             || []                                               // null handling
    []                                               || []                                               // no data handling
    ['foo1@bar.com', 'foo2@bar.com', 'foo3@bar.com'] || ['foo1@bar.com', 'foo2@bar.com', 'foo3@bar.com'] // sunny case
    ['foo1@bar.com', 'FOO1@bar.com', 'FoO1@bAr.CoM'] || ['foo1@bar.com']                                 // duplications
    ['', null, '              ']                     || []                                               // empty elements
    ['foo  1@bar.com', 'foo1bar.com', 'foo1@bar']    || []                                               // invalid format of emails
  }

}
