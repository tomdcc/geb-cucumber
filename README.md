geb-cucumber [![Build Status](https://travis-ci.org/tomdcc/geb-cucumber.svg?branch=master)](https://travis-ci.org/tomdcc/geb-cucumber)
============

A library of cucumber-groovy step definitions to manipulate the browser
using Geb and your Geb page objects.

Example:

```cucumber
Scenario: Log in and smell the roses                           # equivalent geb groovy commands below
Given a customer                                               # insert into db and save some some variables
When I go to the login page                                    # to LoginPage
And I enter the following values:                              # username.value(<the customer username>)
  | username              | password        | remember me |    # password.value('some password')
  | the customer username | 'some password' | checked     |    # rememberMe.click()
And I click the login button                                   # loginButton.click()
Then eventually I am at the customer portal page               # waitFor { at(CustomerPortalPage) }
And the orders table has a row matching the following values:  # (CustomerPortalPage needs to have an orders property
  | id    | description          | final price |               #  that is a list of Geb modules with id, description 
  | /\d+/ | 'Collins Dictionary' | '$50.05'    |               #  and finalPrice properties as content).
When I click the orders table matching row view details button # (previously matched row).viewDetailsButton.click()
# etc
```

Have a look at the [integration tests](https://github.com/tomdcc/geb-cucumber/tree/master/integration-test/src/cucumber/features)
to see what it's capable of.

Installation
------------

Geb Cucumber is deployed to Maven Central and can be added to your project as a dependency using the following coordinates:

    groupId: io.jdev.geb
    artifactId: geb-cucumber
    version: 0.3

Or just download the jar from http://search.maven.org/ if your build system is a bit less connected.

Once installed, you should add `classpath:io.jdev.geb.cucumber.steps.groovy.en`
to your cucumber glue directory list. See the project's
[integration test build file](https://github.com/tomdcc/geb-cucumber/tree/master/integration-test/build.gradle)
for an example.

Usage
-----

### Pages and page navigation

Geb Cucumber attempts to convert names of pages into page class names by
converting lower case names with spaces into proper case, e.g. for the page
name `customer feedback page`, it will look for a class named
`CustomerFeedbackPage`. Page names can end with `page` or `dialog`.

The library will look for pages in a given set of packages. Currently the way
to set the list is by setting the `geb.cucumber.step.packages` system
property.

### Fields and interactions

Geb Cucumber will look up field names based on the same lower-case to
camel-case conversion, e.g. `login button` becomes `loginButton`. It then
looks up the matching content in the Geb page object.

### Modules

It is possible to access fields inside Geb modules on your page. This is done
by appending `<section name> section` to the field definition, so e.g. 
`billing address section state` is mapped to the `state` property in the
module returned by the page's `billingAddress` content.

It is also possible to enter multiple values into a module and verify multiple
values inside a module at once using cucumber table syntax, for more readable
tests.

### Tables

It is possible to verify that a table, implemented in the page model as a list
of Geb modules:
 - Has rows matching values in a cucumber table
 - Has only rows matching values in a cucumber table
 - Has a given number of rows

### Waiting for conditions

If you append `eventually` in front of your verification steps, the library
will wrap the verification in a Geb `waitFor` block.

Changelog
---------

#### Version 0.3
 - Enter values into a module or the page and verify values in a module from a
   variable which is a map
 - Fix assertions to give more meaningful, cleaner errors

#### Version 0.2
 - Make select box value retrieval work in Geb 0.9.3
 - Miscellaneous fixes to allow easier integration of code with application-specific steps

#### Version 0.1
 - Initial version
 - Basic page navigation to urls, uris and page objects with parameters
 - Enter text, select options, click checkboxes and buttons
 - Enter field values in a table and have them set on the corresponding module
 - Handle windows, alerts, confirmation dialogs and prompts popping up
 - Wait for assertions to become true on ajax pages by appending "eventually"
   to the cucumber step
 - Verify table rows, save matching rows for later use
