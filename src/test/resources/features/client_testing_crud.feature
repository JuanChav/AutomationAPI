@active
Feature: Client testing CRUD

  Scenario: Change the phone number of the first Client named Laura
    Given there is at least 10 registered clients
    When I find the first client named Laura
    And save her current phone number
    And update her phone number
    And validate her new phone number is different
    Then I delete all the registered clients
    #And the response should have a status code of 200


  Scenario: Update and delete a New Client
    Given I have a client with the following details:
      | Name | LastName | Gender | Country | City     |
      | John | Doe      | Male   | USA     | New York |
    When I send a POST request to create a client
    Then the response should have a status code of 201
    And the response should include the details of the created client
    And validates the response with client JSON schema
