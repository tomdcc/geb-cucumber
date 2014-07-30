package io.jdev.geb.cucumber.steps.groovy.en

import io.jdev.cucumber.variables.en.EnglishDecoder
import io.jdev.geb.cucumber.core.NavigationSteps
import io.jdev.geb.cucumber.core.en.PageFinderEN

import static cucumber.api.groovy.Hooks.Before
import static cucumber.api.groovy.Hooks.After
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.EN.Then

import cucumber.api.Scenario

NavigationSteps steps = new NavigationSteps()

Before { Scenario scenario ->
    steps.before(scenario, binding, PageFinderEN.instance, new EnglishDecoder())
}

After { Scenario scenario ->
    steps.after(scenario)
}

When(~/^I go to the (.* (?:page|dialog))$/) { String pageName ->
    steps.to(pageName)
}

When(~/^I go via the (.* (?:page|dialog))$/) { String pageName ->
    steps.via(pageName)
}

Then(~/^I am at the (.* (?:page|dialog))$/) { String pageName ->
    steps.at(pageName)
}

When(~/I pause for (\d+) seconds?/) { int seconds ->
    steps.pause(seconds)
}

When(~/^I go to '(.*)'$/) { String path ->
    steps.go(path)
}

Then(~/^I am at '(.*)'$/) { String path ->
    steps.atPath(path)
}

