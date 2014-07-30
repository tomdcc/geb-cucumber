package io.jdev.geb.cucumber.steps.groovy.en

import io.jdev.cucumber.variables.en.EnglishDecoder
import io.jdev.geb.cucumber.core.InteractionSteps
import io.jdev.geb.cucumber.core.en.FieldFinderEN

import static cucumber.api.groovy.Hooks.Before
import static cucumber.api.groovy.Hooks.After
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.EN.Then

import cucumber.api.Scenario

InteractionSteps steps = new InteractionSteps()

Before { Scenario scenario ->
    steps.before(scenario, binding, new FieldFinderEN(), new EnglishDecoder())
}

After { Scenario scenario ->
    steps.after(scenario)
}

When(~/^I (?:enter|select) (.*) in(?:to)? the (.* field)$/) { String value, String fieldDesc ->
    steps.enter(value, fieldDesc)
}

Then(~/^the (.* field) has (?:the )?value (?:of )?(.*)$/) { String fieldDesc, String value ->
    steps.hasValue(value, fieldDesc)
}
