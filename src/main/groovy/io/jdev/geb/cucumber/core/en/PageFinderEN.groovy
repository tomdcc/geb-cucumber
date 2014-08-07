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

import geb.Page
import io.jdev.geb.cucumber.core.BasePageFinder
import io.jdev.geb.cucumber.core.PageFinder
import io.jdev.geb.cucumber.core.PageFinderSetup
import io.jdev.geb.cucumber.core.util.NameUtil

import java.util.regex.Matcher
import java.util.regex.Pattern

class PageFinderEN extends BasePageFinder<Page> {

	static final Pattern PAGE_NAME_PATTERN = Pattern.compile("(.*) (page|dialog)")

    private static PageFinder instance

    public PageFinderEN() {
        super(Page)
    }

    public static PageFinder getInstance() {
        if(!instance) {
            instance = new PageFinderEN(packageNames: PageFinderSetup.packageNames)
        }
        instance
    }
	
    public static void setInstance(PageFinder pageFinder) {
        instance = pageFinder
    }

	@Override
	String pageNameToClassName(String pageName) {
		if(!pageName) {
			return null
		}
		pageName = pageName.toLowerCase()
		Matcher m = PAGE_NAME_PATTERN.matcher(pageName)
		if(!m.matches()) {
			// this probably shouldn't happen
			return null
		}
		return NameUtil.lowerCaseToProperCase(m.group(0))
	}
}
