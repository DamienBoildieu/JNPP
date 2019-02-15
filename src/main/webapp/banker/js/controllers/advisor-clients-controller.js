(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorClientsController', AdvisorClientsController);
 
    AdvisorClientsController.$inject = ['$scope', '$location', 
        'AdvisorClientsService'];
    function AdvisorClientsController($scope, $location, AdvisorClientsService) 
    {
        
        var vm = this;
    
        const firstname = $location.search().firstname;
        const lastname = $location.search().lastname;
        
        if (!firstname || !lastname) $location.path('/advisors');

        $scope.clients = new Array();
        
        getClients(firstname, lastname);
        
        function getClients(firstname, lastname) {
            AdvisorClientsService.getAll(firstname, lastname).then(
                function(clients) {
                    $scope.clients = clients;
                },
                function() {
                    $location.path('/advisors');
                }
            );            
        }
    
    }
 
})();
