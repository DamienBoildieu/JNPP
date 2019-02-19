(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorsController', AdvisorsController);
 
    AdvisorsController.$inject = ['$scope', 'AdvisorsService'];
    function AdvisorsController($scope, AdvisorsService) {
        
        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        const DEFAULT_ADVISOR = {lastname: null, firstname: null};
        
        vm.advisors = new Array();
        vm.advisor = angular.copy(DEFAULT_ADVISOR);
        
        /***********************************************************************
         * Constructeur du controller. */
        
        (function() {
            getAdvisors();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        function getAdvisors() {
            AdvisorsService.getAdvisors().then(
                function(response) {
                    vm.advisors = response;
                }
            );            
        }
        
        /***********************************************************************
         * Methodes publiques du controller accesible a la vue. */
        
        vm.addAdvisor = function() {         
            AdvisorsService.addAdvisor(vm.advisor).then(
                function(response) {
                    vm.advisors.push(response);
                    vm.advisor = angular.copy(DEFAULT_ADVISOR);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        };
    
    }
 
})();
