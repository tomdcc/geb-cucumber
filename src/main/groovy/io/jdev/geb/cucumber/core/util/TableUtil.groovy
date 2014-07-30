package io.jdev.geb.cucumber.core.util

import static NameUtil.lowerCaseToCamelCase

import cucumber.api.DataTable
import io.jdev.cucumber.variables.VariableScope

class TableUtil {

    static List<Map<String,Object>> dataTableToMaps(VariableScope variables, DataTable table) {
        List<Map<String,Object>> maps = new ArrayList<Map<String,Object>>()
        List<String> keys = table.topCells().collect { lowerCaseToCamelCase(it) }
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
