(function () {
    'use strict';
 
    angular
        .module('app-banker')
        .controller('LoginsController', LoginsController);
 
    LoginsController.$inject = [];
    function LoginsController() {
        
        var vm = this;
        
        vm.logins = new Array();
        getLogins();
        
        function getLogins() {
            vm.logins.push({name : 'pierre'});
            vm.logins.push({name : 'damien'});
        }
    
    }
 
})();