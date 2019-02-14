(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('AdvisorsController', AdvisorsController);
 
    AdvisorsController.$inject = ['$scope', 'AdvisorsService'];
    function AdvisorsController($scope, AdvisorsService) {
        
        var vm = this;
        var defaultForm = {lastname: null, firstname: null};
        
        $scope.advisors = new Array();
        $scope.data = angular.copy(defaultForm);
        
        vm.addAdvisor = addAdvisor;
        
        getAdvisors();
        
        function getAdvisors() {
            AdvisorsService.getAll().then(
                function(advisors) {
                    $scope.advisors = advisors;
                }
            );            
        }
        
        function addAdvisor() {         
            AdvisorsService.add($scope.data).then(
                function(advisor) {
                    $scope.advisors.push(advisor);
                    $scope.data = angular.copy(defaultForm);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        }
    
    }
 
})();
