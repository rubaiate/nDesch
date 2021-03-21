Feature: Get All Defs

  Scenario: Get empty array

    Given url 'http://localhost:8080/scheduleDefs/'
    When method GET
    Then status 200