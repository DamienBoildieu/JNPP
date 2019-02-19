(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('SavingbooksController', SavingbooksController);
 
    SavingbooksController.$inject = ['$scope', 'SavingbooksService'];
    function SavingbooksController($scope, SavingbooksService) {
        
        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        const DEFAULT_SAVINGBOOK = 
                {name: null, moneyRate: null, timeRate: null};
        
        vm.savingbooks = new Array();
        vm.savingbook = angular.copy(DEFAULT_SAVINGBOOK);
        
        /***********************************************************************
         * Constructeur du controller. */
        
        (function() {
            getSavingbooks();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        function getSavingbooks() {
            SavingbooksService.getSavingbooks().then(
                function(response) {
                    vm.savingbooks = response;
                }
            );            
        }
        
        /***********************************************************************
         * Methodes publiques du controller accesible a la vue. */
        
        vm.addSavingbook = function() {         
            SavingbooksService.addSavingbook(vm.savingbook).then(
                function(response) {
                    vm.savingbooks.push(response);
                    vm.savingbook = angular.copy(DEFAULT_SAVINGBOOK);
                    $scope.form.$setPristine();
                    $scope.form.$setUntouched();
                }
            );
        };
    
    }
 
})();
