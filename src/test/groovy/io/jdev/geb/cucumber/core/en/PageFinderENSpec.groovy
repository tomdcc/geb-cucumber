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
import test.pages.foo.bar.ViewWidgetPage

class PageFinderENSpec extends Specification {

	PageFinderEN pageFinder

	void setup() {
		pageFinder = new PageFinderEN()
		pageFinder.packageNames = ['test.pages']
	}

	void "page finder resolves pages correctly"() {
		expect:
			pageFinder.getPageClass("view widget page") == ViewWidgetPage.class
	}

	void "page finder throws exception for non-existent page"() {
		when:
			pageFinder.getPageClass("not there page")
		then:
			thrown(IllegalArgumentException)
	}

	void "page finder throws exception for duplicated pages"() {
		when:
			pageFinder.getPageClass("duplicated page")
		then:
			thrown(IllegalArgumentException)
	}
}
