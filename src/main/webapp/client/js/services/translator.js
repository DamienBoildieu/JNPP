(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('TranslatorService', TranslatorService);
     
    TranslatorService.$inject = ['$filter'];
    function TranslatorService($filter) {
        
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
        
        service.isTranslatedPaymentMean = isTranslatedPaymentMean;
        service.translatePaymentMean = translatePaymentMean;
        service.untranslatePaymentMean = untranslatePaymentMean;
        
        service.transformNotif = transformNotif;
        service.transformNotifs = transformNotifs;
        
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
        
        function isTranslatedPaymentMean(string) {
            return string==='carte bancaire' || string==='chéquier';
        }
        
        function translatePaymentMean(paymentMean) {
            if (paymentMean==='BANKCARD')
                return 'carte bancaire';
            else if (paymentMean==='CHECKBOOK')
                return 'chéquier';
            else
                return '';
        }
        
        function untranslatePaymentMean(value) {
            if (value==='carte bancaire')
                return 'BANKCARD';
            else if (value==='chéquier')
                return 'CHECKBOOK';
            else
                return '';
        }
        
        
        function transformNotif(notif) {
            if (notif.type==='APPOINTMENT')
                notif.message = 'Vous avez rendez-vous avec conseiller le '
                + $filter(notif.appointment.date, 'dd/MM/yyyy HH:mm');
            else if (notif.type==='PAYMENT_MEAN') {
                let translated = service.translatePaymentMean(notif.paymentMean.type);
                if (notif.paymentMean==='ORDERED')
                    notif.message = 'Votre ' + translated + ' est commandé';
                else if (notif.paymentMean==='ARRIVED')
                    notif.message = 'Votre ' + translated + ' est arrivé';
                else if (notif.paymentMean==='DELIVERED')
                    notif.message = 'Vous avez récupéré votre ' + translated;
                else
                    notif.message = 'Votre ' + translated + ' est dans un état inconnu';
            } else if (notif.type==='MESSAGE')
                notif.message = 'Vous avez un reçu un nouveau message';
            else if (notif.type==='MOVEMENT')
                notif.message = 'Une transaction a eu lieu sur un de vos compte';
            else if (notif.type==='OVERDRAFT')
                notif.message = 'Vous êtes en négatif sur le compte ' + notif.rib;
            else
                notif.message = '';
        }
        
        function transformNotifs(notifs) {
            for (let notif of notifs) {
                service.transformNotif(notif);
            }
        }
    }
 
})();
 