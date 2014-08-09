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

Then(~/^(eventually )?the (.*) table has (\d+) rows?$/) { String eventually, String fieldDesc, int rows ->
    steps.hasRows(eventually as boolean, fieldDesc, rows)
}

Then(~/^(eventually )?the (.*) table has no rows?$/) { String eventually, String fieldDesc ->
    steps.hasRows(eventually as boolean, fieldDesc, 0)
}

Then(~/^(eventually )?the (.*) table has the following values:$/) { String eventually, String fieldDesc, DataTable dataTable ->
    steps.hasRowValues(eventually as boolean, fieldDesc, dataTable)
}

Then(~/^(eventually )?the (.*) table has (?:a )?rows? matching the following values:$/) { String eventually, String fieldDesc, DataTable dataTable ->
    steps.hasRowsMatching(eventually as boolean, fieldDesc, dataTable, "$fieldDesc matching row")
}

Then(~/^(eventually )?the (.*) table has no rows matching the following values:$/) { String eventually, String fieldDesc, DataTable dataTable ->
    steps.hasNoRowsMatching(eventually as boolean, fieldDesc, dataTable)
}
