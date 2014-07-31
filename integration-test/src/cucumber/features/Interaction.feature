Feature: Interaction features

Scenario: Enter values into basic fields
  When I go to the form page
  Then the name field has the value of "Ada Lovelace"
  And the favourite language field has value ''
  And the use gnat checkbox is not checked
  When I enter 'foo' into the name field
  And I select 'Ada95' in the favourite language field
  And I click the use gnat checkbox
  Then the name field has value 'foo'
  And the favourite language field has value 'Ada95'
  And the use gnat checkbox is checked

Scenario: Enter values into direct page fields using table syntax
  When I go to the form page
  When I enter the following values:
    | name           | favourite language | use gnat |
    | 'Ada Lovelace' | 'Ada95'            | checked  |
  Then the name field has value 'Ada Lovelace'
  And the favourite language field has value 'Ada95'
  And the use gnat checkbox is checked
  When I enter the following values:
    | name           | favourite language | use gnat  |
    | 'Donald Knuth' | 'Tex'              | unchecked |
  Then the page has the following values:
    | name           | favourite language | use gnat  |
    | 'Donald Knuth' | 'Tex'              | unchecked |

Scenario: Enter values into module fields using table syntax
  When I go to the form page
  When I enter the following values into the address section:
    | street address                 | city      | state | postcode | registered mail only |
    | 'Norwood Oval\n4 Woods Street' | 'Norwood' | 'SA'  | '5067'   | unchecked            |
  Then the address section street address field has value 'Norwood Oval\n4 Woods Street'
  Then the address section city field has value 'Norwood'
  Then the address section state field has value 'SA'
  Then the address section postcode field matches /\d{4}/
  Then the address section registered mail only box is not checked
  When I enter the following values into the address section:
    | street address     | city        | state | postcode | registered mail only |
    | '39 George Street' | 'Thebarton' | 'SA'  | '5031'   | checked              |
  Then the address section has the following values:
    | street address     | city        | state | postcode | registered mail only |
    | '39 George Street' | 'Thebarton' | 'SA'  | /\d{4}/   | checked              |

Scenario: Verify generic content
  When I go to the home page with parameters:
    | greeting   |
    | 'hi there' |
  Then the greeting is present
  And the greeting is visible
  And the greeting has value 'hi there'
  And the greeting field has value 'hi there'
  And the greeting string label has value 'hi there'

Scenario: Not present content
  When I go to the home page
  Then the greeting is not present

Scenario: Hidden content
  When I go to the home page with parameters:
    | greeting   | greetingHidden |
    | 'hi there' | 'true'         |
  Then the greeting is present
  And the greeting is not visible
