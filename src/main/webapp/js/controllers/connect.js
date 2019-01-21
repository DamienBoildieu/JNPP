(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConnectController', ConnectController);
 
    ConnectController.$inject = ['$location', 'AuthentificationService', 'FlashService'];
    
    function ConnectController($location, AuthentificationService, FlashService) {
        var vm = this;
 
        vm.connect = function() {
            AuthentificationService.Login(vm.username, vm.password).then(
                function() {
                    AuthentificationService.SetCredentials(vm.username, vm.password);
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                },
                function (response) {
                    FlashService.Error(response.data, true);
                }
            );
        };
 
        initController();
        
        function initController() {
            AuthentificationService.ClearCredentials();
        }
    }
 
})();