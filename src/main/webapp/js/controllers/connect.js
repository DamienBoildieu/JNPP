(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConnectController', ConnectController);
 
    ConnectController.$inject = ['$location', 'AuthentificationService', 'FlashService'];
    
    function ConnectController($location, AuthentificationService, FlashService) {
        var vm = this;
 
        vm.connect = function() {
        	AuthentificationService.Login(vm.username, vm.password, function (response) {
                if (response.success) {
                    AuthentificationService.SetCredentials(vm.username, vm.password);
                    console.log("toto");
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                } else {
                    FlashService.Error(response.message, true);
                }
            });
        };
 
        initController();
        
        function initController() {
            AuthentificationService.ClearCredentials();
        }
    }
 
})();