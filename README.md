geb-cucumber [![Build Status](https://travis-ci.org/tomdcc/geb-cucumber.svg?branch=master)](https://travis-ci.org/tomdcc/geb-cucumber)
============

A library of cucumber-groovy step definitions to manipulate the browser
using Geb and your Geb page objects.

Example:

```cucumber
Scenario: Log in and smell the roses                          # equivalent geb groovy commands below
Given a customer                                              # insert into db and save some some variables
When I go to the login page                                   # go LoginPage
And I enter the following details:                            # username.value(<the customer username>)
  | username              | password         | remember me |  # password.value('some password')
  | the customer username | 'some password ' | checked     |  # rememberMe.click()
And I click the login button                                  # loginButton
Then eventually I am at the customer portal page              # waitFor { at(CustomerPortalPage) }
And the orders table a row matching the following values:     # (CustomerPortalPage needs to have an orders property
  | id    | description          | final price |              #  that is a list of Geb modules with id, description 
  | /\d+/ | 'Collins Dictionary' | '$50.05'    |              #  and finalPrice properties as content).
When I click on the view details button on the matching row   # (previously matched row).viewDetailsButton.click()
# etc
```

Have a look at the [integration tests](https://github.com/tomdcc/geb-cucumber/tree/master/integration-test/src/cucumber/features)
to see what it's capable of.


### Changelog

#### Version 0.1
 - Initial version
 - Basic page navigation to urls, uris and page objects with parameters
 - Enter text, select options, click checkboxes and buttons
 - Enter field values in a table and have them set on the corresponding module
 - Handle windows, alerts, confirmation dialogs and prompts popping up
 - Wait for assertions to become true on ajax pages by appending "eventually"
   to the cucumber step
 - Verify table rows, save matching rows for later use