package pages

import geb.Page

class FormPage extends Page {
    static url = 'form.jsp'
    static at = { title == 'Form page' }
    static Map paramNames = ['dog id': 'dog_id']
    static content = {
        nameField { $('input[name=name]') }
        favouriteLanguageField { $('select[name=favlang]') }
        useGnatCheckbox { $('input[name=gnat]') }
    }
}
