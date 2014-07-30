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

import geb.Browser
import org.openqa.selenium.WebDriver
import spock.lang.Specification

class NavigationStepsSpec extends Specification {

    static final String BASE_URL = 'http://foo.com/bar/'
    static final String CURRENT_URL = 'http://foo.com/bar/baz/foo.jsp'
    static final String CURRENT_URI = 'baz/foo.jsp'

    NavigationSteps steps
    Browser browser
    WebDriver driver

    void setup() {
        steps = new NavigationSteps()
        steps.browser = browser = Mock(Browser)

        _ * browser.getBaseUrl() >> BASE_URL
        driver = Mock(WebDriver)
        _ * browser.getDriver() >> driver
        _* driver.getCurrentUrl() >> CURRENT_URL
    }

    void "can detect if at absolute url"() {
        when: 'assert at current url'
            steps.atPath(CURRENT_URL)

        then: 'all ok'
            notThrown(Throwable)

        when: 'assert at different url'
            steps.atPath(CURRENT_URL + '/blah')

        then: 'assertion thrown'
            thrown(AssertionError)
    }

    void "can detect if at relative uri"() {
        when: 'assert at current uri'
            steps.atPath(CURRENT_URI)

        then: 'all ok'
            notThrown(Throwable)

        when: 'assert at different uri'
            steps.atPath(BASE_URL + '/blah')

        then: 'assertion thrown'
            thrown(AssertionError)
    }

}
