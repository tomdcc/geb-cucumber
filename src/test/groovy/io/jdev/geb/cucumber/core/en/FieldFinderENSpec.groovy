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

import spock.lang.Specification

class FieldFinderENSpec extends Specification {

    FieldFinderEN fieldFinder

    void setup() {
        fieldFinder = new FieldFinderEN()
    }

    void "can find basic fields"() {
        given: 'page obect model'
            def nameField = new Object()
            def page = [nameField: nameField]

        expect: 'correct name returns field'
            fieldFinder.findField("name field", page) == nameField

        and: "incorrect name doesn't"
            !fieldFinder.findField("some other field", page)
    }

    void "can find field in module"() {
        given: 'page obect model'
            def nameField = new Object()
            def module = [nameField: nameField]
            def page = [foo: module]

        expect: 'correct name returns field'
            fieldFinder.findField("foo section name field", page) == nameField
    }

    void "can find field in nested module"() {
        given: 'page obect model'
        def nameField = new Object()
        def innerModule = [nameField: nameField]
        def outerModule = [bar: innerModule]
        def page = [foo: outerModule]

        expect: 'correct name returns field'
        fieldFinder.findField("foo section bar section name field", page) == nameField
    }

    void "can find fields on rows"() {
        given: 'page obect model'
            def nameField1 = new Object()
            def nameField2 = new Object()
            def row1 = [nameField: nameField1]
            def row2 = [nameField: nameField2]
            def page = [foo: [row1, row2]]

        expect: 'first field returned'
            fieldFinder.findField("foo table 1st row name field", page) == nameField1

        and: 'second field returned'
            fieldFinder.findField("foo table 2nd row name field", page) == nameField2
    }

    void "can find fields on nested rows"() {
        given: 'page obect model'
            def nameField1 = new Object()
            def nameField2 = new Object()
            def row1 = [nameField: nameField1]
            def row2 = [nameField: nameField2]
            def innerTable = [row1, row2]
            def outerTable = [innerTable]
            def page = [foo: outerTable]

        expect: 'first field returned'
            fieldFinder.findField("foo table 1st row 1st row name field", page) == nameField1

        and: 'second field returned'
            fieldFinder.findField("foo table 1st row 2nd row name field", page) == nameField2
    }

    void "can find fields deep in the guts"() {
        given: 'page obect model'
            def nameField = new Object()
            def rowModule = [nameField: nameField]
            def table = [rowModule]
            def section = [results: table]
            def outerTable = [new Object(), section]
            def page = [foo: outerTable]

        expect: 'field returned'
            fieldFinder.findField("foo table 2nd row results table 1st row name field", page) == nameField

        and: 'field returned with 2nd module syntax'
            fieldFinder.findField("2nd foo section results table 1st row name field", page) == nameField
    }
}
