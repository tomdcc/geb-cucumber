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



package teststeps

import cucumber.api.Scenario
import io.jdev.cucumber.variables.core.BasicSteps
import io.jdev.cucumber.variables.en.EnglishDecoder
import io.jdev.geb.cucumber.core.en.FieldFinderEN
import io.jdev.geb.cucumber.core.util.ValueUtil

import java.util.regex.Pattern

import static cucumber.api.groovy.Hooks.Before
import static cucumber.api.groovy.Hooks.After
import static cucumber.api.groovy.EN.Then

def steps = new BasicSteps()
def fieldFinder = new FieldFinderEN()

Before() { Scenario scenario ->
    steps.before(scenario, new EnglishDecoder())
}

After() { Scenario scenario ->
    steps.after(scenario)
}

Then(~/^(the .*) variable has (?:the )?value (?:of )?(.*)$/) { String name, String rawValue ->
    Object expectedValue = steps.getVariable(rawValue)
    String actualValue = steps.getVariable(name)
    if(expectedValue instanceof Pattern) {
        assert ((Pattern) expectedValue).matcher(actualValue).matches()
    } else {
        assert expectedValue == actualValue
    }
}

Then(~/^\(test\) (the .*) variable (.*) nested field has (?:the )?value (?:of )?(.*)$/) { String name, String fieldDesc, String rawValue ->
    Object expectedValue = steps.getVariable(rawValue)
    def field = steps.getVariable(name)
    String actualValue = ValueUtil.getValue(fieldFinder.findField(fieldDesc, field))
    if(expectedValue instanceof Pattern) {
        assert ((Pattern) expectedValue).matcher(actualValue).matches()
    } else {
        assert expectedValue == actualValue
    }
}
