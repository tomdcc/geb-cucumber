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

abstract class FieldFinder {

    protected abstract String fieldDescriptionToFieldName(String fieldDescription)
    protected abstract FieldResult findSectionField(String fieldDescription, def parent)
    protected abstract FieldResult findNthSectionField(String fieldDescription, def parent)
    protected abstract FieldResult findRowField(String fieldDescription, def parent)

    def findField(String fieldDescription, def page) {
        findField(fieldDescription, page, true)
    }

    private def findField(String fieldDescription, def parent, boolean topLevel) {
        FieldResult fieldSearchResult = null
        if(!topLevel) {
            // modules can have rows, but pages can't
            fieldSearchResult = findRowField(fieldDescription, parent)
        }
        if(fieldSearchResult == null) {
            fieldSearchResult = findNthSectionField(fieldDescription, parent)
        }
        if(fieldSearchResult == null) {
            fieldSearchResult = findSectionField(fieldDescription, parent)
        }
        if(fieldSearchResult != null) {
            // recurse
            return findField(fieldSearchResult.newFieldDescription, fieldSearchResult.newParent, false)
        } else {
            String fieldName = fieldDescriptionToFieldName(fieldDescription)
            return parent[fieldName]
        }
    }

    public static class FieldResult {
        def newParent
        String newFieldDescription
    }
}
