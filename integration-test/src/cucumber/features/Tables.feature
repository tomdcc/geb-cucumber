Feature: Tables

Scenario: Assert table values
  Given the email address is set to 'leah.carter@hotmail.com.bv'
  When I go to the table page
  Then the users table has 3 rows
  And the users table has the following values:
    | id   | name             | username     | email address     |
    | '43' | 'Leah Carter'    | 'leahcarter' | the email address |
    | '65' | 'Ethan Bell'     | 'ethanb'     | 'ebell@sa.org.bv' |
    | '16' | 'Harriet Miller' | 'harriet68'  | /hmiller@.*/      |
  And the users table has rows matching the following values:
    | name          | username     | email address     |
    | 'Ethan Bell'  | 'ethanb'     | 'ebell@sa.org.bv' |
    | 'Leah Carter' | 'leahcarter' | the email address |
  And the users table has no rows matching the following values:
    | name  |
    | 'Foo' |
    | 'Bar' |

Scenario: Assert table values
  When I go to the table page with parameters:
    | rows |
    | '0'  |
  Then the users table has no rows
  And the users table has 0 rows

Scenario: Table row storage and matching
  Given the email address is set to 'leah.carter@hotmail.com.bv'
  When I go to the table page
  Then the users table has rows matching the following values:
    | name          | username     | email address     |
    | 'Ethan Bell'  | 'ethanb'     | 'ebell@sa.org.bv' |
    | 'Leah Carter' | 'leahcarter' | the email address |
  And (test) the 1st users matching row variable id nested field has value '65'
  And (test) the 2nd users matching row variable id nested field has value '43'
  Then the users table has rows matching the following values:
    | name             | username    | email address |
    | 'Harriet Miller' | 'harriet68' | /hmiller@.*/  |
  And (test) the users matching row variable id nested field has value '16'
