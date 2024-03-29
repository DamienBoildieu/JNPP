(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginsController', LoginsController);
 
    LoginsController.$inject = ['LoginsService'];
    function LoginsController(LoginsService) {
        
        const vm = this;
        
        /***********************************************************************
         * Attributs du controller. */
        
        vm.logins = new Array();

        /***********************************************************************
         * Constructeur du controller. */
        
        (function() {
            getLogins();
        })();

        /***********************************************************************
         * Methodes privees du controller. */
        
        function getLogins() {
            LoginsService.getLogins().then(
                function(response) {
                    vm.logins = response;
                }
            );            
        }
    
    }
 
})();