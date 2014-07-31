package io.jdev.geb.cucumber.core.util

import geb.content.SimplePageContent
import geb.navigator.Navigator
import org.openqa.selenium.WebElement
import spock.lang.Specification
import spock.lang.Unroll

import static io.jdev.geb.cucumber.core.util.ValueUtil.*

class ValueUtilSpec extends Specification {

    void "get value on navigator returns its value"() {
        given:
            Navigator nav = Mock(Navigator)

        when:
            String result = getValue(nav)

        then: 'value asked for'
            1 * nav.value() >> ' hi there '

        and: 'trimmed val returned'
            result == 'hi there'
    }

    @Unroll
    void "get value on navigator returns its text if value is #value"() {
        given:
            Navigator nav = Mock(Navigator)

        when:
            String result = getValue(nav)

        then: 'value asked for'
            1 * nav.value() >> value

        and: 'text asked for'
            1 * nav.text() >> ' hi there '

        and: 'trimmed val returned'
            result == 'hi there'

        where:
            value << [null, '']
    }

    void "get value on non-select simple page content returns its value"() {
        given:
        SimplePageContent content = Mock(SimplePageContent)
        Navigator nav = Mock(Navigator)
        _ * nav.is('select') >> false

        when:
        String result = getValue(content)

        then: 'value asked for'
        1 * content.getProperty('navigator') >> nav
        1 * content.methodMissing('value', []) >> ' hi there '

        and: 'trimmed val returned'
        result == 'hi there'
    }

    void "get value on select simple page content returns its first selected option text"() {
        given:
        SimplePageContent content = Mock(SimplePageContent)
        Navigator nav = Mock(Navigator)
        _ * content.getProperty('navigator') >> nav
        _ * nav.is('select') >> true
        WebElement select = Mock(WebElement)
        WebElement option = Mock(WebElement)
        _ * select.getTagName() >> 'select'
        _ * select.findElements(_) >> [option]
        _ * content.methodMissing('firstElement', []) >> select
        _ * option.getText() >> ' hi there '
        _ * option.isSelected() >> true

        when:
        String result = getValue(content)

        then: 'trimmed val returned'
        result == 'hi there'
    }

    void "get value on select navigator returns its first selected option text"() {
        given:
        Navigator nav = Mock(Navigator)
        _ * nav.is('select') >> true
        WebElement select = Mock(WebElement)
        WebElement option = Mock(WebElement)
        _ * select.getTagName() >> 'select'
        _ * select.findElements(_) >> [option]
        _ * nav.firstElement() >> select
        _ * option.getText() >> ' hi there '
        _ * option.isSelected() >> true

        when:
        String result = getValue(nav)

        then: 'trimmed val returned'
        result == 'hi there'
    }

    void "get value on non-select simple page content returns its text when value is #value"() {
        given:
        SimplePageContent content = Mock(SimplePageContent)
        Navigator nav = Mock(Navigator)
        _ * nav.is('select') >> false

        when:
        String result = getValue(content)

        then: 'value asked for'
        1 * content.getProperty('navigator') >> nav
        1 * content.methodMissing('value', []) >> value
        1 * content.methodMissing('text', []) >> ' hi there '

        and: 'trimmed val returned'
        result == 'hi there'

        where:
        value << [null, '']
    }


    void "get value on other objects returns string"() {
        expect:
            getValue('foo') == 'foo'

        and:
            getValue(1) == '1'

        and:
            getValue(null) == null
    }

}
