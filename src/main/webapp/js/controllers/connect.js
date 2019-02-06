(function () {
    'use strict';
 
    angular
        .module('app')
        .controller('ConnectController', ConnectController);
 
    ConnectController.$inject = ['$location', 'AuthentificationService', 'FlashService'];
    
    function ConnectController($location, AuthentificationService, FlashService) {
        let vm = this;
        
        vm.connectData = {};
        vm.connect = connect;
        
        function connect() {
            AuthentificationService.login(vm.connectData).then(
                function() {
                    AuthentificationService.setCredentials(vm.connectData.username, 
                        vm.connectData.password);
                    FlashService.Success('Utilisateur connecté', true);
                    $location.path('/');
                },
                function (response) {
                    FlashService.Error(response, true);
                }
            );
        };
    }
 
})();