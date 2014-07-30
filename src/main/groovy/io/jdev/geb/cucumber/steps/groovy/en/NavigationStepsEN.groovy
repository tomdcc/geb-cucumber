package io.jdev.geb.cucumber.steps.groovy.en

import cucumber.api.DataTable
import io.jdev.geb.cucumber.core.NavigationSteps
import io.jdev.geb.cucumber.core.en.PageFinderEN
import io.jdev.geb.cucumber.core.en.WebEnglishDecoder

import static cucumber.api.groovy.Hooks.Before
import static cucumber.api.groovy.Hooks.After
import static cucumber.api.groovy.EN.When
import static cucumber.api.groovy.EN.Then

import cucumber.api.Scenario

NavigationSteps steps = new NavigationSteps()

Before { Scenario scenario ->
    steps.before(scenario, binding, PageFinderEN.instance, new WebEnglishDecoder())
}

After { Scenario scenario ->
    steps.after(scenario)
}

When(~/^I go to the (.* (?:page|dialog))$/) { String pageName ->
    steps.to(pageName)
}

When(~/^I go to the (.* (?:page|dialog)) with parameters:$/) { String pageName, DataTable dataTable ->
    steps.to(pageName, dataTable)
}

When(~/^I go to the (.* (?:page|dialog)) for (the .*)$/) { String pageName, String paramName ->
    println "steps.to($pageName, $paramName - 'the ', $paramName)"
    steps.to(pageName, paramName - 'the ', paramName)
}

When(~/^I go to the (.* (?:page|dialog)) for (.*) of (.*)$/) { String pageName, String paramName, String paramValue ->
    steps.to(pageName, paramName, paramValue)
}

When(~/^I go via the (.* (?:page|dialog))$/) { String pageName ->
    steps.via(pageName)
}

When(~/^I go via the (.* (?:page|dialog)) with parameters:$/) { String pageName, DataTable dataTable ->
    steps.via(pageName, dataTable)
}

When(~/^I go via the (.* (?:page|dialog)) for (.*) of (.*)$/) { String pageName, String paramName, String paramValue ->
    steps.via(pageName, paramName, paramValue)
}

When(~/^I go via the (.* (?:page|dialog)) for (the .*)$/) { String pageName, String paramName ->
    steps.to(pageName, paramName - 'the ', paramName)
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

When(~/^I go to '(.*)' with parameters:$/) { String path, DataTable table ->
    steps.go(path, table)
}

Then(~/^I am at '(.*)'$/) { String path ->
    steps.atPath(path)
}

