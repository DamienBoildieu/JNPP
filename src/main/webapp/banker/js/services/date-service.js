(function () {
    'use strict';

    angular
        .module('app')
        .factory('DateService', DateService);

    DateService.$inject = ['$filter'];
    function DateService($filter) {

        /***********************************************************************
         * Construction du service. */

        const service = {};

        service.now = now;
        service.format = format;
        
        return service;

        /***********************************************************************
         * Methodes du services. */
        
        /* Retourne la l'heure et la date de l'instant. */
        function now() {
            return $filter('date')(Date.now(), 'dd/MM/yyyy HH:mm:ss');
        }
        
        /* Formate une date machine. */
        function format(time) {
            return $filter('date')(time, 'dd/MM/yyyy HH:mm:ss');
        }
   
    }

})();


