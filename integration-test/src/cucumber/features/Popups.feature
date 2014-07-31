Feature: Popups

Scenario: Popup windows can be closed without switching to them
  When I go to the home page
  And I click on the popup button
  Then another window has popped up
  When I close the popped up window
  Then I am at the home page

Scenario: Popup windows can be closed after switching to them
  When I go to the home page
  And I click on the popup button
  Then another window has popped up
  When I switch to the popped up window
  And I close the popped up window
  Then I am at the home page

Scenario: Popup windows can be closed by interaction
  When I go to the home page
  And I click on the popup button
  Then the second page has popped up
  When I click the close button
  Then the popped up window has closed
  And I am at the home page

Scenario: Alert box can be accepted
  When I go to the home page
  And I click the alert button
  Then an alert box with message 'Click OK' has popped up
  When I accept the alert box
  Then I am at the home page

Scenario: Alert box can be accepted without asserting the message
  When I go to the home page
  And I click the alert button
  Then an alert box has popped up
  When I accept the alert box
  Then I am at the home page

Scenario: Confirm box can be accepted
  When I go to the home page
  And I click the confirm button
  Then an alert box with message 'Click OK or Cancel' has popped up
  When I accept the alert box
  Then I am at the home page
  And the alert result has value 'true'

Scenario: Confirm box can be dismissed
  When I go to the home page
  And I click the confirm button
  Then an alert box with message 'Click OK or Cancel' has popped up
  When I dismiss the alert box
  Then I am at the home page
  And the alert result has value 'false'

Scenario: Prompt box can be accepted
  When I go to the home page
  And I click the prompt button
  Then an alert box with message 'Type something' has popped up
  When I type 'ok' into the alert box
  And I accept the alert box
  Then I am at the home page
  And the alert result has value 'ok'

Scenario: Prompt box can be dismissed
  When I go to the home page
  And I click the prompt button
  Then an alert box with message 'Type something' has popped up
  When I type 'ok' into the alert box
  And I dismiss the alert box
  Then I am at the home page
  And the alert result has value ''
