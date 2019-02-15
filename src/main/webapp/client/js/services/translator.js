(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('TranslatorService', TranslatorService);
     
    function TranslatorService() {
        
        let service = {};
        
        service.translateGenders = translateGenders;
        service.isTranslatedGender = isTranslatedGender;
        service.translateGender = translateGender;
        service.untranslateGender = untranslateGender;
        
        service.isTranslatedAccount = isTranslatedAccount;
        service.translateAccount = translateAccount;
        service.untranslateAccount = untranslateAccount;
        
        service.isTranslatedCurrency = isTranslatedCurrency;
        service.translateCurrency = translateCurrency;
        service.untranslateCurrency = untranslateCurrency;
        
        return service;      
        
        function translateGenders(genderArray) {
            return genderArray.map(gender => ({key:gender, value:translateGender(gender)}));
        }
        
        function isTranslatedGender(string) {
            return string==='Homme' || string==='Femme';
        }
        
        function translateGender(gender) {
            if (gender==='MALE')
                return 'Homme';
            else if (gender==='FEMALE')
                return 'Femme';
            else
                return '';
        }
        
        function untranslateGender(value) {
            if (value==='Homme')
                return 'MALE';
            else if (value==='Femme')
                return 'FEMALE';
            else
                return '';
        }
        
        function isTranslatedAccount(string) {
            return string==='Compte courrant' || string==='Compte joint'
                || string==='Compte dépôt' || string==='Compte titres';
        }
        
        function translateAccount(account) {
            if (account==='CURRENT')
                return 'Compte courrant';
            else if (account==='JOINT')
                return 'Compte joint';
            else if (account==='SAVING')
                return 'Compte dépôt';
            else if (account==='SHARE')
                return 'Compte titres';
            else
                return '';
        }
        
        function untranslateAccount(value) {
            if (value==='Compte courrant')
                return 'CURRENT';
            else if (value==='JOINT')
                return 'Compte joint';
            else if (value==='Compte dépôt')
                return 'SAVING';
            else if (value==='Compte titres')
                return 'SHARE';
            else
                return '';
        }
        
        function isTranslatedCurrency(string) {
            return string==='€';
        }
        
        function translateCurrency(currency) {
            if (currency==='EURO')
                return '€';
            else
                return '';
        }
        
        function untranslateCurrency(value) {
            if (value==='€')
                return 'EURO';
            else
                return '';
        }
    }
 
})();
 