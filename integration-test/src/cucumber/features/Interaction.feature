Feature: Interaction features

Scenario: Enter values into basic fields
  When I go to the form page
  Then the name field has the value of 'Ada Lovelace'
  And the favourite language field has value ''
  And the use gnat checkbox is not checked
  When I enter 'foo' into the name field
  And I select 'Ada95' in the favourite language field
  And I click the use gnat checkbox
  Then the name field has value 'foo'
  And the favourite language field has value 'Ada95'
  And the use gnat checkbox is checked


