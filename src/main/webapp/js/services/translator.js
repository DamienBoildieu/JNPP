(function () {
    'use strict';
 
    angular
        .module('app')
        .factory('TranslatorService', TranslatorService);
     
    function TranslatorService() {
        
        let service = {};
        
        service.translateGenders = translateGenders;
        
        return service;      
        
        function translateGenders(genderArray) {
            console.log(genderArray);
            return genderArray.map(gender => ({key:gender, value:translateGender(gender)}));
        }
        
        function translateGender(gender) {
            if (gender==='MALE')
                return 'Homme';
            else if (gender==='FEMALE')
                return 'Femme';
            else
                return '';
        }
    }
 
})();
 