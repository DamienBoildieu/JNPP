(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('TranslatorService', TranslatorService);
     
    function TranslatorService() {
        
        let service = {};
        
        service.translateGenders = translateGenders;
        service.translateGender = translateGender;
        service.untranslateGender = untranslateGender;
        service.isTranslatedGender = isTranslatedGender;
        
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
    }
 
})();
 