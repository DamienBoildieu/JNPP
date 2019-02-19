(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SharesController', SharesController);
 
    SharesController.$inject = ['$scope', 'SharesService'];
    function SharesController($scope, SharesService) {
        
        var vm = this;
        var defaultForm = {name: null, value: null};
        
        vm.shares = new Array();
        vm.data = angular.copy(defaultForm);
        
        vm.addShare = addShare;
        
        getShares();
        
        function getShares() {
            SharesService.getAll().then(
                function(shares) {
                    vm.shares = shares;
                }
            );            
        }
        
        function addShare() {         
            SharesService.add(vm.data).then(
                function(share) {
                    vm.shares.push(share);
                    vm.data = angular.copy(defaultForm);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        }
    
    }
 
})();
