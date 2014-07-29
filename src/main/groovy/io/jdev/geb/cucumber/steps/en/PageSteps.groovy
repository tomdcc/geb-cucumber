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

package io.jdev.geb.cucumber.steps.en

import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.When
import io.jdev.cucumber.variables.core.BasicSteps
import io.jdev.cucumber.variables.en.EnglishDecoder
import io.jdev.geb.cucumber.core.PageFinderSetup
import io.jdev.geb.cucumber.core.en.PageFinderEN

class PageSteps extends BasicSteps {

	// cache this for the life of the cucumber process
	@Lazy private static PageFinderEN = { new PageFinderEN(packageNames: PageFinderSetup.packageNames) }

	@Before
	public void before(Scenario scenario) {
		super.before(scenario, new EnglishDecoder());
	}

	@After
	public void after(Scenario scenario) {
		super.after(scenario);
	}

	@When(/^I go to the (.*) (page|dialog)$/)
	public void to(String pageName, String pageType) {

	}

}
