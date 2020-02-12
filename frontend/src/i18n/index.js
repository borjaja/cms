import '@formatjs/intl-relativetimeformat/polyfill';
import '@formatjs/intl-relativetimeformat/dist/locale-data/en';
import '@formatjs/intl-relativetimeformat/dist/locale-data/es';
import '@formatjs/intl-relativetimeformat/dist/locale-data/gl';


import messages from './messages';

export const initReactIntl = () => {

    //addLocaleData([...en, ...es, ...gl]);

    let locale = (navigator.languages && navigator.languages[0]) ||
        navigator.language || navigator.userLanguage || 'en';
    const localeWithoutRegionCode = locale.toLowerCase().split(/[_-]+/)[0];
    const localeMessages = messages[locale] || 
        messages[localeWithoutRegionCode] || messages['en'];

    locale = localeMessages === messages['en'] ? 'en' : locale;

    return {locale, messages: localeMessages};

}

