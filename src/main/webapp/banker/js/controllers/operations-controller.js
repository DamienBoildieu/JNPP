(function () {
    'use strict';

    angular
        .module('app')
        .controller('OperationsController', OperationsController);

    OperationsController.$inject = ['SharesService'];
    function OperationsController(SharesService) {

        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        vm.shares = new Array();
        
        /***********************************************************************
         * Constructeur du controller. */
        
        /* Initialise le controller. */
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

    }

})();
