(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorController', AdvisorController);
 
    AdvisorController.$inject = ['$location', 'AdvisorService'];
    function AdvisorController($location, AdvisorService) 
    {
        
        var vm = this;
    
        const firstname = $location.search().firstname;
        const lastname = $location.search().lastname;
        
        if (!firstname || !lastname) $location.path('/advisors');

        vm.advisor = {firstname: firstname, lastname: lastname};
        vm.clients = new Array();
        
        getClients(firstname, lastname);
        
        function getClients(firstname, lastname) {
            AdvisorService.getAll(firstname, lastname).then(
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
