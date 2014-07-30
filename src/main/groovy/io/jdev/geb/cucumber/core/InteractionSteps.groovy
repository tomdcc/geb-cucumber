package io.jdev.geb.cucumber.core

import cucumber.api.Scenario
import io.jdev.cucumber.variables.core.Decoder

class InteractionSteps extends StepsBase {

    FieldFinder fieldFinder

    public void before (Scenario scenario, Binding binding, FieldFinder fieldFinder, Decoder variableDecoder) {
        super.before(scenario, binding, variableDecoder)
        this.fieldFinder = fieldFinder
    }

    void enter(String rawValue, String fieldDesc) {
        def value = variableScope.decodeVariable(rawValue)
        def field = fieldFinder.findField(fieldDesc, browser.page)
        field.value(value)
    }

    void hasValue(String rawValue, String fieldDesc) {
        def value = variableScope.decodeVariable(rawValue)
        def field = fieldFinder.findField(fieldDesc, browser.page)
        assert field.value() as String == value as String
    }

    void click(String fieldDesc) {
        def field = fieldFinder.findField(fieldDesc, browser.page)
        field.click()
    }

    void isChecked(String fieldDesc) {
        def field = fieldFinder.findField(fieldDesc, browser.page)
        assert field.value() != false
    }
    void isNotChecked(String fieldDesc) {
        def field = fieldFinder.findField(fieldDesc, browser.page)
        assert field.value() == false
    }
}
