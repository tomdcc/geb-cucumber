package io.jdev.geb.cucumber.core.util

import geb.content.SimplePageContent
import geb.navigator.Navigator
import io.jdev.geb.cucumber.core.CheckedDecoder
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
		// handle differences between different geb versions
		_ * content.$() >> nav
		_ * content.methodMissing('$', []) >> nav
		_ * content.value() >> ' hi there '
		_ * content.methodMissing('value', []) >> ' hi there '

        when:
        String result = getValue(content)

        then: 'trimmed val returned'
        result == 'hi there'
    }

    void "get value on select simple page content returns its first selected option text"() {
        given:
        SimplePageContent content = Mock(SimplePageContent)
        Navigator nav = Mock(Navigator)
		// handling some differences between geb versions here, that's why we're mocking
		// things multiple ways
        _ * content.$() >> nav
        _ * content.methodMissing('$', []) >> nav
        _ * content.is('select') >> true
        _ * nav.is('select') >> true
        WebElement select = Mock(WebElement)
        WebElement option = Mock(WebElement)
        _ * select.getTagName() >> 'select'
        _ * select.findElements(_) >> [option]
        _ * content.firstElement() >> select
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
		// handle differences between geb versions, mock in different ways
        _ * nav.is('select') >> false
        _ * content.is('select') >> false
		_ * content.$() >> nav
		_ * content.value() >> value
		_ * content.methodMissing('value', []) >> value
		_ * content.text() >> ' hi there '
		_ * content.methodMissing('text', []) >> ' hi there '

        when:
        String result = getValue(content)

        then: 'trimmed val returned'
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

    void "hasValue gives correct values for simple string equality"() {
        when:
        hasValue('foo', 'foo')

        then:
        notThrown(Throwable)

        when:
        hasValue(1, '1')

        then:
        notThrown(Throwable)

        when:
        hasValue('foo', 'bar')

        then:
        thrown(AssertionError)
    }

    void "hasValue gives correct values for checkbox state matching"() {
        given:
        Navigator field = Mock(Navigator)

        // field and expectation both checked
        when:
        hasValue(field, CheckedDecoder.CheckedState.checked)

        then:
        1 * field.value() >> 'not false'

        and:
        notThrown(Throwable)

        // field and expectation both unchecked
        when:
        hasValue(field, CheckedDecoder.CheckedState.unchecked)

        then:
        1 * field.value() >> false

        and:
        notThrown(Throwable)

        // field checked, expectation unchecked
        when:
        hasValue(field, CheckedDecoder.CheckedState.unchecked)

        then:
        1 * field.value() >> 'not false'

        and:
        thrown(AssertionError)

        // field unchecked, expectation checked
        when:
        hasValue(field, CheckedDecoder.CheckedState.checked)

        then:
        1 * field.value() >> false

        and:
        thrown(AssertionError)
    }

    void "hasValue gives correct values for regex pattern matching"() {
        when:
        hasValue('foo', ~/\w{3}/)

        then:
        notThrown(Throwable)

        when:
        hasValue(1, ~/\d/)

        then:
        notThrown(Throwable)

        when:
        hasValue('foo', ~/w{4}/)

        then:
        thrown(AssertionError)
    }

}
