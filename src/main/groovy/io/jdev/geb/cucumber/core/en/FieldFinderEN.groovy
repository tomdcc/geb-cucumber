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

package io.jdev.geb.cucumber.core.en

import java.util.regex.Matcher
import java.util.regex.Pattern

import static io.jdev.geb.cucumber.core.util.NameUtil.lowerCaseToCamelCase
import io.jdev.geb.cucumber.core.FieldFinder

class FieldFinderEN implements FieldFinder {

    static final Pattern SECTION_PATTERN = ~/(.*?) (?:section|table) (.*)/
    static final Pattern NTH_SECTION_PATTERN = ~/(\d+)(?:st|nd|rd|th) (.*?) (?:section|table) (.*)/
    static final Pattern ROW_PATTERN = ~/(\d+)(?:st|nd|rd|th) row (.*)/

    @Override
    def findField(String fieldDescription, def page) {
        findFieldFromParent(fieldDescription, page, true)
    }

    private def findFieldFromParent(String fieldDescription, def parent, boolean topLevel) {
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
            return findFieldFromParent(fieldSearchResult.newFieldDescription, fieldSearchResult.newParent, false)
        } else {
            String fieldName = fieldDescriptionToFieldName(fieldDescription)
            return parent[fieldName]
        }
    }

    static String fieldDescriptionToFieldName(String fieldDescription) {
        lowerCaseToCamelCase(fieldDescription)
    }

    static FieldResult findSectionField(String fieldDescription, parent) {
        Matcher matcher = fieldDescription =~ SECTION_PATTERN
        if(matcher.matches()) {
            String sectionDesc = matcher.group(1)
            String newFieldDesc = matcher.group(2)

            String sectionName = lowerCaseToCamelCase(sectionDesc)
            new FieldResult(newParent: parent[sectionName], newFieldDescription: newFieldDesc)
        } else {
            null
        }
    }

    static FieldResult findNthSectionField(String fieldDescription, parent) {
        Matcher matcher = fieldDescription =~ NTH_SECTION_PATTERN
        if(matcher.matches()) {
            int num = matcher.group(1) as int
            String sectionDesc = matcher.group(2)
            String newFieldDesc = matcher.group(3)
            String sectionName = lowerCaseToCamelCase(sectionDesc)
            new FieldResult(newParent: parent[sectionName][num - 1], newFieldDescription: newFieldDesc)
        } else {
            null
        }
    }

    static FieldResult findRowField(String fieldDescription, parent) {
        Matcher matcher = fieldDescription =~ ROW_PATTERN
        if(matcher.matches()) {
            int num = matcher.group(1) as int
            String newFieldDesc = matcher.group(2)
            new FieldResult(newParent: parent[num - 1], newFieldDescription: newFieldDesc)
        } else {
            null
        }
    }

    public static class FieldResult {
        def newParent
        String newFieldDescription
    }
}
