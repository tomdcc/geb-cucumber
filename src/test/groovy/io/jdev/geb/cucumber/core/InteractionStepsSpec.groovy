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



package io.jdev.geb.cucumber.core

import cucumber.api.Scenario
import geb.Browser
import io.jdev.cucumber.variables.VariableScope
import io.jdev.cucumber.variables.core.Decoder
import org.openqa.selenium.WebDriver
import spock.lang.Specification

class InteractionStepsSpec extends Specification {

    static final String BASE_URL = 'http://foo.com/bar/'
    static final String CURRENT_URL = 'http://foo.com/bar/baz/foo.jsp'
    static final String CURRENT_URI = 'baz/foo.jsp'

    InteractionSteps steps

    void setup() {
        steps = new InteractionSteps()
    }

    void cleanup() {
        VariableScope.removeCurrentScope()
    }

    void "registers and cleans up steps in binding"() {
        given: 'binding and scenario'
            Binding binding = new Binding()
            Scenario scenario = Mock(Scenario)

        when: 'call before method'
            steps.before(scenario, binding, Mock(FieldFinder), Mock(Decoder))

        then: 'registered itself in binding'
            binding.interactionSteps == steps

        when: 'call after method'
            steps.after(scenario)

        then: 'no longer there'
            !binding.hasVariable('interactionSteps')
    }
}
