Feature: Get trainers with registered training sessions
  Scenario: Fail to save a new Trainer due to invalid details
    Given I have invalid trainer details
    When I try to save the new trainer
    Then an error is thrown due to invalid trainer datalist
  Scenario: Update an existing trainer
    Given An existing trainer with a unique username
    And I have new valid details for this trainer
    When I update the trainer
    Then the trainer details are updated successfully
  Scenario: Find a trainer by their username
    Given A trainer with specific username exists
    When I search for a trainer by username
    Then the trainer details are returned correctly