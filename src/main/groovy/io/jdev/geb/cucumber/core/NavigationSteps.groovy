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

import cucumber.api.Scenario
import geb.Page
import io.jdev.cucumber.variables.core.Decoder

class NavigationSteps extends StepsBase {
    PageFinder pageFinder

    public void before (Scenario scenario, Binding binding, PageFinder pageFinder, Decoder variableDecoder) {
        super.before(scenario, binding, variableDecoder)
        this.pageFinder = pageFinder
    }

    public void to(String pageName) {
        Class<? extends Page> pageClass = pageFinder.getPageClass(pageName)
        assert pageClass
        browser.to pageClass
    }

    public void via(String pageName) {
        Class<? extends Page> pageClass = pageFinder.getPageClass(pageName)
        assert pageClass
        browser.via pageClass
    }

    public void at(String pageName) {
        Class<? extends Page> pageClass = pageFinder.getPageClass(pageName)
        assert pageClass
        assert browser.at(pageClass)
    }

    public void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000)
        } catch(InterruptedException e) {}
    }

    public void go(String path) {
        browser.go(path)
    }

    public void atPath(String path) {
        URI uri = new URI(path)
        if(!uri.absolute) {
            uri = new URI(browser.baseUrl).resolve(uri)
        }
        assert browser.driver.currentUrl.equals(uri.toString())
    }
}
