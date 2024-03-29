(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SharesController', SharesController);
 
    SharesController.$inject = ['$scope', 'SharesService'];
    function SharesController($scope, SharesService) {
        
        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        const DEFAULT_SHARE = {name: null, value: null};
        
        vm.shares = new Array();
        vm.share = angular.copy(DEFAULT_SHARE);
        
        /***********************************************************************
         * Constructeur du controller. */
        
        (function() {
            getShares();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        function getShares() {
            SharesService.getShares().then(
                function(response) {
                    vm.shares = response;
                }
            );            
        }
        
        /***********************************************************************
         * Methodes publiques du controller accesible a la vue. */
        
        vm.addShare = function() {         
            SharesService.addShare(vm.share).then(
                function(response) {
                    vm.shares.push(response);
                    vm.share = angular.copy(DEFAULT_SHARE);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        };
    
    }
 
})();
