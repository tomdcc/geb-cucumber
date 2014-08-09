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

package io.jdev.geb.cucumber.core.util

import static NameUtil.lowerCaseToCamelCase

import cucumber.api.DataTable
import io.jdev.cucumber.variables.VariableScope

class TableUtil {

    static List<Map<String,Object>> dataTableToMaps(VariableScope variables, DataTable table, boolean convertKeys = true) {
        List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>()
        List<String> keys = table.topCells()
        if(convertKeys) {
            keys = keys.collect { lowerCaseToCamelCase(it) }
        }
        for(List<String> tableRow : table.cells(1)) {
            Map<String,Object> resultRow = new LinkedHashMap<String,Object>()
            for(int i = 0; i < tableRow.size(); i++) {
                resultRow.put(keys[i], variables.decodeVariable(tableRow[i]))
            }
            maps.add(resultRow)
        }
        return maps
    }
}
