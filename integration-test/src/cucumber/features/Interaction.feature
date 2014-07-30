Feature: Interaction features

Scenario: Enter values into basic fields
  When I go to the form page
  Then the name field has the value of 'Ada Lovelace'
  When I enter 'foo' into the name field
  Then the name field has value 'foo'


