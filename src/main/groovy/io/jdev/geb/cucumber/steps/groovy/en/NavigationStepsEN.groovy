/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 the original author or authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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

Then(~/^(eventually )?I am at the (.* (?:page|dialog))$/) { String eventually, String pageName ->
    steps.at(eventually as boolean, pageName)
}

When(~/^I pause for (\d+) seconds?$/) { int seconds ->
    steps.pause(seconds)
}

When(~/^I go to '(.*)'$/) { String path ->
    steps.go(path)
}

When(~/^I go to '(.*)' with parameters:$/) { String path, DataTable table ->
    steps.go(path, table)
}

Then(~/^(eventually )?I am at '(.*)'$/) { String eventually, String path ->
    steps.atPath(eventually as boolean, path)
}

Then(~/^(eventually )?another window has popped up$/) { String eventually ->
    // ignore eventually here as popping windows up always needs a bit of a waitFor
    steps.assertPopup()
}

When(~/^I switch to the popped up window$/) { ->
    steps.switchToPopup(null)
}

Then(~/^(eventually )?the (.* (?:page|dialog)) has popped up$/) { String eventually, String pageName ->
    // ignore eventually here as popping windows up always needs a bit of a waitFor
    steps.switchToPopup(pageName)
}

When(~/^I close the popped up window$/) { ->
    steps.closePopup()
}

Then(~/^(eventually )?the popped up window has closed$/) { String eventually ->
    // ignore eventually here as closing windows always needs a bit of a waitFor
    steps.popupClosed()
}

Then(~/^(eventually )?an alert box has popped up$/) { String eventually ->
    // ignore eventually here as alerts frequently need a bit of a waitFor
    // and we do it automatically
    steps.hasAlert(null)
}

Then(~/^(eventually )?an alert box with message (.*) has popped up$/) { String eventually, String message ->
    // ignore eventually here as alerts frequently need a bit of a waitFor
    // and we do it automatically
    steps.hasAlert(message)
}

When(~/^I accept the alert box$/) { ->
    steps.acceptAlert()
}

When(~/^I dismiss the alert box$/) { ->
    steps.dismissAlert()
}

When(~/^I type (.*) into the alert box$/) { String text ->
    steps.enterAlertPrompt(text)
}
