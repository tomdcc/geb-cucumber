package io.jdev.geb.cucumber.core

import cucumber.api.DataTable
import cucumber.api.Scenario
import geb.Module
import io.jdev.cucumber.variables.core.Decoder
import io.jdev.geb.cucumber.core.util.TableUtil
import io.jdev.geb.cucumber.core.util.ValueUtil

class InteractionSteps extends StepsBase {

    FieldFinder fieldFinder

    public void before (Scenario scenario, Binding binding, FieldFinder fieldFinder, Decoder variableDecoder) {
        super.before(scenario, binding, variableDecoder)
        this.fieldFinder = fieldFinder
    }

    void enter(String rawValue, String fieldDesc) {
        def value = variableScope.decodeVariable(rawValue)
        def field = fieldFinder.findField(fieldDesc, browser.page)
        enterValue(field, value)
    }

    void enterValues(String fieldDesc, DataTable dataTable) {
        def target = getOptionalTarget(fieldDesc)
        List<Map<String,Object>> vals = TableUtil.dataTableToMaps(variableScope, dataTable, false)
        assert vals.size() == 1, "Cannot enter more than 1 row when entering multiple values"
        enterValues(target, vals.first())
    }

    private def getOptionalTarget(String fieldDesc) {
        def target
        if(fieldDesc) {
            target = fieldFinder.findField(fieldDesc, browser.page)
            assert target instanceof Module, "$fieldDesc is not a module, but you cannot enter multiple values into non-modules"
        } else {
            target = browser.page
        }
        target
    }

    private void enterValues(def target, Map<String,Object> vals) {
        for(Map.Entry<String,Object> entry : vals) {
            String fieldName = entry.key
            def field = fieldFinder.findField(fieldName, target)
            enterValue(field, entry.value)
        }
    }

    private void enterValue(def field, def value) {
        if(value instanceof CheckedDecoder.CheckedState) {
            boolean isChecked = field.value() != false
            boolean wantChecked = value == CheckedDecoder.CheckedState.checked
            if(isChecked != wantChecked) {
                field.click()
            }
        } else {
            field.value(value)
        }
    }

    void hasValue(String rawValue, String fieldDesc) {
        def value = variableScope.decodeVariable(rawValue)
        def field = fieldFinder.findField(fieldDesc, browser.page)
        hasValue(field, value)
    }

    private static void hasValue(def field, def expectedValue) {
        if(expectedValue instanceof CheckedDecoder.CheckedState) {
            boolean isChecked = field.value() != false
            boolean wantChecked = expectedValue == CheckedDecoder.CheckedState.checked
            assert isChecked == wantChecked
        } else {
            String fieldValue = ValueUtil.getValue(field)
            assert fieldValue == expectedValue as String
        }
    }

    void hasValues(String fieldDesc, DataTable dataTable) {
        def target = getOptionalTarget(fieldDesc)
        List<Map<String,Object>> vals = TableUtil.dataTableToMaps(variableScope, dataTable, false)
        assert vals.size() == 1, "Cannot enter more than 1 row when verifying multiple values"
        hasValues(target, vals.first())
    }

    private void hasValues(def target, Map<String,Object> vals) {
        for(Map.Entry<String,Object> entry : vals) {
            String fieldName = entry.key
            def field = fieldFinder.findField(fieldName, target)
            hasValue(field, entry.value)
        }
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
