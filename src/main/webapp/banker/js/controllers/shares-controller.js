(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SharesController', SharesController);
 
    SharesController.$inject = ['$location', '$scope', 'SharesService'];
    function SharesController($location, $scope, SharesService) {
        
        var vm = this;
        var defaultForm = {name: null, value: null};
        
        $scope.shares = new Array();
        $scope.data = angular.copy(defaultForm);
        
        vm.addShare = addShare;
        
        getShares();
        
        function getShares() {
            SharesService.getAll().then(
                function(shares) {
                    $scope.shares = shares;
                }
            );            
        }
        
        function addShare() {         
            SharesService.add($scope.data).then(
                function(share) {
                    $scope.shares.push(share);
                    $scope.data = angular.copy(defaultForm);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        }
    
    }
 
})();
