(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorsController', AdvisorsController);
 
    AdvisorsController.$inject = ['$scope', 'AdvisorsService'];
    function AdvisorsController($scope, AdvisorsService) {
        
        var vm = this;
        var defaultForm = {lastname: null, firstname: null};
        
        vm.advisors = new Array();
        vm.data = angular.copy(defaultForm);
        
        vm.addAdvisor = addAdvisor;
        
        getAdvisors();
        
        function getAdvisors() {
            AdvisorsService.getAll().then(
                function(advisors) {
                    vm.advisors = advisors;
                }
            );            
        }
        
        function addAdvisor() {         
            AdvisorsService.add(vm.data).then(
                function(advisor) {
                    vm.advisors.push(advisor);
                    vm.data = angular.copy(defaultForm);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        }
    
    }
 
})();
