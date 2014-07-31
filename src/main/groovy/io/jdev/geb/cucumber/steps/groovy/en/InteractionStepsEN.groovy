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

Then(~/^(eventually )?the (.*?)(?: field| label)? (?:matches |has (?:the )?value (?:of )?)(.*)$/) { String eventually, String fieldDesc, String value ->
    steps.hasValue(eventually as boolean, value, fieldDesc)
}

Then(~/^(eventually )?the (.*?)(?: field| label)? is present$/) { String eventually, String fieldDesc ->
    steps.isPresent(eventually as boolean, fieldDesc)
}

Then(~/^(eventually )?the (.*?)(?: field| label)? is not present$/) { String eventually, String fieldDesc ->
    steps.isNotPresent(eventually as boolean, fieldDesc)
}

Then(~/^(eventually )?the (.*?)(?: field| label)? is visible$/) { String eventually, String fieldDesc ->
    steps.isVisible(eventually as boolean, fieldDesc)
}

Then(~/^(eventually )?the (.*?)(?: field| label)? is not visible$/) { String eventually, String fieldDesc ->
    steps.isNotVisible(eventually as boolean, fieldDesc)
}

When(~/^I click (?:on )?the (.*?)(?: checkbox| box)?$/) { String fieldDesc ->
    steps.click(fieldDesc)
}

Then(~/^(eventually )?the (.*) (?:check)?box is checked$/) { String eventually, String fieldDesc->
    steps.isChecked(eventually as boolean, fieldDesc)
}

Then(~/^(eventually )?the (.*) (?:check)?box is not checked$/) { String eventually, String fieldDesc ->
    steps.isNotChecked(eventually as boolean, fieldDesc)
}

Then(~/^(eventually )?the page has the following values:$/) { String eventually, DataTable dataTable ->
    steps.hasValues(eventually as boolean, null, dataTable)
}

Then(~/^(eventually )?the (.*) section has the following values:$/) { String eventually, String fieldDesc, DataTable dataTable ->
    steps.hasValues(eventually as boolean, fieldDesc, dataTable)
}