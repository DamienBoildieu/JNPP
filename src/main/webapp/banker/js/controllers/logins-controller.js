(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('LoginsController', LoginsController);
 
    LoginsController.$inject = ['LoginsService'];
    function LoginsController(LoginsService) {
        
        var vm = this;
        
        vm.logins = new Array();
        getLogins();
        
        function getLogins() {
            LoginsService.getAll().then(
                function(logins) {
                    vm.logins = logins;
                }
            );            
        }
    
    }
 
})();