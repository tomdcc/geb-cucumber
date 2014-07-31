package pages

import geb.Module
import geb.Page

class FormPage extends Page {
    static url = 'form.jsp'
    static at = { title == 'Form page' }
    static Map paramNames = ['dog id': 'dog_id']
    static content = {
        name { $('input[name=name]') }
        favouriteLanguage { $('select[name=favlang]') }
        useGnat { $('input[name=gnat]') }
        address { module AddressModule, $('#address') }
    }
}

class AddressModule extends Module {
    static content = {
        streetAddress      { $('textarea[name=streetAddress]') }
        city               { $('input[name=city]') }
        state              { $('select[name=state]') }
        postcode           { $('input[name=postcode]') }
        registeredMailOnly { $('input[name=regMail]') }
    }
}