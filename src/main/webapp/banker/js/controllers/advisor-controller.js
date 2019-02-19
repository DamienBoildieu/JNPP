(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorController', AdvisorController);
 
    AdvisorController.$inject = ['$location', 'AdvisorsService'];
    function AdvisorController($location, AdvisorsService) {
        
        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */

        vm.advisor = {
            firstname: $location.search().firstname, 
            lastname: $location.search().lastname};
        vm.clients = new Array();
        
        /***********************************************************************
         * Constructeur du controller. */
        
        (function() {
            
            /* Pas de parametres passes dans l'url, on redirige. */
            if (!vm.advisor.firstname || !vm.advisor.lastname) 
                $location.path('/advisors');
            
            getClients(vm.advisor.firstname, vm.advisor.lastname);
        })();
        
        /***********************************************************************
         * Methodes privees du controller. */
        
        function getClients(firstname, lastname) {
            AdvisorsService.getAdvisorClients(firstname, lastname).then(
                function(clients) {
                    vm.clients = clients;
                },
                function() {
                    $location.path('/advisors');
                }
            );            
        }
    
    }
 
})();
