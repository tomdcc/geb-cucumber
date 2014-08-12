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

import cucumber.api.DataTable
import cucumber.api.Scenario
import geb.Module
import io.jdev.cucumber.variables.core.Decoder
import io.jdev.geb.cucumber.core.util.TableUtil
import io.jdev.geb.cucumber.core.util.ValueUtil

class InteractionSteps extends StepsBase {

    FieldFinder fieldFinder

    private static final String BINDING_VAR_NAME = 'interactionSteps'

    public void before (Scenario scenario, Binding binding, FieldFinder fieldFinder, Decoder variableDecoder) {
        super.before(scenario, binding, variableDecoder, BINDING_VAR_NAME)
        this.fieldFinder = fieldFinder
    }

    void enter(String rawValue, String fieldDesc) {
        def value = variableScope.decodeVariable(rawValue)
        def field = fieldFinder.findField(fieldDesc, browser.page)
        ValueUtil.enterValue(field, value)
    }

    void enterValues(String fieldDesc, DataTable dataTable) {
        def target = getOptionalTarget(fieldDesc)
        List<Map<String,Object>> vals = TableUtil.dataTableToMaps(variableScope, dataTable, false)
        assert vals.size() == 1, "Cannot enter more than 1 row when entering multiple values"
        enterTargetValues(target, vals.first())
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

    private void enterTargetValues(def target, Map<String,Object> vals) {
        for(Map.Entry<String,Object> entry : vals) {
            String fieldName = entry.key
            def field = fieldFinder.findField(fieldName, target)
            ValueUtil.enterValue(field, entry.value)
        }
    }

    void hasValue(boolean wait, String rawValue, String fieldDesc) {
        def value = variableScope.decodeVariable(rawValue)
        runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            ValueUtil.hasValue(field, value)
        }
    }


    void hasValues(boolean wait, String fieldDesc, DataTable dataTable) {
        List<Map<String,Object>> vals = TableUtil.dataTableToMaps(variableScope, dataTable, false)
        assert vals.size() == 1, "Cannot enter more than 1 row when verifying multiple values"
        runWithOptionalWait(wait) {
            def target = getOptionalTarget(fieldDesc)
            targetHasValues(target, vals.first())
        }
    }

    private void targetHasValues(def target, Map<String,Object> vals) {
        for(Map.Entry<String,Object> entry : vals) {
            String fieldName = entry.key
            def field = fieldFinder.findField(fieldName, target)
            ValueUtil.hasValue(field, entry.value)
        }
    }

    void click(String fieldDesc) {
        def field = fieldFinder.findField(fieldDesc, browser.page)
        field.click()
    }

    void isChecked(boolean wait, String fieldDesc) {
        assert runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            field.value() != false
        }
    }

    void isNotChecked(boolean wait, String fieldDesc) {
        assert runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            field.value() == false
        }
    }

    void isPresent(boolean wait, String fieldDesc) {
        assert runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            field
        }
    }

    void isNotPresent(boolean wait, String fieldDesc) {
        assert runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            !field
        }
    }

    void isVisible(boolean wait, String fieldDesc) {
        assert runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            field.displayed
        }
    }

    void isNotVisible(boolean wait, String fieldDesc) {
        assert runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            !field.displayed
        }
    }

    void hasRows(boolean wait, String fieldDesc, int expectedNumRows) {
        runWithOptionalWait(wait) {
            def field = fieldFinder.findField(fieldDesc, browser.page)
            assert field.size() == expectedNumRows
            true
        }
    }

    void hasRowValues(boolean wait, String fieldDesc, DataTable dataTable) {
        List<Map<String,Object>> expectedRows = TableUtil.dataTableToMaps(variableScope, dataTable, false)
        runWithOptionalWait(wait) {
            def table = fieldFinder.findField(fieldDesc, browser.page)
            int expectedNumberOfRows = expectedRows.size()
            assert table.size() == expectedNumberOfRows
            for(int i = 0; i < expectedNumberOfRows; i++) {
                Map<String,Object> expectedRow = expectedRows[i]
                def row = table[i]
                targetHasValues(row, expectedRow)
            }
            true
        }
    }

    void hasRowsMatching(boolean wait, String fieldDesc, DataTable dataTable, String matchingRowVarName) {
        List<Map<String,Object>> expectedRows = TableUtil.dataTableToMaps(variableScope, dataTable, false)
        runWithOptionalWait(wait) {
            def table = fieldFinder.findField(fieldDesc, browser.page)
            for(Map<String,Object> expectedRow : expectedRows) {
                def row = hasRowMatching(table, expectedRow)
                assert row
                // store
                variableScope.storeVariable(matchingRowVarName, row)
            }
            true
        }
    }

    void hasNoRowsMatching(boolean wait, String fieldDesc, DataTable dataTable) {
        List<Map<String, Object>> expectedRows = TableUtil.dataTableToMaps(variableScope, dataTable, false)
        runWithOptionalWait(wait) {
            def table = fieldFinder.findField(fieldDesc, browser.page)
            for (Map<String, Object> expectedRow : expectedRows) {
                assert !hasRowMatching(table, expectedRow)
            }
            true
        }
    }

    private def hasRowMatching(def table, Map<String,Object> expectedRow) {
        for(def row : table) {
            try {
                targetHasValues(row, expectedRow)
                return row
            } catch (AssertionError e) {
                // wasn't found
            }
        }
        false
    }

    void enterFromVariable(String fieldDesc, String varName, DataTable dataTable) {
        def target = getOptionalTarget(fieldDesc)
        assert target
        Map<String,Object> mapVar = variableScope.decodeVariable(varName)
        assert mapVar
        def data = TableUtil.dataTableToMapFromSourceMap(dataTable, mapVar)
        enterTargetValues(target, data)
    }

    void hasValuesFromVariable(boolean wait, String fieldDesc, String varName, DataTable dataTable) {
        Map<String,Object> mapVar = variableScope.decodeVariable(varName)
        assert mapVar
        def data = TableUtil.dataTableToMapFromSourceMap(dataTable, mapVar)
        runWithOptionalWait(wait) {
            def target = getOptionalTarget(fieldDesc)
            targetHasValues(target, data)
        }
    }

}
