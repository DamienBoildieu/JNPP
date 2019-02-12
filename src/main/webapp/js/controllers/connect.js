(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConnectController', ConnectController);
 
    ConnectController.$inject = ['$location', 'NotifyService', 
        'AuthentificationService', 'FlashService'];
    
    function ConnectController($location, NotifyService, AuthentificationService, FlashService) {
        AuthentificationService.unconnectedPage('/welcome');
        let vm = this;
        
        vm.connectData = {};
        vm.connect = connect;
        
        function connect() {
            AuthentificationService.login(vm.connectData, function(response) {
                if (response.success) {
                    NotifyService.notify('logInOutEvent');
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/home');
                } else {
                    FlashService.Error(response.message);
                }
            });
        }
    }
 
})();