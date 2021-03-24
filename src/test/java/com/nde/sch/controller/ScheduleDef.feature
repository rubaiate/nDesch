Feature: Get All Defs

  Scenario: Get schedule def details

    Given url 'http://localhost:8080/scheduleDefs/'
    When method GET
    Then status 200
    And assert response.length == 2
    And assert response[0].id == 'sch1'
    And assert response[1].id == 'cd-sch'
    And def expected =
    """
    {
      "jobName":"job1",
      "dependentName":null,
      "scheduleExpression":"* * * * * *",
      "action":"RUN",
      "timeZone":"Asia/Singapore",
      "id":"sch1",
      "triggerType":"TIMED",
      "enabled":false
    }
    """
    And match response[0] contains only expected