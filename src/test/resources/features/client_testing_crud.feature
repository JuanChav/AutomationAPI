@active
Feature: Client testing CRUD

  Scenario: Change the phone number of the first Client named Laura
    Given there is at least 10 registered clients
    When find the first client named Laura
    And save her current phone number
    And update her phone number
    And validate her new phone number is different
    Then delete all the registered clients

  Scenario: Update and delete a New Client
    When create a new client
    And find the new client
    And update any parameter of the new client
    And delete the new client