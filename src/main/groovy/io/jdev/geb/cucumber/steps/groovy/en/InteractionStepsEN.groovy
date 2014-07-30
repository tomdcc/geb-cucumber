package io.jdev.geb.cucumber.steps.groovy.en

import cucumber.api.DataTable
import io.jdev.geb.cucumber.core.InteractionSteps
import io.jdev.geb.cucumber.core.en.FieldFinderEN
import io.jdev.geb.cucumber.core.en.WebEnglishDecoder

import static cucumber.api.groovy.Hooks.Before
import static cucumber.api.groovy.Hooks.After
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.EN.Then

import cucumber.api.Scenario

InteractionSteps steps = new InteractionSteps()

Before { Scenario scenario ->
    steps.before(scenario, binding, new FieldFinderEN(), new WebEnglishDecoder())
}

After { Scenario scenario ->
    steps.after(scenario)
}

When(~/^I (?:enter|select) (.*) in(?:to)? the (.*) field$/) { String value, String fieldDesc ->
    steps.enter(value, fieldDesc)
}

When(~/^I enter the following values:$/) { DataTable dataTable ->
    steps.enterValues(null, dataTable)
}

When(~/^I enter the following values in(?:to)? the (.*) section:$/) { String fieldDesc, DataTable dataTable ->
    steps.enterValues(fieldDesc, dataTable)
}

Then(~/^the (.*?)(?: field| label)? has (?:the )?value (?:of )?(.*)$/) { String fieldDesc, String value ->
    steps.hasValue(value, fieldDesc)
}

Then(~/^the (.*?)(?: field| label)? is present$/) { String fieldDesc ->
    steps.isPresent(fieldDesc)
}

Then(~/^the (.*?)(?: field| label)? is not present$/) { String fieldDesc ->
    steps.isNotPresent(fieldDesc)
}

Then(~/^the (.*?)(?: field| label)? is visible/) { String fieldDesc ->
    steps.isVisible(fieldDesc)
}

Then(~/^the (.*?)(?: field| label)? is not visible/) { String fieldDesc ->
    steps.isNotVisible(fieldDesc)
}

When(~/^I click (?:on )?the (.*?)(?: checkbox| box)?$/) { String fieldDesc ->
    steps.click(fieldDesc)
}

Then(~/^the (.*) (?:check)?box is checked$/) { String fieldDesc->
    steps.isChecked(fieldDesc)
}

Then(~/^the (.*) (?:check)?box is not checked$/) { String fieldDesc ->
    steps.isNotChecked(fieldDesc)
}

Then(~/the page has the following values:/) { DataTable dataTable ->
    steps.hasValues(null, dataTable)
}

Then(~/the (.*) section has the following values:/) { String fieldDesc, DataTable dataTable ->
    steps.hasValues(fieldDesc, dataTable)
}