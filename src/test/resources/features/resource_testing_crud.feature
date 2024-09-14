@active
Feature: Resource testing CRUD

  Scenario: Get the list of active resource
    Given  have at least 5 active resources
    When  find all resources active
    Then update them as inactive
