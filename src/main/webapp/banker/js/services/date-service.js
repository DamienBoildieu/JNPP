(function () {
    'use strict';

    angular
        .module('app')
        .factory('DateService', DateService);

    DateService.$inject = ['$filter'];
    function DateService($filter) {

        /***********************************************************************
         * Construction du service. */
        
        const DATE_FORMAT = 'dd/MM/yyyy HH:mm:ss';

        const service = {};

        service.now = now;
        service.nextSecond = nextSecond;
        service.format = format;
        
        return service;

        /***********************************************************************
         * Methodes du services. */
        
        /* Retourne la l'heure et la date de l'instant. */
        function now() {
            return format(Date.now());
        }
        
        /* Retourne l'heure et la date de l'instant plus une seconde. */
        function nextSecond() {
            return format(Date.now() + 1000);
        }
        
        /* Formate une date machine. */
        function format(time) {
            return $filter('date')(time, DATE_FORMAT);
        }
   
    }

})();


