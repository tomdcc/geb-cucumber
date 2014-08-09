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
import geb.binding.BindingUpdater
import io.jdev.cucumber.variables.VariableScope
import io.jdev.cucumber.variables.core.Decoder

abstract class StepsBase {
    protected VariableScope variableScope
    protected BindingUpdater bindingUpdater
    protected Browser browser
    protected Binding binding
    protected String bindingName

    protected void before(Scenario scenario, Binding binding, Decoder variableDecoder, String bindingName) {
        variableScope = VariableScope.getCurrentScope(scenario, variableDecoder)
        if (!binding.hasVariable('browser')) {
            browser = new Browser()
            bindingUpdater = new BindingUpdater(binding, browser)
            bindingUpdater.initialize()
        } else {
            browser = binding.browser
        }
        if (bindingName) {
            this.binding = binding
            this.bindingName = bindingName
            binding.setVariable(bindingName, this)
        }
    }

    public void after(Scenario scenario) {
        VariableScope.removeCurrentScope()
        if(bindingUpdater) {
            bindingUpdater.remove()
        }
        if(binding && binding.hasVariable(bindingName) && binding.getVariable(bindingName) == this) {
            binding.variables.remove(bindingName)
        }
    }

    public <T> T runWithOptionalWait(boolean wait, Closure<T> closure) {
        if(wait) {
            browser.waitFor(closure)
        } else {
            closure.call()
        }
    }
}
