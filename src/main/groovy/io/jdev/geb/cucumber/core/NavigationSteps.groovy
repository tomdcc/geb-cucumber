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

import cucumber.api.DataTable
import cucumber.api.Scenario
import geb.Page
import io.jdev.cucumber.variables.core.Decoder
import io.jdev.geb.cucumber.core.util.TableUtil
import org.openqa.selenium.Alert
import org.openqa.selenium.NoAlertPresentException

import java.lang.reflect.Modifier

class NavigationSteps extends StepsBase {
    PageFinder pageFinder
    String mainWindowHandle
    Alert currentAlert

    public void before (Scenario scenario, Binding binding, PageFinder pageFinder, Decoder variableDecoder) {
        super.before(scenario, binding, variableDecoder)
        this.pageFinder = pageFinder
    }

    public void after(Scenario scenario) {
        super.after(scenario)
        cleanupWindows()
    }

    public void to(String pageName, Map params = [:]) {
        Class<? extends Page> pageClass = pageFinder.getPageClass(pageName)
        assert pageClass
        browser.to(params, pageClass)
    }

    public void to(String pageName, String niceParamName, String paramRawValue) {
        Class<? extends Page> pageClass = pageFinder.getPageClass(pageName)
        assert pageClass
        browser.to(getNiceParams(pageClass, niceParamName, paramRawValue), pageClass)
    }

    public void to(String path, DataTable dataTable) {
        to(path, tableToParams(dataTable))
    }

    public void via(String pageName, Map params = [:]) {
        Class<? extends Page> pageClass = pageFinder.getPageClass(pageName)
        assert pageClass
        browser.via(params, pageClass)
    }

    public void via(String pageName, String niceParamName, String paramRawValue) {
        Class<? extends Page> pageClass = pageFinder.getPageClass(pageName)
        assert pageClass
        browser.via(getNiceParams(pageClass, niceParamName, paramRawValue), pageClass)
    }

    public void via(String path, DataTable dataTable) {
        via(path, tableToParams(dataTable))
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

    public void go(String path, Map params = [:]) {
        browser.go(params, path)
    }

    public void go(String path, DataTable dataTable) {
        Map<String,Object> params = tableToParams(dataTable)
        browser.go(params, path)
    }

    private Map<String,Object> tableToParams(DataTable dataTable) {
        assert dataTable.cells(0).size() == 2, "must specify exactly one row for parameters"
        TableUtil.dataTableToMaps(variableScope, dataTable).first()
    }

    public void atPath(String path) {
        String expectedUrl = buildUrl(path)
        assert browser.driver.currentUrl == expectedUrl
    }

    private String buildUrl(String path) {
        URI uri = new URI(path)
        if(!uri.absolute) {
            uri = new URI(browser.baseUrl).resolve(uri)
        }
        uri.toString()
    }

    private static final String PARAM_NAMES_MAP_PROPERTY_NAME = 'paramNames'
    private String getParamFromName(Class<? extends Page> pageClass, String paramDescriptiveName) {
        MetaProperty prop = pageClass.metaClass.getMetaProperty(PARAM_NAMES_MAP_PROPERTY_NAME)
        assert prop && Modifier.isStatic(prop.modifiers),
                "Cannot look up parameter name $paramDescriptiveName on class $pageClass.name as it does not have a static $PARAM_NAMES_MAP_PROPERTY_NAME field"
        def paramNameMap = pageClass[PARAM_NAMES_MAP_PROPERTY_NAME]
        assert paramNameMap instanceof Map, "Cannot look up parameter name $paramDescriptiveName on class $pageClass.name as it the $PARAM_NAMES_MAP_PROPERTY_NAME field is not a java.util.Map"

        String paramName = paramNameMap[paramDescriptiveName]
        assert paramName, "Cannot look up parameter name $paramDescriptiveName on class $pageClass.name as it is not present in the $PARAM_NAMES_MAP_PROPERTY_NAME map"
        paramName
    }

    private Map<String,Object> getNiceParams(Class<? extends Page> pageClass, String niceParamName, String paramRawValue) {
        String paramName = getParamFromName(pageClass, niceParamName)
        def paramValue = variableScope.decodeVariable(paramRawValue)
        assert paramValue != null, "Could not find variable named $paramRawValue"
        [(paramName): paramValue]
    }

    public void assertPopup() {
        if(mainWindowHandle && mainWindowHandle != browser.driver.windowHandle) {
            throw new IllegalStateException("Previous main window handle set but doesn't match current window handle - nested popups not currently supported")
        }
        mainWindowHandle = browser.driver.windowHandle
        browser.waitFor { browser.driver.windowHandles.size() > 1 }
    }

    public void switchToPopup(String pageName) {
        assertPopup()
        def newWindowHandle = browser.driver.windowHandles.find { it != mainWindowHandle }
        browser.driver.switchTo().window(newWindowHandle)
        if(pageName) {
            at(pageName)
        }
    }

    public void popupClosed() {
        browser.driver.switchTo().window(mainWindowHandle)
        browser.waitFor { browser.driver.windowHandles.size() == 1 }
    }

    public void closePopup() {
        // just playing it safe
        if(!mainWindowHandle) {
            mainWindowHandle = browser.driver.windowHandle
        }
        if(mainWindowHandle == browser.driver.windowHandle) {
            // select the window which isn't the main one
            String otherHandle = browser.driver.windowHandles.find { it != mainWindowHandle }
            assert otherHandle, "Could not find other window to close"
            browser.driver.switchTo().window(otherHandle)
        }
        browser.driver.close()
        browser.driver.switchTo().window(mainWindowHandle)
    }

    private cleanupWindows() {
        if(currentAlert) {
            try {
                currentAlert.dismiss()
            } catch(NoAlertPresentException e) { }
            currentAlert = null
        }
        if(mainWindowHandle && browser.driver.windowHandles.size() > 1) {
            browser.driver.windowHandles.findAll { it != mainWindowHandle } .each { handle ->
                browser.driver.switchTo().window(handle).close()
            }
            browser.driver.switchTo().window(mainWindowHandle)
        }
        mainWindowHandle = null
    }

    public void hasAlert(String rawAlertMessage) {
        currentAlert = browser.waitFor { browser.driver.switchTo().alert() }
        if(rawAlertMessage) {
            String expectedAlertMessage = variableScope.decodeVariable(rawAlertMessage) as String
            assert currentAlert.text == expectedAlertMessage
        }
    }

    public void enterAlertPrompt(String value) {
        assert currentAlert
        currentAlert.sendKeys(variableScope.decodeVariable(value) as String)
    }

    public void dismissAlert() {
        currentAlert.dismiss()
        currentAlert = null
    }

    public void acceptAlert() {
        currentAlert.accept()
        currentAlert = null
    }
}
