package com.esgroup.geecon2020.spock.basic

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Covering:
 *
 * 1. Basic structure of Spock tests
 *
 */
class BasicsTests extends Specification {

  def '''The most basic test in Spock.'''() {
    expect:
    1 + 2 == 3
  }

  def '''The most basic test in Spock in more verbose way.'''() {
    given:                                      // remove given
    def a = 2
    def b = 1

    when:                                       // remove when
    def result = a + b
                                                // add assertion result != 3
    then:                                       // remove then
    result == 3
  }

  def '''The where clause.'''() {
    given:
    def a = 2
    def b = 1

    when:
    def result = (a + b - c) % d

    then:
    result == expectedResult

    where:
    c | d || expectedResult
    0 | 2 || 1
    1 | 2 || 0
  }

  @Unroll
  def '''The "(2 + 1 - #c) % #d" should be equal to #expectedResult'''() {
    given:
    def a = 2
    def b = 1

    when:
    def result = (a + b - c) % d

    then:
    result == expectedResult

    where:
    c | d || expectedResult
    0 | 2 || 1
    1 | 2 || 0
  }

}
