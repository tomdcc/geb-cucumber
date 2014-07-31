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

    protected void before(Scenario scenario, Binding binding, Decoder variableDecoder) {
        variableScope = VariableScope.getCurrentScope(scenario, variableDecoder)
        if(!binding.hasVariable('browser')) {
            browser = new Browser()
            bindingUpdater = new BindingUpdater(binding, browser)
            bindingUpdater.initialize()
        } else {
            browser = binding.browser
        }
    }

    public void after(Scenario scenario) {
        VariableScope.removeCurrentScope()
        if(bindingUpdater) {
            bindingUpdater.remove()
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
